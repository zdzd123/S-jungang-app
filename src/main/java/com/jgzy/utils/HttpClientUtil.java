package com.jgzy.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class HttpClientUtil {

	/**
	 * Get请求调用
	 * @param url	不含参数的URL	
	 * @param charset	字符编码（Consts类下有常量）	
	 * @param basicNameValuePair  创建basicNameValuePair(key,value)对象，参数key：url后的key, value：url等号后的值
	 * @return 调用URL返回结果。返回结果中字段status的值分别代表的含义为：<br>0: 成功 <br>1: 服务器内部错误 <br>2: 参数错误 <br>3: http method错误 <br>21: 此操作为批量操作 <br>22: 同步到检索失败 <br>31: 服务端加锁失败 <br>32: 服务端释放锁失败 <br>1001: 表的name重复 <br>1002: 表的数量达到了最大值 <br>1003: 表中存在poi数据，不允许删除 <br>2001: 列的key重复 <br>2002: 列的key是保留字段 <br>2003: 列的数量达到了最大值 <br>2004: 唯一索引只能创建一个 <br>2005: 更新为唯一索引失败，原poi数据中有重复 <br>2011: 排序筛选字段只能用于整数或小数类型的列 <br>2012: 排序筛选的列已经达到了最大值 <br>2021: 检索字段只能用于字符串类型的列且最大长度不能超过512个字节 <br>2022: 检索的列已经达到了最大值 <br>2031: 索引的列已经达到了最大值 <br>2041: 指定的列不存在 <br>2042: 修改max_length必须比原值大 <br>3001: 更新坐标必须包含经纬度和类型 <br>3002: 唯一索引字段存在重复 <br>3031: 上传的文件太大 <br>
	 * 
	 */
	public static String doGet(String url, String charset,BasicNameValuePair... basicNameValuePair) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = null;
		// 封装请求参数
		List<NameValuePair> params = new ArrayList<>();
		for (int i = 0; i < basicNameValuePair.length; i++) {
			params.add(basicNameValuePair[i]);
		}

		String str = "";
		try {
			// 转换为键值对
			str = EntityUtils.toString(new UrlEncodedFormEntity(params, charset));
			// 创建Get请求
			HttpGet httpGet = new HttpGet(url + "?" + str);
			// 执行Get请求，
			response = httpClient.execute(httpGet);
			// 看请求是否成功，这儿打印的是http状态码
//			System.out.println(response.getStatusLine().getStatusCode());
			// 得到响应体
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, charset);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 消耗实体内容
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// 关闭相应 丢弃http连接
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result ;
	}
	
	/**
	 * Post请求调用
	 * @param url	不含参数的URL		
	 * @param charset	字符编码（Consts类下有常量）
	 * @param map	参数key：url后的key, value：url等号后的值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String doPost(String url, String charset , Map<String, String> map) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null ;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpPost = new HttpPost(url);
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator<?> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> elem = (Entry<String, String>) iterator.next();
				String key = elem.getKey() ;
				String value = elem.getValue() ;
				BasicNameValuePair bnvp = new BasicNameValuePair(key, value);
				list.add(bnvp);
			}
			if (list.size() > 0) {
				// 转换为键值对
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpPost.setEntity(entity);
			}
			// 执行Post请求，
			response = httpClient.execute(httpPost);
			if (response != null) {
				// 看请求是否成功，这儿打印的是http状态码
//				System.out.println(response.getStatusLine().getStatusCode());
				// 得到响应体
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			// 消耗实体内容
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// 关闭相应 丢弃http连接
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	} 
}
