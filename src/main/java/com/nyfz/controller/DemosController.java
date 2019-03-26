package com.nyfz.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import com.alibaba.fastjson.JSON;
import com.nyfz.entity.CountryEntity;
import com.nyfz.entity.UserEntity;
import com.nyfz.mapper.CountryMapper;
import com.nyfz.mapper.UserMapper;
import com.nyfz.util.IdUtil;
import com.nyfz.util.JedisUtil;
import com.nyfz.util.http.HttpResult;
import com.nyfz.util.http.HttpService;


@Controller
public class DemosController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private CountryMapper countryMapper;
	
	@Autowired
	private HttpService httpService;
	
	@RequestMapping(value="/downExecl")
	@ResponseBody
	public void creatExecl(HttpServletResponse response){
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet =wb.createSheet("国家列表");
		creatTitle(wb, sheet);
		List<CountryEntity> dataList = countryMapper.queryAll(null);
		
		if(!dataList.isEmpty()){
			int rowNum=1;
			for(CountryEntity countryE :dataList){
				XSSFRow row = sheet.createRow(rowNum);
				row.createCell(0).setCellValue(countryE.getId());
				row.createCell(1).setCellValue(countryE.getCountryName());
				row.createCell(2).setCellValue(countryE.getCountryCode());
				rowNum++;
			}
			File exportFile = new File("D:/uploadFile/country.xlsx");
			try {
				FileOutputStream fileOut=new FileOutputStream(exportFile);
				
				response.setCharacterEncoding("UTF-8");
	            response.setHeader("content-Type", "application/vnd.ms-excel");
	            response.setHeader("Content-Disposition",
	                    "attachment;filename=" + URLEncoder.encode("country.xlsx", "UTF-8"));
	            wb.write(response.getOutputStream());
				//wb.write(fileOut);
				fileOut.flush();
				fileOut.close();
			} catch (FileNotFoundException e) {
				System.out.println("导出失败"); 
			}catch(IOException e){
				System.out.println("导出失败"); 
			}
			
		}
	}
	
	/***
	 * 创建EXECL 的 title
	 * @param workBook
	 * @param sheet
	 */
	private void creatTitle(XSSFWorkbook workBook,XSSFSheet sheet){
		XSSFRow row = sheet.createRow(0);//创建sheet
		XSSFCell cell;
		cell =row.createCell(0);
		cell.setCellValue("id");
		
		cell = row.createCell(1);
		cell.setCellValue("名称");
		
		cell = row.createCell(2);
		cell.setCellValue("代码");
		
	}
	
	
	/***
	 * 导入EXECL
	 * @param file
	 * @return
	 */
	@PostMapping(value="/uploadExecl")
	@ResponseBody
	public String uploadExecl(@RequestParam("execfile") MultipartFile file){
		if(file.isEmpty()){
			return "空文件";
			
		}
		String fileName=file.getOriginalFilename();
		 if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
	           return "上传文件格式不正确";
		 }
		 try {
			InputStream fileIS=file.getInputStream();
			Workbook wb=new XSSFWorkbook(fileIS);
			Sheet sheet = wb.getSheetAt(0);
			if(sheet == null){
				return "数据为空";
			}
			List<CountryEntity> list = new ArrayList<CountryEntity>();
			CountryEntity countryE;
			System.out.println(sheet.getLastRowNum());
			for(int i=1;i<=sheet.getLastRowNum();i++){
				Row row = sheet.getRow(i);
				countryE = new CountryEntity();
				
				String countryName=row.getCell(0).getStringCellValue();
				String countryCode=row.getCell(1).getStringCellValue();
				countryE.setCountryName(countryName);
				countryE.setCountryCode(countryCode);
				list.add(countryE);
			}
			if(!list.isEmpty()){
				countryMapper.addBatch(list);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			return "导入异常";
		}
		return "导入成功";
	}
	
	
	@PostMapping(value="/upload")
	@ResponseBody
	public String upload(@RequestParam("fileName") MultipartFile file){
		if(file.isEmpty()){
			return "空文件";
			
		}
		String fileName=file.getOriginalFilename();
		String savePath="D:/uploadFile";
		File newFile= new File(savePath+"/"+fileName);
		try {
			file.transferTo(newFile);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "上传失败";
		}
		System.out.println(fileName);
		return "上传成功";
	}
	
	@RequestMapping(value="/delete/{id}")
	public ModelAndView delete(@PathVariable String id){
		ModelAndView result= new ModelAndView("index");
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("id", id);
		countryMapper.delete(map);
		Map<String, String> paraMap =new HashMap<String, String>();
		List<CountryEntity> countryList = countryMapper.queryAll(paraMap);
		result.addObject("countryList", countryList);
		
		return result;
	}
	
	@PostMapping(value="/update")
	public String update(@RequestBody Map<String,Object> map){
		
		countryMapper.update(map);
		return "redirect:index";
	}
	
	@RequestMapping(value="/toUpdate/{id}")
	public ModelAndView toUpdate(@PathVariable String id){
		ModelAndView result= new ModelAndView("update");
		Map<String, String> paraMap =new HashMap<String, String>();
		paraMap.put("id", id);
		List<CountryEntity> countryList = countryMapper.queryAll(paraMap);
		if(countryList != null && countryList.size()>0){
			result.addObject("country", countryList.get(0));
		}
		
		return result;
	}
	
	/***
	 * 增加一个国家
	 */
	@RequestMapping(value="/addCountry/{countryName}/{countryCode}")
	@ResponseBody
	public String addCountry(@PathVariable String countryName,@PathVariable String countryCode){
		System.out.println(countryName+countryCode);
		ModelAndView result =new ModelAndView("index");
		Map<String, String> paraMap =new HashMap<String, String>();
		paraMap.put("countryName", countryName);
		paraMap.put("countryCode", countryCode);
		
		countryMapper.add(paraMap);
		
		return "success";
	}
	
	/***
	 * 按条件查询国家信息
	 */
	@RequestMapping(value="/index")
	public ModelAndView queryCountry(){
		ModelAndView result= new ModelAndView("index");
		Map<String, String> paraMap =new HashMap<String, String>();
		List<CountryEntity> countryList = countryMapper.queryAll(paraMap);
		result.addObject("countryList", countryList);
		return result;
	}
	
	
	
	
	
	
	
	/**
	 * 日志配置
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/log")
	public String log() throws Exception{
        // 日志的级别由低到高 trace < debug < info < warn < error
        // 可以调整需要输出的日志级别；日志会在这个级别和以后的高级别生效
		// Springboot默认给我们使用的是info级别的
        logger.trace("==trace==");
        logger.debug("==debug==");
        logger.info("==info==");
        logger.warn("==warn==");
        JedisUtil.getCluster().set("cjj", "caojunjing");
        logger.error("==error==");
		return "log";
	}
	/**
	 * 
	 * 生成ID
	 * @return
	 */
	@GetMapping("/id")
	public String id(){
		return String.valueOf(IdUtil.next());
	}
	/**
	 * 从数据库查询user表信息
	 * @return
	 */
	@GetMapping("/data")
	public List<UserEntity> data(){
		return userMapper.getData();
	}

	/**
	 * druid数据源监控数据
	 * @return
	 */
	@GetMapping("/druid/stat")
    public Object druidStat(){
        // DruidStatManagerFacade#getDataSourceStatDataList 
		//该方法可以获取所有数据源的监控数据，
		//除此之外 DruidStatManagerFacade 还提供了一些其他方法，你可以按需选择使用。
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }
	
	@GetMapping("/doGet")
	public String doGet() throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", "123");
		map.put("name", "张三");
		int a=1/0;
		HttpResult result = httpService.doGet("http://localhost:8082/nyfz/get",map);
		HttpResult re=httpService.doPost("http://localhost:8082/nyfz/post", map);
		return result.getCode()+result.getBody()+re.getCode()+re.getBody();
	}
	@GetMapping("/get")
	public String doGet1(@RequestParam Map<String,Object> map){
		//if(map != null){
		//	return JSON.toJSONString(map);
		//}
		return "login";
	}
	@PostMapping("/post")
	public String doPost(@RequestBody Map<String,Object> map){
		if(map != null){
			return JSON.toJSONString(map);
		}
		return "post something!";
	}
}
