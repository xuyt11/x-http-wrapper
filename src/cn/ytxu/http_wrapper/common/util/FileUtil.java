package cn.ytxu.http_wrapper.common.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by newchama on 16/3/29.
 */
public class FileUtil {

    public static String getClassFileName(String name) {
        String classFileName;
        if (name.contains(" ")) {// 包含有空格，需要先将空格转换为下划线，在转换为驼峰法
            classFileName = CamelCaseUtils.toCapitalizeCamelCase(
                    CamelCaseUtils.convertSpace2UnderLine(name));
        } else if (name.contains("_")) {// 包含有下划线，直接转换为驼峰法
            classFileName = CamelCaseUtils.toCapitalizeCamelCase(name);
        } else {// 首字母大写
            classFileName = name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        return classFileName;
    }

    /**
     * 生成分类的包名--->v5
     */
    public static String getCategoryPackageName(String name) {
        String classFileName = getClassFileName(name);
        return classFileName.substring(0, 1).toLowerCase() + classFileName.substring(1);
    }

    /**
     * 生成的包名:由下划线组成的全小写字符串
     */
    public static String getPackageName(String name) {
        String packageName;

        if (name.contains(" ")) {// 包含有空格，需要先将空格转换为下划线，在转换为驼峰法
            packageName = CamelCaseUtils.convertSpace2UnderLine(name);
        } else {
            packageName = name;
        }

        packageName = packageName.toLowerCase();
        return packageName;
    }

    public static Writer getWriter(String dirPath, String fileName, String charsetName) throws UnsupportedEncodingException, FileNotFoundException {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        Writer writer = new OutputStreamWriter(new FileOutputStream(new File(dir, fileName)), charsetName);
        return writer;
    }

    //将file转化成string
    public static String getContent(String filePath, String charset) throws IOException {
        //对一串字符进行操作
        BufferedReader reader = null;
        try {
            reader = getReader(filePath, charset);
            StringBuffer fileData = getBufferData(reader);
            return fileData.toString();
        } finally {
            closeReader(reader);
        }
    }

    public static BufferedReader getReader(String filePath, String charset) throws IOException {
        return  new BufferedReader(new InputStreamReader(new FileInputStream(filePath), charset));
    }

    private static StringBuffer getBufferData2(BufferedReader reader) throws IOException {
        StringBuffer fileData = new StringBuffer();
        char[] buf = new char[1024];
        int numRead;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        return fileData;
    }

    private static StringBuffer getBufferData(BufferedReader reader) throws IOException {
        StringBuffer fileData = new StringBuffer();
        String strLine;
        while (null != (strLine = reader.readLine())) {
            fileData.append(strLine).append("\n");
        }
        return fileData;
    }

    public static void closeReader(BufferedReader reader) {
        if (null == reader) {
            return;
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<String> getLineContents(String filePath, String charset) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), charset));
            List<String> contents = new ArrayList<>();
            String strLine;
            while (null != (strLine = reader.readLine())) {
                contents.add(strLine);
            }
            return contents;
        } finally {
            closeReader(reader);
        }
    }

}
