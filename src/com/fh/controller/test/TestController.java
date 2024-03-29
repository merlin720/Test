package com.fh.controller.test;

import com.fh.entity.SettingModel;
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
     *
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
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "updateFileContent", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateFileContent(@RequestBody UpdateModel model) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            String key = model.getKey1();
            String path = model.getFilePath();
            File srcFile = new File(path);
            List<String> list = new ArrayList<String>();
            fileInputStream = new FileInputStream(srcFile);
            inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = "";
            StringBuffer buf = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                System.out.println(str);
                if (str.contains(model.getKey1())) {
                    if ("1".equals(model.getNeedAddF())) {
                        str = key + model.getSignal() + model.getValue() + ";";
                    } else {
                        str = key + model.getSignal() + model.getValue();
                    }

                }
                System.out.println(str);
                buf.append(str);
                buf = buf.append(System.getProperty("line.separator"));
            }
            fos = new FileOutputStream(srcFile);
            pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            pw.close();
            fos.close();
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
            System.out.println(key);
            modelMap.put("message", "获取结果");
            modelMap.put("code", "0");
            modelMap.put("data", null);

        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("message", "系统内部错误");
            modelMap.put("code", "1");
            modelMap.put("data", null);
            if (null != pw) {
                pw.flush();
                pw.close();
            }

            try {
                if (null != fos) {
                    fos.close();
                }
                if (null != bufferedReader) {
                    bufferedReader.close();
                }
                if (null != inputStreamReader) {
                    inputStreamReader.close();
                }
                if (null != fileInputStream) {
                    fileInputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        return modelMap;
    }

    /**
     * 获取文件夹下所有文件的接口
     *
     * @param testVo
     * @return
     */
    @RequestMapping(value = "queryFiles", method = RequestMethod.POST)
    @ResponseBody//TestVo testVo
    public Map<String, Object> queryFiles(@RequestBody TestVo testVo) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            System.out.println("1111111");
            List<String> list = new ArrayList<String>();
            FileUtil.list.clear();
            list = FileUtil.getFilesInPath(testVo.getFilePath());
            System.out.println(testVo.getFilePath());
            System.out.println(testVo.getEndStr());
            try {
                modelMap.put("message", "获取结果");
                modelMap.put("code", "0");
                modelMap.put("data", list);
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

    @RequestMapping(value = "querySettingFiles", method = RequestMethod.POST)
    @ResponseBody//TestVo testVo
    public Map<String, Object> querySettingFiles(@RequestBody SettingModel settingModel) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            System.out.println("1111111");
            List<String> list = new ArrayList<String>();
            FileUtil.list.clear();
            list = FileUtil.getFilesInPath(settingModel.getFilePath(), settingModel.getEndStr());
            try {
                modelMap.put("message", "获取结果");
                modelMap.put("code", "0");
                modelMap.put("data", list);
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

    /**
     * 获取某个路径下的文件夹
     * @param testVo
     * @return
     */
    @RequestMapping(value = "queryFilesInPath", method = RequestMethod.POST)
    @ResponseBody//TestVo testVo
    public Map<String, Object> queryFilesInPath(@RequestBody TestVo testVo) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            System.out.println("1111111");
            List<String> list = new ArrayList<String>();
            FileUtil.list.clear();
            File file = new File(testVo.getFilePath());
            File[] array = file.listFiles();
            for (File file1 : array) {
                if (file1.isDirectory()) {
                    list.add(file1.getPath());
                }
            }
            try {
                modelMap.put("message", "获取结果");
                modelMap.put("code", "0");
                modelMap.put("data", list);
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