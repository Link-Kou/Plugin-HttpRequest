package com.Spring.api;

import com.Spring.TestProxy;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author lk
 * @version 1.0
 * @date 2020/6/6 10:24
 */
public class Disk {

    private static Logger logger = LoggerFactory.getLogger(TestProxy.class);

    public static boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            File file = new File("");
            String filePath = file.getCanonicalPath();
            System.out.println(filePath);
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(filePath + File.separator + "100MB-atlanta.bin");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    logger.info("file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
