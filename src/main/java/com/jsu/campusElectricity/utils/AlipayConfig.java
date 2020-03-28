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
	public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQChJSi2N2lsnsv1qnVxbvMwXsboAklo3nvOwLtt2OCzXn3UpqGWctCvqmBhAYH5PGO8h8NpjxCGNCSHuxzb/IIIRfHV+3N4pG88DFzpyV1xNnH/v++g12JaiyvE4J9jnikSPxj+SFGTmmm25WbFBCJdBpGAow1uZ1VtNgLxzxARUg/xtRqt/El00qgoeLcAZ9eSWqa8fDeKuvrTgsrMjnG7sKbn9qIUo9FYe715lRFSoz7MoD3BFlBEVeMrdLpVcHQljpWkzeQW/u8o/e4+Y49OcOyKIlnzJIWNiYkjX1/eXwa46Y+f29+C2Vkq8wNMDjO6aIuSQZ+QxiQdXgj2yw63AgMBAAECggEAUDwHPEr0w++BEC2yUitzDJd0JBrXYE6+iGAwMm502vpmXpYlCJrcS0riuAofI/CsnDdg1dM04A1gTvCcWvAr8mCZG/kaMmx1MgnZzVHEo9T/B98SOhxjzT9UKK/KzLf4LSybjiiF7Z9JmZY5CEmZroXRtqH40wrCXyEwcCOwFV7i7rjQl0+4B7IpS/cltdnq4Xd+ZS6qlYLZUHT2seGe4QwtaUx+/+XcxaB7LyX012nFJJv23c/6ShlUlHh4e8plMwlrCP7aUKt6PyEKbAtjumKCzXGJb1ryyElb11VsRwQ73CKMA1l59qWGQMU1hyqPCbupfpeZrlt5qOumNp/eUQKBgQDZzA+2hTMk8dfUAVXopClkrmJnjNwwPa8gP5s0nEgtASCMgMlRVhGMaxEsIM9E7ajoRY5aQo0BdWERAQ36JpmJlSHGDsi76o41eQrLh7mbdqexheBuxeNwJV3QEm0kpt+rRMVObD/h6SO2B36VF1Pvgrsai0nDKiLTiRIZzYBSaQKBgQC9aTQ0Y2dDENpLWHS0qr83P11RpAZ9byF4D9mxA0t+zWIiSKjWWsm+9w5hDdNM3b5fO6Dto9eMWKr5xTBSBGe9l20Rt/m5UL3+jnK0Jieo1/bxkbyqzJDUfDbkxFW0dmOmCz0pkIRzx/QoQVd+o5C5WHUgTA2dIwKejPV2V230HwKBgE9X0sWYYthrrkfSsLxOybGfAcRdgX2lix4BbgZNhNG4XmYxKKYT59Ky0o+z6C5hMi5j4IcrRXkpjNIIA1KFK1v+00325tPmkCYU0YTyDgD/yfmXHvrb5o5dXn7Fp7M5g7Q+Gm8GaoNfTp4qvseLtw1DH+x8n8OUnxlXDxwNTFk5AoGAVfqMdP9uX1YmES+dAmnDlNuRi76uJ4ArrCldwulg+rVWR4O+CFd73tCc2VeCRBEgGnCh012dOZsCOZDWJ1TSlpBHNVTS6dkJ9aRJ0xbCx/UIpo1mHA3x7FJaRKwOz3UyNKxDXju4tCne3esTQWwUBN5vhHKhd2PuC3Sm88oqIjcCgYB0h+ArPAs3CVJ3iCPIk9iUWb33+R6LBDbbQxjEAeIGQqooS1+mekMq+yuWvaQTIu7Pdm7F2mP7A+6V/k2eSRAnVbyqDbqHf3t9SQLcx+gZrHN77EBWtIZ0g5Wbvjy44+gXxLH8WkOmq5lbedZ8W1EnFKHwq2VRsi26v9L7u2E9rg==";
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
	public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkM1IPHRxneuXGYH8T0LjF60+q2Sgi8bOO6sHAlx8by7Bc6vQchy+y6w7TCN2TZ2VMeDCI25nYggTF7FFLHnKbg9hS8gHlqGrZ+O1n8JZGmwVW7TJGmt3tI/MMsZtY+YqjYKNhgdqRpVrEB95lPx8mbsLuBJGi2Ibt3EtwUawuj9ZMxpHelTf8gxca9UPJ19+mZl/qyDBxalZh8HcT+KHg6fIHs4nyMuTztIW5j4gmCnTffC8PI4w3Gvtq9Om4+zvoEgc7ZxETujz3n9nWW5JqA5/YcQRbULc8JHb+WxF0+Pg46kLfjQ5oGSdUvwJQ7kVyDVqIZpVzR8BRA3DzO6saQIDAQAB";

	// 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://47.113.118.101:8060/api/successPay";

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
