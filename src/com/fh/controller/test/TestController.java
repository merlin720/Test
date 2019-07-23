package com.fh.controller.test;

import com.fh.entity.TestVo;
import com.fh.entity.UpdateModel;
import com.fh.util.FileCharsetConverter;
import com.fh.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.*;

/**
 * Created by RX-78 on 2019/7/16.
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {
    /**
     * 返回文件内容的接口
     * @param testVo
     * @return
     */
    @RequestMapping(value = "queryFileContent", method = RequestMethod.POST)
    @ResponseBody//TestVo testVo
    public Map<String, Object> queryFileContent(@RequestBody TestVo testVo) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            System.out.println("1111111");
            List<String> list = new ArrayList<String>();
            System.out.println(testVo.getFilePath());
//            System.out.println(params.get("filePath"));
            //testVo.getFilePath() 指定的文件路径
            File srcFile = new File(testVo.getFilePath());
            try {
                FileInputStream fileInputStream = new FileInputStream(srcFile);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String str = "";
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                while ((str = bufferedReader.readLine()) != null) {
                    list.add(str);
                }


                modelMap.put("message", "获取结果");
                modelMap.put("code", "0");
                modelMap.put("data", list);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("message", "系统内部错误");
            modelMap.put("code", "1");
            modelMap.put("data", null);
        }


        return modelMap;
    }

    /**
     * 修改文件内容的接口
     * @param model
     * @return
     */
    @RequestMapping(value = "updateFileContent", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateFileContent(@RequestBody UpdateModel model) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            String key = model.getKey1();
            String path = model.getFilePath();
            File srcFile = new File(path);
            List<String> list = new ArrayList<String>();
            FileInputStream fileInputStream = new FileInputStream(srcFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = "";
            StringBuffer buf = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                System.out.println(str);
                if (str.contains(model.getKey1())) {
                    str = key + ":" + model.getValue();

                }
                System.out.println(str);
                buf.append(str);
                buf = buf.append(System.getProperty("line.separator"));
            }
            FileOutputStream fos = new FileOutputStream(srcFile);
            PrintWriter pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            pw.close();

            System.out.println(key);
            modelMap.put("message", "获取结果");
            modelMap.put("code", "0");
            modelMap.put("data", null);

        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("message", "系统内部错误");
            modelMap.put("code", "1");
            modelMap.put("data", null);
        }
        return modelMap;
    }
    @RequestMapping(value = "queryFiles", method = RequestMethod.POST)
    @ResponseBody//TestVo testVo
    public Map<String, Object> queryFiles(@RequestBody TestVo testVo) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            System.out.println("1111111");
            List<String> list = new ArrayList<String>();
            System.out.println(testVo.getFilePath());
            try {
                modelMap.put("message", "获取结果");
                modelMap.put("code", "0");
                modelMap.put("data", FileUtil.getFilesInPath(testVo.getFilePath()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("message", "系统内部错误");
            modelMap.put("code", "1");
            modelMap.put("data", null);
        }


        return modelMap;
    }

}