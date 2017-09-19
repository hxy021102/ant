package com.mobian.thirdpart.wx;

import com.mobian.absx.F;
import com.mobian.util.Constants;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by wenming on 2016/8/17.
 */
public class DownloadMediaUtil {

    /**
     * 根据内容类型判断文件扩展名
     *
     * @param contentType 内容类型
     * @return
     */
    public static String getFileExtName(String contentType) {
        String fileEndWitsh = "";
        if ("image/jpeg".equals(contentType))
            fileEndWitsh = ".jpg";
        else if ("audio/mpeg".equals(contentType))
            fileEndWitsh = ".mp3";
        else if ("audio/amr".equals(contentType))
            fileEndWitsh = ".amr";
        else if ("video/mp4".equals(contentType))
            fileEndWitsh = ".mp4";
        else if ("video/mpeg4".equals(contentType))
            fileEndWitsh = ".mp4";
        return fileEndWitsh;
    }

    /**
     * 获取媒体文件
     * @param mediaId 媒体文件id
     * @param
     * */
    public static String downloadMedia(String realPath, String mediaId, String dirName) {
        if(F.empty(mediaId)) return null;

//        String filePath = null;
        // 拼接请求地址
        String requestUrl = WeixinUtil.getDownloadMediaUrl(mediaId);
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            conn.connect();

            Calendar calendar = Calendar.getInstance();
            if (!dirName.endsWith("/")) dirName += "/";

            dirName = dirName + calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);
            realPath += Constants.UPLOADFILE+"/"+dirName;
            // 根据内容类型获取扩展名
            String fileExt = DownloadMediaUtil.getFileExtName(conn.getHeaderField("Content-Type"));
            // 将mediaId作为文件名
            String fileName = mediaId + fileExt;
//            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
//            FileOutputStream fos = new FileOutputStream(new File(filePath));
//            byte[] buf = new byte[8096];
//            int size = 0;
//            while ((size = bis.read(buf)) != -1)
//                fos.write(buf, 0, size);
//            fos.close();
//            bis.close();
            FileUtils.copyInputStreamToFile(conn.getInputStream(), new File(realPath, fileName));
            conn.disconnect();
            //String info = String.format("下载媒体文件成功，filePath=" + filePath);
            //System.out.println(info);
            return Constants.UPLOADFILE+"/"+dirName+"/"+fileName;
        } catch (Exception e) {
            String error = String.format("下载媒体文件失败：%s", e);
            System.out.println(error);
        }
        return null;
    }
}
