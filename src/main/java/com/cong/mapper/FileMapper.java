package com.cong.mapper;

import com.cong.bean.File;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @Date : 2019/11/3
 * @Author : xiuc_shi
 **/
@Mapper
public interface FileMapper {
    @Insert("insert into beauty_tbl (content, filename, category, file_format, create_time) values (#{content},#{filename},#{category}, #{fileFormat}, #{createTime})")
    int upload(File file);

    @Select("select * from beauty_tbl where filename = #{filename}")
    File getFileByFilename(String filename);

    @Select("select filename from beauty_tbl where category = #{category}")
    List<String> getFilenamesByCategory(String category);

    @Select("select distinct category from beauty_tbl")
    List<String> getCategorys();

    @Select("select * from beauty_tbl where category = #{category}")
    List<File> getFilesByCategory(String category);
}
