package dianking.connector.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class MD5Util {
	private static Logger logger = Logger.getLogger(MD5Util.class);
	// 用来将字节转换成 16 进制表示的字符
	static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String getFileMD5(InputStream fis) {
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}

			byte[] buffer = new byte[2048];
			int length = -1;
			long s = System.currentTimeMillis();
			try {
				while ((length = fis.read(buffer)) != -1) {
					md.update(buffer, 0, length);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fis.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			System.err.println("last: " + (System.currentTimeMillis() - s));
			byte[] b = md.digest();
			return byteToHexStringSingle(b);
	}
	/**
	/**
	 * 对文件全文生成MD5摘要
	 * 
	 * @param file
	 *            要加密的文件
	 * @return MD5摘要码
	 */
	public static String getFileMD5(File file) {
		try {
			return getFileMD5(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} 
		return null;
	}

	/** */
	/**
	 * 对一段String生成MD5加密信息
	 * 
	 * @param message
	 *            要加密的String
	 * @return 生成的MD5信息
	 */
	public static String getMD5(String message) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
//			System.err.println("MD5摘要长度：" + md.getDigestLength());
			byte[] b = md.digest(message.getBytes("utf-8"));
			return byteToHexStringSingle(b);// byteToHexString(b);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Deprecated
	/** */
	/** 
	 * 把byte[]数组转换成十六进制字符串表示形式 
	 * @param tmp    要转换的byte[] 
	 * @return 十六进制字符串表示形式 
	 */
	private static String byteToHexString(byte[] tmp) {
		String s;
		// 用字节表示就是 16 个字节
		char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
		// 所以表示成 16 进制需要 32 个字符
		int k = 0; // 表示转换结果中对应的字符位置
		for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
			// 转换成 16 进制字符的转换
			byte byte0 = tmp[i]; // 取第 i 个字节
			str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
			// >>> 为逻辑右移，将符号位一起右移
			str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
		}
		s = new String(str); // 换后的结果转换为字符串
		return s;
	}

	/**
	 * 独立把byte[]数组转换成十六进制字符串表示形式
	 * 
	 * @author Bill
	 * @create 2010-2-24 下午03:26:53
	 * @since
	 * @param byteArray
	 * @return
	 */
	public static String byteToHexStringSingle(byte[] byteArray) {
		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}

    public static void main(String[] args) {
        System.out.println(MD5Util.getMD5("luo"));
    }
}