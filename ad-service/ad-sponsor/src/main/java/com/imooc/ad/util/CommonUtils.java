package com.imooc.ad.util;

import org.apache.commons.codec.digest.DigestUtils;

public class CommonUtils {

	public static String md5(String value) {
		return DigestUtils.md2Hex(value).toUpperCase();
	}

}
