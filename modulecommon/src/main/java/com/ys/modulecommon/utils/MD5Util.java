package com.ys.modulecommon.utils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * <P>MD5</P>
 * 
 * @version $Id$
 * @user linxl 2017年4月11日 下午1:50:45
 */
public class MD5Util {

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return resultString;
	}

	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			return MD5Encode(origin, null);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };
	public static String mapStringKeySortToLinkString(Map<String, String> params, boolean isIgnoreBlankOrNull) {
		List<String> keys = new ArrayList<>(params.keySet());
		Collections.sort(keys);
		StringBuilder sb = new StringBuilder();
		int size = keys.size();
		for (int i = 0; i < size; i++) {
			String key = keys.get(i);
			String obj = params.get(key);
			if (!isIgnoreBlankOrNull || !isBlank(obj)) {
				sb.append(key).append("=").append(obj == null ? "&" : obj + "&");
			}
		}
		return sb.substring(0, sb.length() - 1);
	}

	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 验签
	 * @param map
	 * @param signatureInfo
	 * @param md5Key
	 * @return
	 */
	public static boolean checkSign(Map<String,String> map,String signatureInfo,String md5Key)
	{
		String signStr=MD5Util.mapStringKeySortToLinkString(map,true);
		String finalSignStr=MD5Util.MD5Encode(signStr + md5Key).toUpperCase();
		LogUtil.e("TAG", "finalSignStr:" + finalSignStr);
		LogUtil.e("TAG", "signatureInfo:" + signatureInfo);
		if(finalSignStr.equals(signatureInfo))
		{
			return true;
		}else
		{
			return false;
		}
	}
}
