package com.nyfz.controller.login;

import com.alibaba.fastjson.JSON;
import com.nyfz.util.WeixinClient;
import com.nyfz.util.http.HttpResult;
import com.nyfz.util.http.HttpService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@RestController
public class UploadWeixin {

    @Autowired
    private HttpService httpService;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public void uploadFile(@RequestPart("file")MultipartFile file){

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

            //File newFile = new File("H:\\workspace\\spring-boot-demos\\"+System.currentTimeMillis()+fileName);
            File newFile = new File(System.currentTimeMillis()+fileName);
            file.transferTo(newFile);

            //获取通讯录token
            String getAddressTokenUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ww403d6245519a4750&corpsecret=-8n4Nv7Xw1m0t6RDQUkbpq6TQkAV5m5nPXgGyOeE8IA";
            Map<String,String> map = new HashMap<String, String>();
            HttpResult result = httpService.doGet(getAddressTokenUrl);
            //HttpResult re=httpService.doPost("http://localhost:8082/nyfz/post", map);
            System.out.println("result_code:"+result.getCode());
            System.out.println("result_body:"+result.getBody());
            Map<String,String> tokenMap = (Map<String, String>) JSON.parse(result.getBody());
            String addressToken = tokenMap.get("access_token");

            //获取通讯录列表
            String getAddressListUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token="+addressToken+"&department_id=1&fetch_child=1";
            HttpResult result2 = httpService.doGet(getAddressListUrl);
            System.out.println("result2_body:"+result2.getBody());
            List<Map<String,String>> userListMap = (List<Map<String, String>>) JSON.parseObject(result2.getBody()).get("userlist");


            //获取发送消息token
            String getSendTokenUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ww403d6245519a4750&corpsecret=xI2eU5r8OoXVu3JR3tpeaS-CwFSc2Pq5YWAUkhHPidE";
            HttpResult result3 = httpService.doGet(getSendTokenUrl);
            System.out.println("result3_body:"+result3.getBody());
            String sendToken = (String) JSON.parseObject(result3.getBody()).get("access_token");


            Map<String,List<Integer>> relationMap = this.getRelationMap(workbook);
            Sheet sheet = workbook.getSheetAt(0);
            Row headRow = sheet.getRow(0);
            for(Map user : userListMap){
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
                        .append("_").append(calendar.get(Calendar.YEAR))
                        .append(calendar.get(Calendar.MONTH)+1).append(calendar.get(Calendar.DATE))
                        .append("_工资条.xlsx");
                        //= calendar.get(Calendar.YEAR)+""+calendar.get(Calendar.MONTH)+""+calendar.get(Calendar.DATE);
                File perFile = new File(perFileName.toString());
                OutputStream outputStream = new FileOutputStream(perFile);
                perWorkbook.write(outputStream);

                //上传临时素材
                String uploadUrl = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token="+sendToken+"&type=file";
                String resultUp = httpService.httpRequest(uploadUrl,perFile);
                System.out.println("result:"+resultUp);
                if("0".equals(JSON.parseObject(resultUp).get("errcode").toString())){
                    String media_id = JSON.parseObject(resultUp).get("media_id").toString();
                    System.out.println("media_id:"+media_id);

                    //发送消息
                    String sendMesUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+sendToken;
                    Map<String,Object> contentMap = new HashMap<String, Object>();
                    //contentMap.put("touser", user.get("userid"));
                    contentMap.put("touser", "CaoJunJing");
                    contentMap.put("agentid", 1000003);
                    //contentMap.put("msgtype", "text");
                    contentMap.put("msgtype", "file");

                    Map<String,String> cont = new HashMap<String, String>();
                    cont.put("media_id",media_id);
                    contentMap.put("file", cont);
                    HttpResult sendResult =  httpService.doPost(sendMesUrl,contentMap);
                    System.out.println(sendResult.getBody().toString());

                    perFile.delete();


                }

            }


        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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



}
