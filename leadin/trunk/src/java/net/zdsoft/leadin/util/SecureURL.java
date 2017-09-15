package net.zdsoft.leadin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Convenience class for setting and retrieving cookies.
 */
public class SecureURL {
	private static final Logger logger = LoggerFactory.getLogger(SecureURL.class);

	public static String retrieve(String url) throws Exception {
		logger.debug("entering retrieve(" + url + ")");
		if (url.toLowerCase().indexOf("http:") < 0) {
			throw new MalformedURLException("非法的URL：" + url);
		}
		
		URL u = new URL(url);
		URLConnection uc = u.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection) uc;
		httpConn.setRequestProperty("Connection", "close");
		httpConn.setRequestProperty("Content-Type","text/xml; charset=UTF-8"); 
		int HttpResult = httpConn.getResponseCode();
		logger.info("response code :" + HttpResult);
		
		if (HttpResult != HttpURLConnection.HTTP_OK) {
			logger.error("fail connect");
			throw new Exception("连接失败，可能服务未启动");
		} else {
			logger.debug("success connect");
		}
		
		BufferedReader r = null;
		try {
			r = new BufferedReader(new InputStreamReader(httpConn
					.getInputStream()));
			String line;
			StringBuffer buf = new StringBuffer();
			while ((line = r.readLine()) != null) {
				buf.append(line + "\n");
			}
			String content = buf.toString();
			logger.info("response content :" + content);
			return content;
		} finally {
			try {
				if (r != null)
					r.close();
			} catch (IOException ex) {
				throw ex;
			}
		}
	}
	
	public static void main(String[] args) {
		try {
		    System.out.println("========tst========");
//			String rs = retrieve("http://192.168.0.220:801/ShareData.asp?ticketid=5eb045c9829ac918d3f505e5525bc553&version=2.0&sync=101&appId=14");
			String rs = retrieve("http://192.168.0.56:8000/fpf/test.xml");			
			System.out.println("========tst========");
			System.out.println(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
