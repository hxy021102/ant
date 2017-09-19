package com.mobian.util;

import java.io.*;

/**
 * Created by john on 16/4/3.
 */
public class FileUtil {
    public static void updateCommonJs() {
        String filePath = getWebRootPath();
        filePath += File.separator + "shopweb" + File.separator + "js" + File.separator + "common.js";
        String str; //原有txt内容
        String s1 = new String();//内容更新
        try {
            File f = new File(filePath);
            if (f.exists()) {
                System.out.print("文件存在");
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedReader input = new BufferedReader(new FileReader(f));

            while ((str = input.readLine()) != null) {
                if (str.indexOf("var server_url") > -1) continue;
                s1 += str + "\n";
            }
            input.close();
            String host = "var server_url = " + ConfigUtil.get("server_url")+";\n";
            s1 = host +s1;
            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(s1);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static String getWebRootPath() {
        String path = FileUtil.class.getResource("/").getFile();
        if (path.indexOf("WEB-INF") > 0) {
            path = path.substring(0, path.indexOf("WEB-INF") - 1);
        }
        path.replace("/", File.separator);
        return path;
    }
}
