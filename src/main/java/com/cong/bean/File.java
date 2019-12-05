package com.cong.bean;

import lombok.*;

import java.io.InputStream;
import java.sql.Timestamp;



/**
 * @Date : 2019/11/3
 * @Author : xiuc_shi
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class File {
    private Integer id;
    private InputStream content;
    private String filename;
    private String category;
    private String fileFormat;
    private Timestamp createTime;
}
