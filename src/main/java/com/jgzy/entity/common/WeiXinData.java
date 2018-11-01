package com.jgzy.entity.common;

import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.jgzy.utils.MD5Gen;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

/**
 * 微信数据包
 * 
 * @author feel
 * 
 */
public class WeiXinData {

	private final HashMap<String, String> hashmap = new HashMap<String, String>();

	/**
	 * 添加
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, String value) {
		hashmap.put(key, value);
	}

	/**
	 * 判断key是否存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean hasKey(String key) {
		return hashmap.containsKey(key);
	}

	/**
	 * 获取
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return hashmap.get(key);
	}

	/**
	 * 清除
	 */
	public void clear() {
		hashmap.clear();
	}

	/**
	 * 输出url
	 * 
	 * @return
	 */
	public String toURL() {
		StringBuilder sb = new StringBuilder();
		String[] key_arr = hashmap.keySet().toArray(new String[hashmap.size()]);
		Arrays.sort(key_arr);

		String lastKey = "";
		for (int i = 0; i < key_arr.length; i++) {
			String name = key_arr[i];
			String value = hashmap.get(name);
			if (value != null && !"".equals(value)) {
				if ("key".equals(name)) {
					// key 排到最后
					lastKey = "&" + name + "=" + value;
				} else if ("sign".equals(name)) {
					// sign 不参与签名
				} else {
					sb.append("&" + name + "=" + value);
				}
			}
		}
		sb.append(lastKey);
		return !sb.toString().equals("") ? sb.toString().substring(1) : "";
	}

	/**
	 * 输出XML
	 * 
	 * @return
	 */
	public String toXML() {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		String[] key_arr = hashmap.keySet().toArray(new String[hashmap.size()]);
		Arrays.sort(key_arr);

		String lastSign = "";
		for (int i = 0; i < key_arr.length; i++) {
			String name = key_arr[i];
			String value = hashmap.get(name);
			if (value != null && !"".equals(value)) {
				// key 不参输出
				if ("key".equals(name)) {

				}
				// sign 最后輸出簽名
				else if ("sign".equals(name)) {
					lastSign = "<" + name + ">" + "<![CDATA[" + value + "]]></" + name + ">";
				} else {
					sb.append("<" + name + ">").append("<![CDATA[" + value + "]]>").append("</" + name + ">");
				}
			}
		}
		sb.append(lastSign);
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 默认签名
	 * 
	 * @return
	 */
	public String makeSign() {
		// sign不参与签名
		return MD5Gen.MD5Purity(this.toURL());
	}

	/**
	 * h5签名
	 * 
	 * @return
	 */
	public String makeSignH5() {
		// sign不参与签名
		String charset = "utf-8";
		try {
			return MD5Gen.MD5Purity(new String(this.toURL().getBytes(charset), charset));
		} catch (Exception e) {
			return "sign fail";
		}
	}

	/**
	 * xml文档转化为  WeiXinData
	 * 
	 * @param xml
	 * @return
	 */
	public static WeiXinData parseXml(String xml) {
		//  Map retMap = new HashMap();
		WeiXinData wxData = new WeiXinData();
		try {
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = sb.build(source);
			Element root = (Element) doc.getRootElement();// 指向根节点
			List<Element> es = root.getChildren();
			if (es != null && es.size() != 0) {
				for (Element element : es) {
					wxData.put(element.getName(), element.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wxData;
	}
}
