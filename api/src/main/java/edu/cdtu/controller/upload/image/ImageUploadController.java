package edu.cdtu.controller.upload.image;

import edu.cdtu.utils.ResultUtils;
import edu.cdtu.entity.ResultVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/upload")
public class ImageUploadController {
    @Value("${web.uploadpath}")
    private String webUploadpath;

    @RequestMapping("/uploadImage")
    public ResultVo uploadImage(@RequestParam("file") MultipartFile file){
        String url = "";
        // 图片原名
        String fileName = file.getOriginalFilename();
        // 文件扩展名
        String fileExtensionName = fileName.substring(fileName.indexOf("."));
        // 新的文件名，避免重名覆盖
        String newName = UUID.randomUUID().toString()+fileExtensionName;
        // 图片上传路径
        String path = webUploadpath;
        // 图片上传的目录对象
        File fileDir = new File(path);
        // 如果路径文件不存在，创建文件并设置可编辑
        if(!fileDir.exists()){
            fileDir.mkdirs();
            fileDir.setWritable(true);
        }
        // 创建新上传的文件
        File targetFile = new File(path, newName);
        try{
            // 把接口收到的文件写入到本地文件
            file.transferTo(targetFile);
            url ="/"+targetFile.getName();
        }catch (Exception e){
            return null;
        }
        return ResultUtils.success("上传成功", "/images"+url);
    }
}
