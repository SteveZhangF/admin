package com.oncore.data.file.downloader;

import com.oncore.common.configure.CommonConfigure;
import com.qiniu.util.Auth;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Created by steve on 3/6/16.
 */
@Component
public abstract class DownLoader {
    CommonConfigure commonConfigure;
    Log log = LogFactory.getLog(DownLoader.class);
    @Autowired
    public DownLoader(CommonConfigure commonConfigure) {
        this.commonConfigure = commonConfigure;
    }
    public abstract String getDownloadUrl(String key);

    public abstract String getReportContent(String targetName) throws IOException;


}
