package com.jsu.campusElectricity.utils;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016101100664493";

	// 商户私钥，您的PKCS8格式RSA2私钥
	public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCyb6Wf3jFq4yvWX8Qws2FUPyWhr70kDyfLPd67U6nlSHBPDvIPOVXD82uoTcMOYEB/jHR9j0nEyXRYGhROgUI5DusRviYAMpqtdpHt+jdtXjuqqk/bcyeoUPxcFuaC9h/+ugZSU4htr9YvBMYTzg+yHTr3/awPzlIkqcE/XSW7UoWInavB4CCI0nbiVGrC+gozM8ejQn2WEYvMdc0QFGL4InKPpueL+98CT8sTpmLP3rtFF4hjIyvASBs3GV1aS4lK85wq8O3mrF4Y/chgzky4R9JrNWfrrVXXyFkvBMDxztETKax3BMLbuWAICVshf+xW5gkeGwNT4Hl4VGKsPdYtAgMBAAECggEAGFc6lLBOtXH/zOKLdMlDgaaLj57VglG+7+6kr5EeH/TdvSsgwgrkNDLEgNT6wr9GjR+v1GDzgzCn+ky0cHEZg4cRcf4qWOSOTRNbnMcBQJ4WLZXGBv2Sb1d81WMaVssUWj8uFZTni27tHMTd8F2vrnBmVuX3zsAqXG37upUOTJse39q4XEUlX0SCmCF3Ho+d5HVTyReIdEcmtild0sjmxJx4fKFuRk3f9QCJ1/akNsRBJTG+AQXj6uK8PNwqp/FQ8hqTxEWRD4afi8KIMCjV2S6DUvFTFiB2czGJIRn8HxQLX4qazMl/WRADS30bZL9hnNgN7bU6jzBTP2G2PSnHgQKBgQDbKdBjOePO2Cdra6tVBGWKyqvQnv1HiMNjMHHdTA3W0kQrmuGoJjVBIno8db7t6T3o4aNsOjftikHvvrAcJX8Xw20RThf58dWa3xIm84XDb3lp4OQ+hlr8sysPjalPxVcyHjLw3RioqtstDDZaMlrnp7KRDAtXf01zTNvRInlYZQKBgQDQbWri2OxZz4pY4Alvi009bip9/eAkMF3D49ShpOgmRv4WlpAEapUR79sWkCUwereA6HfMXB83xkUxUtR6s/C6R6AIFuG8B2dXJT5UuLyfrT+s4TxBzw5ona9gO+Yd3yWnVIyZtuSJ6PRLn9YDg/gM7MbN7RamjKycki/nIf4WKQKBgQDQ21VK6mUtvpI6bZ4WZB08mg4IvysSG8RP51/ri8n4eeMbRvtbWiYQD4I70d4FLeyg3T73KbgYuFRD3A5KlHU8uTFcd3aj7BIrCABtfE9yQu6KtxXse++DuF8F8HN0oNSK33qeTaVtP32lvWiipjA5aXfjgH3igV/WzF0DPX40fQKBgQCKpS5jxh66w2bpJebA724q/fyAIQAXsqC87PIFsNfIlpLw3ajhdzDAZMhdYveKHyewNXNzWphdlqNyYwH6uXXZxEl8+BOi613fx2OGIfk/UpS34IAf2qdgCfyeTFoP5t96RrDck5/E6VSQdbt8TS4VEvBQDXVyxsMPx+Lbom4NuQKBgCvrI9F0xQv413fGrPSKl3rbBAa0msvzJjzxZMQzbdw2HBrzZgw0KiwTLgJJwhlI5HuBwN1U0hnZY1xAJim6EU++awhNslbs34mXaIWq2gD+AXu/yObXc4jB0CFYS9LwqePp88tbGwpvFryPhhAa789paOctzOdRO7kS3pEI5bZr";
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
	public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkM1IPHRxneuXGYH8T0LjF60+q2Sgi8bOO6sHAlx8by7Bc6vQchy+y6w7TCN2TZ2VMeDCI25nYggTF7FFLHnKbg9hS8gHlqGrZ+O1n8JZGmwVW7TJGmt3tI/MMsZtY+YqjYKNhgdqRpVrEB95lPx8mbsLuBJGi2Ibt3EtwUawuj9ZMxpHelTf8gxca9UPJ19+mZl/qyDBxalZh8HcT+KHg6fIHs4nyMuTztIW5j4gmCnTffC8PI4w3Gvtq9Om4+zvoEgc7ZxETujz3n9nWW5JqA5/YcQRbULc8JHb+WxF0+Pg46kLfjQ5oGSdUvwJQ7kVyDVqIZpVzR8BRA3DzO6saQIDAQAB";

	// 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://127.0.0.1:8080/api/user/successPay";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://127.0.0.1:8080/user/user-index.html";

	// 签名方式
	public static String sign_type = "RSA2";

	// 字符编码格式
	public static String charset = "utf-8";

	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

	// 支付宝网关
	public static String log_path = "D:\\";

//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	/**
	 * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	 * 
	 * @param sWord 要写入日志里的文本内容
	 */
	public static void logResult(String sWord) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
			writer.write(sWord);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
