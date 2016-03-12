package com.oncore.data.file.uploader;

import com.oncore.common.configure.CommonConfigure;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by steve on 3/6/16.
 */
@Component
public class UpLoader {
    String ACCESS_KEY;

    String SECRET_KEY;
    //upload space
    String bucketname;
    Auth auth;
    CommonConfigure commonConfigure;
    //set the  ACCESS_KEY and SECRET_KEY of qiniu
    @Autowired
    public UpLoader(CommonConfigure commonConfigure) {
        this.commonConfigure = commonConfigure;
        this.ACCESS_KEY = commonConfigure.getQiniu_access_key();
        this.SECRET_KEY = commonConfigure.getQiniu_secret_key();
        this.bucketname = commonConfigure.getQiniu_bucket_name();
        this.auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    }

    UploadManager uploadManager = new UploadManager();

    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    public void upload(String content,String targetName) throws IOException {

        try {
            //调用put方法上传

            File file = new File(commonConfigure.getBaseDir()+"/tmp/");
            if(!file.exists()){
                file.mkdirs();
            }
            file = new File(file.getAbsolutePath()+"/"+targetName);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
            Response res = uploadManager.put(file.getAbsoluteFile(), targetName, getUpToken());
            //打印返回的信息
//            file.delete();
            System.out.println(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
    }
//
//    public static void main(String args[]) throws IOException{
//        new UpLoader().upload();
//    }
}
