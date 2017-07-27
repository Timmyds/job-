package com.xxl.job.utils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

public class HttpProxy {
	
	public static final String JSON_CONTENT = "application/json;charset=UTF-8";
	
	public static final String FORM_CONTENT = "application/x-www-form-urlencoded";
	
	private HttpProxy() {}
	
	public static String get(String url) throws Exception {
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(ssl()).build();
		try {
			HttpGet get = new HttpGet(url);

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
	
	public static String postJson(String url, String postData) throws Exception {
		return post(url, postData, JSON_CONTENT);
	}
	
	public static String postForm(String url, String postData) throws Exception {
		return post(url, postData, FORM_CONTENT);
	}
	
	public static String post(String url, String postData, String contentType) throws Exception {
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(ssl()).build();
		try {
			HttpPost post = new HttpPost(url);
//			post.setHeader("Accept", JSON_CONTENT);
			post.setHeader("Content-Type", contentType);
			post.setEntity(new StringEntity(postData, "UTF-8"));
			post.setConfig(requestConfig());
			CloseableHttpResponse response = httpclient.execute(post);
			try {
				// int statusCode = response.getStatusLine().getStatusCode();
				// if (statusCode >= 400) {
				// // 抛出网络错误异常
				// throw new IOException(statusCode + "");
				// }
				return EntityUtils.toString(response.getEntity(), "UTF-8");

			} finally {
				response.close();
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
