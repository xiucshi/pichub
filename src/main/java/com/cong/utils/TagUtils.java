package com.cong.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date : 2019/11/4
 * @Author : xiuc_shi
 **/
public class TagUtils {
    public final static String IMG_TAG = "<img id=\"%d\"  height=\"300px\" src = \" http://bigfat.club/picture?filename=%s\" name = \"%s\" />\n";
    public final static String VIDEO_A_TAG = "<a id=\"%d\" href = \" http://bigfat.club/video?filename=%s\" />%s<br>\n";
    public final static String PICTURE_A_TAG = "%d  <a href = \" http://bigfat.club/picture?filename=%s\" style=\" color:blue; font-size:18px;\" >%s</a><br>";
    public final static String CATEGORY_TAG = "%d  <a href = \" http://bigfat.club/filenames/%s\" style=\" color:blue; font-size:18px;\" >%s</a><br>";
    public final static String CATEGORY_VIEW_ALL_TAG = "%d  <a href = \" http://bigfat.club/all/%s\" style=\" color:blue; font-size:18px;\" >显示%s的全部照片</a><br>";
    public static List<String> convertToTag(List<String> names, String targetUrl){
        List<String> hrefs = new ArrayList<>(names.size());
        int index = 1;
        for(String name : names){
            hrefs.add(String.format(targetUrl,index++, name, name));
        }
        return hrefs;
    }



}
