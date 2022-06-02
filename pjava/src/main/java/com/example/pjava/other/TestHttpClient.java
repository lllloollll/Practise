package com.example.pjava.other;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @ProjectName: Practise
 * @Package: com.example.pjava.other
 * @ClassName: TestHttpClient
 * @Description: java类作用描述
 * @Author: 毛毛虫
 * @CreateDate: 2022/5/27 3:24 下午
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/27 3:24 下午
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TestHttpClient {
    public static void main(String[] args) {
        String link="https://www.instagramsave.com//dl.php?url=https%3A%2F%2Fscontent.cdninstagram.com%2Fv%2Ft50.2886-16%2F282570631_506512867928126_6141407743253487864_n.mp4%3F_nc_ht%3Dscontent.cdninstagram.com%26_nc_cat%3D102%26_nc_ohc%3Du7jujBBXkAsAX9qAqs3%26tn%3DxoJMhXsWjRAeV4Jb%26edm%3DAP_V10EBAAAA%26ccb%3D7-5%26oe%3D6292A470%26oh%3D00_AT9Ff6Jyrgrba4rauDPAUe0KbK2aPJOPXjnBc7sfHeThqg%26_nc_sid%3D4f375e&type=video&token=186ddd08bea04fa94ff3374ed48798d91a4d4ab1";
        URL url;
        try {
            url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int length = connection.getContentLength();
            connection.g
            System.out.println("length:" + length);
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(link);
        try {
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            long fileSize = entity.getContentLength();
            System.out.println("fileSize:" + fileSize);
            client.getConnectionManager().shutdown();

        } catch (ClientProtocolException e) {
            System.out.println("error:" + e);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("error:" + e);
            e.printStackTrace();
        }

    }
}
