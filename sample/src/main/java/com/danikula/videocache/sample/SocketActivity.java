package com.danikula.videocache.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import com.danikula.videocache.KLog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SocketActivity extends FragmentActivity implements CacheListener {

    private InputStream inputStream;
    private HttpURLConnection httpURLConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        findViewById(R.id.btStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            String testUrl = "http://s2.wmlives.com/24f9d7e62706fc5c89a45cb7565a6714/5a71aa21/video/m/201801251221218lbYo0twk1G.mp4";
                            HttpProxyCacheServer proxy = App.getProxy(SocketActivity.this);
                            proxy.registerCacheListener(SocketActivity.this, testUrl);
                            String proxyUrl = proxy.getProxyUrl(testUrl);
                            URL url = new URL(proxyUrl);
                            httpURLConnection = (HttpURLConnection) url.openConnection();
                            httpURLConnection.setRequestMethod("GET");
                            httpURLConnection.setRequestProperty("Charset", "UTF-8");
                            httpURLConnection.setConnectTimeout(5000);
                            httpURLConnection.setReadTimeout(8000);
                            int code = httpURLConnection.getResponseCode();
                            if (code == 200) {
                                inputStream = httpURLConnection.getInputStream();
                                byte[] buffer = new byte[8192];
                                int allCount = 0;
                                int count;
                                while ((count = inputStream.read(buffer)) != -1) {
                                    allCount += count;
                                    KLog.i("=====读取文件大小：" + allCount);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            KLog.i("=====读取文件出错：" + e.getMessage());
                        } finally {
                        }
                    }
                }.start();
            }
        });

        findViewById(R.id.btStop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onCacheAvailable(File cacheFile, String url, int percentsAvailable) {

    }
}
