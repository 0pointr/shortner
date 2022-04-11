package com.debajoy.shortner.service;

public class ServerLinkUtil {

	public static String scheme;
	public static String host;
	public static int port;
	public static String url;
	
	public static boolean isNotPopulated() {
		return url == null;
	}
	
	
}
