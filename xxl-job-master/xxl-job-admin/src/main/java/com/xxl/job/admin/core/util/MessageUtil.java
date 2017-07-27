package com.xxl.job.admin.core.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;


public class MessageUtil {
	
	public static final String JSON_CONTENT = "application/json;charset=UTF-8";
	
	public static final String FORM_CONTENT = "application/x-www-form-urlencoded";
	
	private static String messUrl;
	private static String messAppId;

	static{
		messUrl = PropertiesUtil.getString("xxl.job.message.url");
		messAppId=PropertiesUtil.getString("xxl.job.message.appid");
	}
	
	private MessageUtil() {}
	
	public static String sendMessage(String phone,String content) throws Exception {
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(ssl()).build();
		try {
			HttpGet get = new HttpGet(messUrl+"?mobile="+phone+"&text="+content);
			get.setHeader("Authorization", messAppId);
			get.setConfig(requestConfig());
			CloseableHttpResponse res = httpclient.execute(get);
			try {
				return EntityUtils.toString(res.getEntity(), "UTF-8");
			} finally {
				res.close();
			}
		} finally {
			 httpclient.close();
		}
	}
	


	

	
	private static SSLConnectionSocketFactory ssl() throws Exception {
		return new SSLConnectionSocketFactory(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
			
			@Override
			public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				return true;
			}
		}).build());
	}

	private static RequestConfig requestConfig() {
		return RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(5000).build();
	}

}
