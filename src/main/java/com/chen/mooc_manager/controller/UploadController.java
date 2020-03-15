package com.chen.mooc_manager.controller;

import com.chen.mooc_manager.base.result.ResponseCode;
import com.chen.mooc_manager.base.result.Results;
import com.chen.mooc_manager.util.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Controller
@RequestMapping("upload")
public class UploadController {

    @PostMapping("/pictureUpload")
    @ResponseBody
    public Results<String> projectPictureUpload(@RequestParam(value = "projectImg",required = true) MultipartFile file){
        //将图片上传到服务器
        if(file.isEmpty()){
            return Results.failure(ResponseCode.FILE_EMPTY);
        }
        //原始文件名
        String originalFilename = file.getOriginalFilename();
        //文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        //图片名称为uuid+图片后缀防止冲突
        String fileName = UUID.randomUUID().toString()+"."+suffix;

        //文件保存路径
        //windows下的路径
        String filePath ="d:/pictureUpload/project/";

        try {
            //写入图片
            Boolean writePictureFlag = FileUtils.uploadFile(file.getBytes(),filePath,fileName);
            if(writePictureFlag == false){
                //上传图片失败
                return Results.failure(ResponseCode.UPLOAD_FAILURE);
            }
            //上传成功后，将可以访问的完整路径返回
            String fullImgPath = "/pictureUpload/project/"+fileName;
            return Results.success("上传图片成功",fullImgPath);
        } catch (Exception e) {
            e.printStackTrace();
            //上传图片失败
            return Results.failure(ResponseCode.UPLOAD_FAILURE);
        }
    }
}
