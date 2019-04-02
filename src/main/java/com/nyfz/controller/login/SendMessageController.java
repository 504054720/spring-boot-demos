package com.nyfz.controller.login;

import com.nyfz.service.WeixinService;
import com.nyfz.util.WeixinClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SendMessageController {
    @Autowired
    WeixinService weixinService;


    @PostMapping("/sendMessage")
    public String sendMessage(@RequestPart("file")MultipartFile file){

        if(file.isEmpty()){
            return "上传附件不能为空";
        }
        String fileName =file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if(!".xlsx".equals(suffix) && !".xls".equals(suffix)){
            return  "请上传.xlsx或.xls类型文件";

        }
        return weixinService.sendMessage(file);
    }



}
