package com.cong.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Date : 2019/11/11
 * @Author : xiuc_shi
 **/
public class FileUtils {

    public static InputStream compressPicture(InputStream file) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(file).scale(0.3f).outputQuality(0.5f).toOutputStream(outputStream);
        byte[] bytes = outputStream.toByteArray();
        return new ByteArrayInputStream(bytes);
    }

    public static boolean save(String savePath, MultipartFile file){
        try{
            String filename = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            Path path = Paths.get(savePath, filename);
            Files.write(path,bytes);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static InputStream getVideoCover(String savePath, String filename){
        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(savePath + "/" + filename);
        try {
            Frame frame = fFmpegFrameGrabber.grabFrame();
            opencv_core.IplImage image = frame.image;
            BufferedImage bufferedImage = image.getBufferedImage();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage,"jpg",outputStream);
            byte[] bytes = outputStream.toByteArray();
            return new ByteArrayInputStream(bytes);
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
