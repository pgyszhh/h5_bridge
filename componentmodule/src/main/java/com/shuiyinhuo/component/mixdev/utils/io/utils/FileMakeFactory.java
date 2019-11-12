package com.shuiyinhuo.component.mixdev.utils.io.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Zhiheng Su
 * @Data: 2017/3/13.
 * @Description: 文件辅助类
 */
public class FileMakeFactory {
    public static File makeFileByPath(String  path){
        File file=new File(path);
        return file;
    }

    public static ArrayList<File> listDirContainFilesByPath(String path){
        ArrayList<File> files =new ArrayList<File>();
        files.clear();
        File file=new File(path);
        if(file!=null){
            if(file.isDirectory()){
                File[] files1 = file.listFiles();
                if(!isEmptey(files1)&&files1.length!=0){
                    for(File f:files1){
                        files.add(f);
                    }
                }
            }
        }
        return files;
    }

    public static ArrayList<File> listDirContainFilesByFile(File file){
        ArrayList<File> files =new ArrayList<File>();
        files.clear();
        if(file!=null){
            if(file.isDirectory()){
                File[] files1 = file.listFiles();
                if(!isEmptey(files1)&&files1.length!=0){
                    for(File f:files1){
                        files.add(f);
                    }
                }
            }
        }
        return files;
    }


    public static boolean isEmptey(Object obj){
        if(obj==null){
            return true;
        }
        return false;
    }

    public static  boolean isDir(String path){
        File file = makeFileByPath(path);
        return file.isDirectory();
    }

    public static  boolean isDir(File file){
        return file.isDirectory();
    }

    /**
     * 获取给定路径下的所有文件的绝对路径，只能获得一层
     * @param mRootPath
     * @return
     */
    public static ArrayList<String > makeFilePath(String mRootPath){
       ArrayList<String > paths=new ArrayList<String>();
        paths.clear();
        File file = makeFileByPath(mRootPath);
        if (!isEmptey(file)){
            if(isDir(file)){
                ArrayList<File> files = listDirContainFilesByFile(file);
                if(!isEmptey(files) &&files.size()!=0){
                    for(File f:files){
                        if(!isDir(f)){
                            paths.add(f.getAbsolutePath());
                        }
                    }
                }
            }else {
                paths.add(mRootPath);
            }
        }
        return paths;
    }

    public static boolean isListEmpty(List<?> list){
        if(isEmptey(list)){
            return true;
        }
        return list.size()==0;
    }

    public static boolean isFileSizeZero(File file){
        return file.length()==0;
    }

    public static boolean isFileSizeZero(String  path){
        File file = makeFileByPath(path);
        if (isEmptey(file)){
            return true;
        }
        return file.length()==0;
    }

    public static boolean isFileExist(String path){
        File file = makeFileByPath(path);
        return file.exists();
    }
}
