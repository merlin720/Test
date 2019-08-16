package com.fh.controller.test;

import com.fh.entity.*;
import com.fh.util.FileUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by RX-78 on 2019/7/16.
 */
@Controller
@RequestMapping(value = "/setting")
public class SettingController {
    /**
     * @param testVo
     * @return
     */
    @RequestMapping(value = "getCurrentSystem", method = RequestMethod.POST)
    @ResponseBody//TestVo testVo
    public Map<String, Object> getCurrentSystem(@RequestBody PathAndFilePath testVo) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            System.out.println("1111111");
            List<String> list = new ArrayList<String>();
            System.out.println(testVo.getFilePath());
            File srcFile = new File(testVo.getSystemFilePath());
            try {
                FileInputStream fileInputStream = new FileInputStream(srcFile);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String str = "";
                while ((str = bufferedReader.readLine()) != null) {
                    String[] split = str.split("=");
                    if (split.length > 1 && testVo.getKey().equals(split[0])) {
                        list.add(split[1]);
                    }

                }
                for (String s : list) {
                    System.out.println(s);
                }
                List<ModelSetting> list1 = new ArrayList<ModelSetting>();
                FileUtil.list.clear();
                File file = new File(testVo.getFilePath());
                File[] array = file.listFiles();
                for (File file1 : array) {
                    if (file1.isDirectory()) {
                        ModelSetting modelSetting = new ModelSetting();
                        modelSetting.setName(file1.getPath());
                        if (list.size() == 1) {
                            modelSetting.setSelected(file1.getName().equals(list.get(0).substring(0, list.get(0).length() - 1)));
                        }
                        list1.add(modelSetting);
                    }
                }
                modelMap.put("message", "获取结果");
                modelMap.put("code", "0");
                modelMap.put("data", list1);
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
     * @param testVo
     * @return
     */
    @RequestMapping(value = "addFile", method = RequestMethod.POST)
    @ResponseBody//TestVo testVo
    public Map<String, Object> addFile(@RequestBody SettingAddBean testVo) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            System.out.println("1111111");
            List<String> list = new ArrayList<String>();
            System.out.println(testVo.getSourcePath());
            System.out.println(testVo.getDestPath());

            try {
                FileUtil.copyDirectiory(testVo.getSourcePath(), testVo.getDestPath());
                modelMap.put("message", "获取结果");
                modelMap.put("code", "0");
                modelMap.put("data", "创建成功");
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
     * @param testVo
     * @return
     */
    @RequestMapping(value = "deleteFile", method = RequestMethod.POST)
    @ResponseBody//TestVo testVo
    public Map<String, Object> deleteFile(@RequestBody TestVo testVo) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            System.out.println("1111111");
            List<String> list = new ArrayList<String>();
            System.out.println(testVo.getFilePath());
            File srcFile = new File(testVo.getFilePath());

            FileUtil.deleteFile(srcFile);
            modelMap.put("message", "获取结果");
            modelMap.put("code", "0");
            modelMap.put("data", "创建成功");

        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("message", "系统内部错误");
            modelMap.put("code", "1");
            modelMap.put("data", null);
        }


        return modelMap;
    }

    /**
     * @param testVo
     * @return
     */
    @RequestMapping(value = "getProcessSeting", method = RequestMethod.POST)
    @ResponseBody//TestVo testVo
    public Map<String, Object> getProcessSeting(@RequestBody TestVo testVo) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            System.out.println("1111111");

            HashMap<String, String> map = new HashMap<String, String>();
            System.out.println(testVo.getFilePath());

            FileInputStream fileInputStream = new FileInputStream(testVo.getFilePath());
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = "";
            String target = "";
            while ((str = bufferedReader.readLine()) != null) {
                String[] split = str.split("=");
                if (split.length > 1 && testVo.getKey().equals(split[0])) {
                    target = split[1].substring(0, split[1].length() - 1);
                }
            }
            String srcFile = testVo.getBasePath() + target;
            File file = new File(srcFile, testVo.getFileName());
            FileInputStream fileInputStream11 = new FileInputStream(file);
            InputStreamReader inputStreamReader1 = new InputStreamReader(fileInputStream11, "utf-8");
            BufferedReader bufferedReader1 = new BufferedReader(inputStreamReader1);
            String str1 = "";
            map.put("currentPath", file.getPath());
            while ((str1 = bufferedReader1.readLine()) != null) {
                System.out.println(str1);
                String[] split = str1.split("=");
                if (split.length > 1) {
                    map.put(split[0], split[1].substring(0, split[1].length() - 1));
                }

            }
            modelMap.put("message", "获取结果");
            modelMap.put("code", "0");
            modelMap.put("data", map);

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
    public Map<String, Object> updateFileContent(@RequestBody ProcessUpdateModel model) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        FileOutputStream fos = null;
        PrintWriter pw = null;

        try {
            String map = model.getMap();
            System.out.println(map);
            Type type = new TypeToken<HashMap<String, String>>() {
            }.getType();
            Gson gson = new Gson();
            HashMap<String, String> hashMap = gson.fromJson(map, type);
            String path = model.getFilePath();
            File srcFile = new File(path);
            fileInputStream = new FileInputStream(srcFile);
            inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = "";
            StringBuffer buf = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                String split[] = str.split("=");
                if (split.length>1){
                    if (null != hashMap.get(split[0])) {
                        str = split[0] + "=" + hashMap.get(split[0]) + ";";
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

}