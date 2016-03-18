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
public abstract class UpLoader {
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
    }


    public abstract void init();



    public abstract void upload(String content,String targetName) ;
}
