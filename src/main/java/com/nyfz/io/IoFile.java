package com.nyfz.io;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class IoFile {
    public static void main(String[] args) throws IOException {
        File f = new File("H:/aaa.txt");
        //构建fileOutputStream对象，文件不存在则自动创建
        FileOutputStream fileOutputStream = new FileOutputStream(f);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream,"UTF-8");

        outputStreamWriter.append("输入内容");
        outputStreamWriter.append("输入内容");
        outputStreamWriter.append("\r\n");
        outputStreamWriter.append("输入内容");
        outputStreamWriter.close();;
        fileOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream(f);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,"UTF-8");
        StringBuilder stringBuilder =new StringBuilder();

        while (inputStreamReader.ready()){
            stringBuilder.append((char) inputStreamReader.read());
        }
        System.out.println(stringBuilder.toString());

        inputStreamReader.close();
        fileInputStream.close();

        Map<Integer,Integer> map = new HashMap<Integer, Integer>(17);
        for(int i=0;i<5;i++){
            map.put(i,i);
        }
        System.out.println(map.size());
        for(int i=0;i<5;i++){
            System.out.println(map.get(i));
        }
    }
}
