/* 
 * @(#)Codec.java    Created on 2006-3-31
 * Copyright (c) 2005 ZDSoft Networks, Inc. All rights reserved.
 * $Header: f:/44cvsroot/stusys/OA3.6/java/src/net/zdsoft/fpf/server/util/CodecUtils.java,v 1.1 2006/11/09 12:53:43 zhaosf Exp $
 */
package net.zdsoft.leadin.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.codec.binary.Base64;

/**
 * Encode and Decode.
 * 
 * @author zhangza
 * @version $Revision: 1.1 $, $Date: 2006/11/09 12:53:43 $
 */
public class CodecUtils {

	public static String encode(Object object) throws IOException {
		String encodeString = null;
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ObjectOutputStream o = new ObjectOutputStream(os);
			o.writeObject(object);
			o.flush();

			byte[] buf = os.toByteArray();
			encodeString = new String(Base64.encodeBase64(buf));

			o.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return encodeString;
	}

	public static Object decode(String content) throws IOException,
			ClassNotFoundException {
		Object object = null;
		byte[] buf = Base64.decodeBase64(content.getBytes());

		ObjectInputStream iobj;
		try {
			iobj = new ObjectInputStream(new ByteArrayInputStream(buf));
			object = (String) iobj.readObject();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
		return object;
	}

	/**
	 * BASE64 encode.
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String encodeBase64(String str) {
		byte[] encodeBytes = Base64.encodeBase64(str.getBytes());
		return new String(encodeBytes);
	}

	/**
	 * BASE64 decode.
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String decodeBase64(String str) {
		byte[] decodeBytes = Base64.decodeBase64(str.getBytes());
		return new String(decodeBytes);
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		String test = "zayyvQ==";
		System.out.println(decodeBase64(test));

		String a = "同步";
		System.out.println(encodeBase64(a));
	}
}
