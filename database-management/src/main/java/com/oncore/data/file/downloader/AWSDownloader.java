package com.oncore.data.file.downloader;

import com.oncore.common.configure.CommonConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by steve on 3/17/16.
 */
@Component("awsDownloader")
public class AWSDownloader extends DownLoader{
    @Autowired
    public AWSDownloader(CommonConfigure commonConfigure) {
        super(commonConfigure);
    }

    @Override
    public String getDownloadUrl(String key) {

        return null;
    }

    @Override
    public String getReportContent(String targetName) throws IOException {
        return null;
    }
}
