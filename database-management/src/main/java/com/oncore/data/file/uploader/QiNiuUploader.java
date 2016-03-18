package com.oncore.data.file.uploader;

import com.oncore.common.configure.CommonConfigure;
import com.qiniu.storage.UploadManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by steve on 3/17/16.
 */

@Component
public class QiNiuUploader extends UpLoader {
    UploadManager uploadManager = new UploadManager();

    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }
    @Autowired
    public QiNiuUploader(CommonConfigure commonConfigure) {
        super(commonConfigure);
    }

    @Override
    public void init() {

    }

    @Override
    public void upload(String content, String targetName){

    }
}
