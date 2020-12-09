/**
 * @FileName: UploadController
 * @Author: code-fusheng
 * @Date: 2020/5/4 20:29
 * Description: 文件上传控制类
 */
package xyz.fusheng.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.fusheng.model.common.aspect.annotation.Log;
import xyz.fusheng.model.common.aspect.enums.BusinessType;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.common.utils.UploadService;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 文件上传
     *
     * @param file
     * @return url 文件地址
     */
    @RequestMapping("/uploadImage")
    @Log(title = "文件上传", businessType = BusinessType.OTHER)
    public Result<String> uploadImage(MultipartFile file) throws IOException {
        String url = uploadService.uploadImageToAliyunOss(file);
        return new Result<>("操作成功: 文件上传！", url);
    }
}
