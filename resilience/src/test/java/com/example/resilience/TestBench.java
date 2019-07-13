package com.example.resilience;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @author : chenzhen
 * @date : 2019-07-13
 */
public class TestBench {

    private final static Logger logger = LoggerFactory.getLogger(TestBench.class);
    //模拟的并发量
    private static final int CONCURRENT_NUM = 42;
    private static String url = "http://localhost:8080/resilience2";
//    private static String url = "http://192.168.80.132:8080/guava";
//    private static String url = "http://192.168.80.132:8080/hystrix";
//    private static String url = "http://192.168.80.132:8080/hystrix2";
    private static CountDownLatch cdl = new CountDownLatch(CONCURRENT_NUM);

    public static void main(String[] args) {
        for (int i = 0; i < CONCURRENT_NUM; i++) {
            new Thread(new Demo()).start();
            cdl.countDown();
        }
    }

    public static class Demo implements Runnable{
        @Override
        public void run() {
            try {
                cdl.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //使用工具类发送http请求

            String json2 = requestPost(url, "");
            logger.info(new Date().getTime()+"::"+json2);
        }

    }

    /**
     * 发送POST请求，参数是JSON
     */
    public static String requestPost(String url, String jsonParam){
        logger.info("HttpTool.requestPost 开始 请求url：" + url + ", 参数：" + jsonParam);
        //创建HttpClient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建HttpPost对象
        HttpPost httpPost = new HttpPost(url);

        //配置请求参数
        RequestConfig requestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .setExpectContinueEnabled(true)
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();

        httpPost.setConfig(requestConfig);

        String respContent = null;

        //设置参数和请求方式
        StringEntity entity = new StringEntity(jsonParam,"UTF-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");

        httpPost.setEntity(entity);

        HttpResponse resp;
        try {
            //执行请求
            resp = client.execute(httpPost);
            if(resp.getStatusLine().getStatusCode() == 200) {
                HttpEntity responseObj = resp.getEntity();
                respContent = EntityUtils.toString(responseObj,"UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("HttpTool.requestPost 异常 请求url：" + url + ", 参数：" + jsonParam + "，异常信息：" + e);
        }
        logger.info("HttpTool.requestPost 结束 请求url：" + url + ", 参数：" + jsonParam + "");
        //返回数据
        return respContent;
    }
}
