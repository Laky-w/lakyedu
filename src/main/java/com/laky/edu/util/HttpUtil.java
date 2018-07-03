package com.laky.edu.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Http请求的工具类  
 * @author Administrator
 *
 */
public class HttpUtil {
	//连接超时时间设置
	private static final int TIMEOUT_IN_MILLIONS = 5000;
	private static final Logger logger= Logger.getLogger(HttpUtil.class);
	
	public interface CallBack {
		void onRequestComplete(String result);
	}

	/**
	 * 异步的Get请求
	 * 
	 * @param urlStr
	 * @param callBack
	 */
	public static void doGetAsyn(final String urlStr, final CallBack callBack) {
		new Thread() {
			public void run() {
				String result;
				try {
					result = doGet(urlStr);
					if (callBack != null) {
						callBack.onRequestComplete(result);
					}
				} catch (IOException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
			};
		}.start();
	}

	/**
	 * 异步的Post请求
	 * 
	 * @param urlStr
	 * @param params
	 * @param callBack
	 * @throws Exception
	 */
	public static void doPostAsyn(final String urlStr, final String params,
                                  final CallBack callBack) throws Exception {
		new Thread() {
			public void run() {
				try {
					String result = doPost(urlStr, params);
					if (callBack != null) {
						callBack.onRequestComplete(result);
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}

			};
		}.start();

	}

	/**
	 * Get请求，获得返回数据
	 * 
	 * @param urlStr
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String doGet(String urlStr) throws IOException {
		URL url = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		String result=null;
		url = new URL(urlStr);
		conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
		conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("connection", "Keep-Alive");
		if (conn.getResponseCode() == HttpStatus.SC_OK) {
			is = conn.getInputStream();
			baos = new ByteArrayOutputStream();
			int len = -1;
			byte[] buf = new byte[128];

			while ((len = is.read(buf)) != -1) {
				baos.write(buf, 0, len);
			}
			baos.flush();
			result = baos.toString();
		} else {
			throw new RuntimeException(" responseCode is not 200 ... ");
		}
		if (is != null){
			is.close();
		}
			
		if (baos != null){
			baos.close();
		}
		conn.disconnect();
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 * @throws IOException
	 * @throws Exception
	 */
	public static String doPost(String url, String param) throws IOException {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		URL realUrl = new URL(url);
		// 打开和URL之间的连接
		HttpURLConnection conn = (HttpURLConnection) realUrl
				.openConnection();
		// 设置通用的请求属性
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestProperty("charset", "utf-8");
		conn.setUseCaches(false);
		// 发送POST请求必须设置如下两行
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
		conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

		if (param != null && !param.trim().equals("")) {
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
		}
		// 定义BufferedReader输入流来读取URL的响应
		in = new BufferedReader(
				new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			result += line;
		}
		if (out != null) {
			out.close();
		}
		if (in != null) {
			in.close();
		}
		return result;
	}
	
	/**
	 * HttpClient 请求 拿回数据
	 * @param url  请求路径
	 * @return  返回的数据
	 * @throws IOException
	 * @throws ParseException 
	 */
	public static String doPostKey(String url, Map<String, String> map,String token) throws ParseException, IOException {
		HttpPost httppost = getPost(url, map);
		// 处理请求，得到响应
		HttpClient httpclient = HttpClients.createDefault();
		httppost.setHeader("token",token);
		HttpResponse response = httpclient.execute(httppost);
		String respStr = EntityUtils.toString(response.getEntity(),"UTF-8");
		return respStr;
	}
	
	protected static HttpPost getPost(String url, Map<String, String> params) {
		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		if (params != null) {
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
			}
		}
		try {
			httpost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return httpost;
	}

}
