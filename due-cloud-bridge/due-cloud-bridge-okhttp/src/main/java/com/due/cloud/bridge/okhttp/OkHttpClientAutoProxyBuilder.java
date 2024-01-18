package com.due.cloud.bridge.okhttp;

import com.due.cloud.bridge.okhttp.config.HttpClientAutoProxyConfig;
import com.due.cloud.bridge.okhttp.interceptor.CustomizedNetInterceptor;
import com.due.cloud.bridge.okhttp.interceptor.LoggerInterceptor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

@Slf4j
@Getter
public class OkHttpClientAutoProxyBuilder {

	private final HttpClientAutoProxyConfig config;

//	public OkHttpClientAutoProxyBuilder() {
//		this(null);
//	}

	public OkHttpClientAutoProxyBuilder(HttpClientAutoProxyConfig config) {
		this.config = config;
	}

	public OkHttpClient build() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder()
				.sslSocketFactory(sslSocketFactory(), x509TrustManager())
				.hostnameVerifier(hostnameVerifier())
				.addNetworkInterceptor(new CustomizedNetInterceptor())// 网络拦截器
				.addInterceptor(new LoggerInterceptor()) // 日志拦截器
				.connectTimeout(100, TimeUnit.SECONDS)
				.readTimeout(100, TimeUnit.SECONDS)
				.writeTimeout(100, TimeUnit.SECONDS)
				.connectionPool(new ConnectionPool(200, 10, TimeUnit.SECONDS))
				.retryOnConnectionFailure(false);
		if (this.isProxyEnable()) {
			int port = this.config.getProxyPort();
			String host = this.config.getProxyHost();
			builder.proxy(new Proxy(Type.HTTP, new InetSocketAddress(host, port)));
			log.info("Enable http proxy {}:{}", host, port);
			if (this.isCredentialEnable()) {
				String userName = this.config.getUsername();
				String passWord = this.config.getPassword();
				Authenticator.setDefault(new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication(userName, passWord.toCharArray());
					}
				});
				log.info("Enable http proxy credential {}:{}", userName, "******");
			}
		} else {
			log.info("Disable http proxy");
		}

		return builder.build();
	}

	public X509TrustManager x509TrustManager() {
		return new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
				//
			}

			@Override
			public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
				//
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}
		};
	}

	public SSLSocketFactory sslSocketFactory() {
		try {
			//信任任何链接
			SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
			sslContext.init(null, new TrustManager[] {x509TrustManager()}, new SecureRandom());
			return sslContext.getSocketFactory();
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			log.error("SSL设置异常", e);
		}
        return null;
	}

	public HostnameVerifier hostnameVerifier() {
		return (s, sslSession) -> true;
	}

	private boolean isProxyEnable() {
		if (null == this.config || !this.config.isProxyEnable()) {
			return false;
		}
		return true;
	}

	private boolean isCredentialEnable() {
		return this.isProxyEnable() && this.config.isCredentialEnable();
	}

}