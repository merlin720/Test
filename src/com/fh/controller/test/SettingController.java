package com.fh.controller.test;

import com.fh.entity.*;
import com.fh.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
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
                copyDirectiory(testVo.getSourcePath(),testVo.getDestPath());
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

            deleteFile(srcFile);
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

    private static void copyFile(File sourcefile, File targetFile) throws IOException {

        // 新建文件输入流并对它进行缓冲
        FileInputStream input = new FileInputStream(sourcefile);

        // 新建文件输出流并对它进行缓冲
        FileOutputStream out = new FileOutputStream(targetFile);
        BufferedOutputStream outbuff = new BufferedOutputStream(out);

        // 缓冲数组
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = input.read(b)) != -1) {
            outbuff.write(b, 0, len);
        }

        //关闭文件
        outbuff.close();
        input.close();

    }

    private static void copyDirectiory(String sourceDir, String targetDir) throws IOException {

        // 新建目标目录
        (new File(targetDir)).mkdirs();

        // 获取源文件夹当下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();

        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new File(targetDir + File.separator + sourceFile.getName());
                copyFile(sourceFile, targetFile);

            }

            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + File.separator + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + File.separator + file[i].getName();

                copyDirectiory(dir1, dir2);
            }
        }

    }



    private static void deleteFile(File file) {
        // 判断传递进来的是文件还是文件夹,如果是文件,直接删除,如果是文件夹,则判断文件夹里面有没有东西
        if (file.isDirectory()) {
            // 如果是目录,就删除目录下所有的文件和文件夹
            File[] files = file.listFiles();
            // 遍历目录下的文件和文件夹
            for (File f : files) {
                // 如果是文件,就删除
                if (f.isFile()) {
                    System.out.println("已经被删除的文件:" + f);
                    // 删除文件
                    f.delete();
                } else if (file.isDirectory()) {
                    // 如果是文件夹,就递归调用文件夹的方法
                    deleteFile(f);
                }
            }
            // 删除文件夹自己,如果它低下是空的,就会被删除
            System.out.println("已经被删除的文件夹:" + file);
            file.delete();
        }

        // 如果是文件,就直接删除自己
        System.out.println("已经被删除的文件:" + file);
        file.delete();

    }
}