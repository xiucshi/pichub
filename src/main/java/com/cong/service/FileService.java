package com.cong.service;

import com.cong.bean.File;
import com.cong.mapper.FileMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Date : 2019/10/28
 * @Author : xiuc_shi
 **/
@Service
public class FileService {
    @Resource
    private FileMapper fileMapper;
    public String upload(File file){
        if(fileMapper.upload(file) > 0){
            return "upload success";
        }else{
            return "upload fail";
        }
    }

    public File getFileByFilename(String filename){
        File file = fileMapper.getFileByFilename(filename);
        return file;
    }

    /*
        残缺版，直接将List中的内容编写成<a></a>返回
     */
    public List<String> getFilenamesByCategory(String category){
        List<String> filenames = fileMapper.getFilenamesByCategory(category);
        return filenames;
    }

    public List<String> getCategorys(){
        List<String> categorys = fileMapper.getCategorys();
        return categorys;
    }

    public List<File> getFilesByCategory(String category){
        List<File> files = fileMapper.getFilesByCategory(category);
        return files;
    }


}
