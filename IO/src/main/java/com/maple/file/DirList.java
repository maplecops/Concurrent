package com.maple.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

public class DirList {

    private void filterCurrentFile() {
        File currentDir = new File(".");
        String[] fileList = currentDir.list();

        /**
         * 使用 FilenameFilter接口进行文件过滤
         *  也可以改写成lambda表达式格式
         */
        String[] javaFileList = currentDir.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (name.endsWith(".xml")) {
                    return true;
                }
                return false;
            }
        });

        currentDir.list((dir, name) ->{
            if( name.endsWith(".xml"))
                return true;
            return false;
        });

        Arrays.sort(javaFileList, String.CASE_INSENSITIVE_ORDER);
        for (String fileName : javaFileList
        ) {
            System.out.println(fileName);
        }
        ;

    }

    public static void main(String[] args) {
        DirList dirList = new DirList();
        dirList.filterCurrentFile();
    }

}
