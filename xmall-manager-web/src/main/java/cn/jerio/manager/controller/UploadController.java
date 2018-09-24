package cn.jerio.manager.controller;

import cn.jerio.entity.Result;
import cn.jerio.util.FastDFSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Jerio on 2018/9/22
 */
@RestController
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Value("${FILE_SERVER_URL}")
    private String file_server_url;

    @RequestMapping("/upload")
    public Result upload(MultipartFile file){

        String originalFilename = file.getOriginalFilename();//获取文件名
        String extName=originalFilename.substring( originalFilename.lastIndexOf(".")+1);//得到扩展名

        try {

            String fileId = FastDFSClient.uploadFile(file.getBytes(), extName);
            String url=file_server_url+fileId;//图片完整地址
            return Result.success(url);
        } catch (Exception e) {
            logger.error("文件上传失败",e);
            return Result.fail("上传失败");
        }
    }
}
