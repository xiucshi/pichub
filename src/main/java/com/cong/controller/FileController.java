package com.cong.controller;

import com.cong.annotation.UserInfo;
import com.cong.bean.File;
import com.cong.bean.User;
import com.cong.service.FileService;
import com.cong.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Date : 2019/10/28
 * @Author : xiuc_shi
 **/
@RestController
@Slf4j
@Api(value = "文件操作api")
public class FileController {

    @Autowired
    private FileService fileService;
    private final String VIDEO_SAVE_PATH = "src\\main\\resources\\static\\videos";
    private final String PICTURE_SAVE_PATH = "src\\main\\resources\\static\\images";
    @Autowired
    @Qualifier("mailContainer")
    private List<String> mailContainer;

    /**
     * 文件上传接口，接受图片和MP4格式
     * 如果是图片，则先压缩图片，构建File对象存到mysql，原图存到本地，
     * 视频不直接存到mysql，而是将文件名存到mysql，视频存到本地
     * @param user
     * @param files
     * @param category
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Transactional
    @ApiOperation(value = "文件上传")
    public ResponseEntity<String> upload(@UserInfo User user, @RequestParam("files") MultipartFile[] files, @RequestParam("category") String category) throws IOException {
        if( !user.getUsername().equals("admin")){
            return ResponseEntity.ok().body("抱歉,只有管理员可以上传文件！");
        }
        ResponseEntity<String> res;
        try{
            for(MultipartFile file : files){
                String[] split = file.getOriginalFilename().split("\\.");
                String fileFormat = split[split.length-1];
                //图片
                if (fileFormat.equals("jpg") || fileFormat.equals("png") || fileFormat.equals("gif")){
                    if (FileUtils.save(PICTURE_SAVE_PATH,file)) {
                        InputStream inputStream = file.getInputStream();
                        inputStream = FileUtils.compressPicture(inputStream);
                        File picture = File.builder().content(inputStream).createTime(new Timestamp(new Date().getTime())).
                                fileFormat(fileFormat).filename(file.getOriginalFilename()).category(category).build();
                        fileService.upload(picture);
                    }
                } else {
                    if (FileUtils.save(VIDEO_SAVE_PATH, file)) {
                        InputStream videoCover = new FileInputStream(new java.io.File(VIDEO_SAVE_PATH,"video_cover.jpg"));
                        File video = File.builder().content(videoCover).createTime(new Timestamp(new Date().getTime())).fileFormat(fileFormat).
                                filename(file.getOriginalFilename()).category("video").build();
                        fileService.upload(video);
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            log.debug(e.toString());
            log.info("upload pictures fail.");
            return ResponseEntity.status(500).body("有文件上传失败");
        }
        log.info("upload pictures success.");
        return ResponseEntity.ok().body("文件上传成功");
    }

    /**
     * 根据图片名返回图片流，produces 提示浏览器这是图片，直接显示
     * @param request
     * @param filename
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/picture",produces = {MediaType.IMAGE_JPEG_VALUE , MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    @ApiOperation(value = "返回存到mysql中的压缩图片")
    public ResponseEntity<Object> previewPicture(HttpServletRequest request, @RequestParam("filename") String filename) throws IOException {
        File file = fileService.getFileByFilename(filename);
        if (file == null) {
            return ResponseEntity.ok().body("<h1>文件 " + filename + " 不存在.</h1>");
        }
        InputStream img = file.getContent();
        byte[] bytes = new byte[img.available()];
        img.read(bytes);
        String client = request.getHeader("User-Agent").split("\\)")[0] + ")";
       // log.info(request.getRemoteAddr() + "  " + client + "  viewed the file " + filename );
        return ResponseEntity.ok().body(bytes);
    }

    @GetMapping(value = "/video")
    public ResponseEntity<String> viewVideo(HttpServletRequest request, @RequestParam("filename") String filename){
        String client = request.getHeader("User-Agent").split("\\)")[0] + ")";
        log.info(request.getRemoteAddr() + "  " +client + "  viewed the video " + filename );
        return ResponseEntity.ok().body("<video width=\"320\" height=\"240\" controls=\"controls\" preload=\"auto\">" +
                                            "<source src=\"http://bigfat.club/videos/" + filename + "\" type=\"video/mp4\" />"
                                        + "</video>");
    }

    /**
     * 根据category返回该category下的所有图片名称
     * @param category
     * @return
     */
    @GetMapping("/filenames")
    @ApiOperation(value = "根据category返回所有文件名")
    public ResponseEntity<Object> filenames(@RequestParam("category") String category){
        List<String> filenames = fileService.getFilenamesByCategory(category);
        if (filenames == null || filenames.size() == 0){
            return ResponseEntity.ok().body("该目录不存在文件：" + category);
        }
      //  List<String> pictures = filenames.stream().filter(filename -> !filename.endsWith(".mp4")).collect(Collectors.toList());
        return ResponseEntity.ok().body(filenames);
    }

    /**
     * 根据用户信息，返回该用户拥有的所有category
     * @param user
     * @return List
     */
    @GetMapping("/category")
    public ResponseEntity<Object> category(@UserInfo User user){
     //   user = User.builder().category("Taylor Swift,xiaoqiai").build();
        List<String> category = Stream.of(user.getCategory().split(",")).collect(Collectors.toList());
        if (category == null || category.size() == 0){
            return ResponseEntity.ok().body("找不到目录");
        }
        return ResponseEntity.ok().body(category);
    }

//    /**
//     * 返回某一category的所有压缩图片
//     * @param category
//     * @return
//     * @throws IOException
//     */
//    @GetMapping(value = "/all/{category}")
//    public ResponseEntity<Object> views(@PathVariable String category) throws IOException {
//        List<String> filenames = fileService.getFilenamesByCategory(category);
//        if(filenames == null || filenames.size() == 0){
//            return ResponseEntity.ok().body("该目录不存在文件：" + category);
//        }
//        List<String> pictures = filenames.stream().filter(filename -> filename.endsWith(".jpg") || filename.endsWith(".png") || filename.endsWith(".gif"))
//                .collect(Collectors.toList());
//        return ResponseEntity.ok().body(filenames);
//    }

}
