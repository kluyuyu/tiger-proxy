package org.tiger.proxy.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by liufish on 16/8/3.
 */
public class FileUtil {


    public static String readAsStrByUtf8(String path) {

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            reader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
            String tempStr = null;
            while ((tempStr = reader.readLine()) != null) {
                buffer.append(tempStr);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {

            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        }

        return buffer.toString();
    }
}
