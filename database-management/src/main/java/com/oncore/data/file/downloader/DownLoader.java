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
public class DownLoader {
    CommonConfigure commonConfigure;
    String ACCESS_KEY;
    String SECRET_KEY;
    String domain;
    Auth auth;
    Log log = LogFactory.getLog(DownLoader.class);
    @Autowired
    public DownLoader(CommonConfigure commonConfigure) {
        this.commonConfigure = commonConfigure;
        this.ACCESS_KEY = commonConfigure.getQiniu_access_key();
        this.SECRET_KEY = commonConfigure.getQiniu_secret_key();
        this.domain = commonConfigure.getQiniu_down_load_domain();
        this.auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    }

//    public DownLoader(){
//        this.ACCESS_KEY =   "Tx1BTcN2PaZtTQJf0epmNIxymz-rnyl8YeKLmNDZ";
//        this.SECRET_KEY="YzxGFjp6BfO_5dtFsMpBeS4qLbIKYiDcCbrjDJzw";
//        this.domain="7xrljn.com1.z0.glb.clouddn.com";
//        this.auth= Auth.create(ACCESS_KEY, SECRET_KEY);
//    }
    //return download url
    public String getDownloadUrl(String key) {
        String URL = "http://" + this.domain + "/" + key;
        //调用privateDownloadUrl方法生成下载链接,第二个参数可以设置Token的过期时间
        String downloadRUL = auth.privateDownloadUrl(URL, 3600);
//        log.info("created url " + downloadRUL);
        System.out.println(downloadRUL);
        return downloadRUL;
    }

    public String getReportContent(String targetName) throws IOException {
        String tmp = commonConfigure.getBaseDir()+"/tmp/"+targetName;
        File file = new File(tmp);
        if(!file.exists()){
            return null;
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String tmp_str = null;
        StringBuffer stringBuffer = new StringBuffer();
        while((tmp_str = bufferedReader.readLine())!=null){
            stringBuffer.append(tmp_str);
        }
        return stringBuffer.toString();
    }

//    public static void main(String[] arg){
//        new DownLoader().getDownloadUrl("dd");
//    }

}
