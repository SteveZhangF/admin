package com.oncore.data.file.downloader;

import com.qiniu.util.Auth;

/**
 * Created by steve on 3/6/16.
 */
public class DownLoader {
    //设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = "Tx1BTcN2PaZtTQJf0epmNIxymz-rnyl8YeKLmNDZ";
    String SECRET_KEY = "YzxGFjp6BfO_5dtFsMpBeS4qLbIKYiDcCbrjDJzw";
    //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //构造私有空间的需要生成的下载的链接
    String URL = "http://bucketdomain/key";

    public void download() {
        //调用privateDownloadUrl方法生成下载链接,第二个参数可以设置Token的过期时间
        String downloadRUL = auth.privateDownloadUrl(URL, 3600);
        System.out.println(downloadRUL);
    }

    public static void main(String args[]) {
        new DownLoader().download();
    }


}
