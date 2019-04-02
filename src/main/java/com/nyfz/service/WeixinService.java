package com.nyfz.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nyfz.util.WeixinClient;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Service
public class WeixinService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeixinService.class);
    @Value("${weixin.corpid}")
    public String corpid;
    @Value("${weixin.address.corpsecret}")
    public String addressCorpsecret;
    @Value("${weixin.myapp.corpsecret}")
    public String myAppCorpsecret;
    @Value("${weixin.myapp.agentid}")
    public int myappAgentid;
    @Autowired
    WeixinClient weixinClient;

    public String sendMessage(MultipartFile file){
        String result = "1";

        try {
            String fileName = file.getOriginalFilename();
            Workbook workbook = null;
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            if(".xlsx".equals(suffix)){
                workbook = new XSSFWorkbook(file.getInputStream());
            }
            if(".xls".equals(suffix)){
                workbook = new HSSFWorkbook(file.getInputStream());
            }

            File newFile = new File(System.currentTimeMillis()+fileName);
            file.transferTo(newFile);

            //获取通讯录token
            String addressToken = weixinClient.getToken(corpid,addressCorpsecret);
            //获取通讯录列表
            List<Map<String,String>> addressList =  weixinClient.getAddressList(addressToken);
            //获取发送消息token
            String sendMessageToken = weixinClient.getToken(corpid,myAppCorpsecret);

            for(Map user : addressList){
                //获取每个员工的Excel
                File sendFile = this.creadExcelFile(workbook,user);
                //上传每个员工的Excel获取media_id
                JSONObject resultUp = weixinClient.uploadTempFile(sendMessageToken,sendFile);
                if("0".equals(resultUp.get("errcode").toString())){
                    Map<String,Object> contentMap = new HashMap<String, Object>();
                    //contentMap.put("touser", user.get("userid"));
                    contentMap.put("touser", "CaoJunJing");
                    contentMap.put("agentid", myappAgentid);
                    //contentMap.put("msgtype", "text");
                    contentMap.put("msgtype", "file");
                    Map<String,String> cont = new HashMap<String, String>();
                    cont.put("media_id",resultUp.get("media_id").toString());
                    contentMap.put("file", cont);
                    //发送员工工资条Excel
                    result = weixinClient.sendMessage(sendMessageToken,contentMap);
                    //清理文件
                    sendFile.delete();

                }
            }
            LOGGER.info("发送工资条成功！");
            return result;
        } catch (Exception e) {
           LOGGER.info("发送工资条异常："+e.getMessage());
        }
        return result;
    }

    /***
     * 获取姓名与内容的行号关系MAP
     * @param workbook
     * @return
     */
    public Map<String,List<Integer>> getRelationMap(Workbook workbook){
        Sheet sheet = workbook.getSheetAt(0);

        Map<String,List<Integer>> relationMap = new HashMap<String,List<Integer>>();
        for (Row row : sheet){
            row.getCell(0).getStringCellValue();
            List<Integer> rowList = new ArrayList<>();
            if(relationMap.get(row.getCell(0).getStringCellValue()) != null){
                rowList = relationMap.get(row.getCell(0).getStringCellValue());
            }
            rowList.add(row.getRowNum());
            relationMap.put(row.getCell(0).getStringCellValue(),rowList);
        }
        return relationMap;
    }

    /***
     * 创建每个员工的工资条Excel
     * @param workbook
     * @param user
     * @return
     * @throws IOException
     */
    public File creadExcelFile(Workbook workbook,Map user) throws IOException {
        //获取员工姓名与Excel中的位置关系
        Map<String,List<Integer>> relationMap = this.getRelationMap(workbook);
        Sheet sheet = workbook.getSheetAt(0);
        Row headRow = sheet.getRow(0);
        List<Integer> rowList = relationMap.get(user.get("name"));
        Workbook perWorkbook = new XSSFWorkbook();
        Sheet perSheet = perWorkbook.createSheet();
        Row newHeadRow = perSheet.createRow(0);
        for (int i =0;i<headRow.getLastCellNum();i++){
            newHeadRow.createCell(i).setCellValue(headRow.getCell(i).getStringCellValue());
        }
        if(rowList != null){
            for(int i=1;i<=rowList.size();i++){
                int tmpNum = i - 1;
                Row contentRow = sheet.getRow(rowList.get(tmpNum));
                Row newRow = perSheet.createRow(i);
                for(int j=0;j<contentRow.getLastCellNum();j++){
                    if (1 == contentRow.getCell(j).getCellType()) {
                        newRow.createCell(j).setCellValue(contentRow.getCell(j).getStringCellValue());
                    } else {
                        newRow.createCell(j).setCellValue(contentRow.getCell(j).getNumericCellValue());
                    }

                }
            }
        }

        Calendar calendar = Calendar.getInstance();
        StringBuilder perFileName = new StringBuilder();
        perFileName.append(user.get("name"))
                .append("_")
                .append(calendar.get(Calendar.YEAR))
                .append(calendar.get(Calendar.MONTH)+1)
                .append(calendar.get(Calendar.DATE))
                .append("_工资条.xlsx");
        File perFile = new File(perFileName.toString());
        OutputStream outputStream = new FileOutputStream(perFile);
        perWorkbook.write(outputStream);
        return perFile;
    }
}
