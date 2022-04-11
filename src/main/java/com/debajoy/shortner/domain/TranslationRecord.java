package com.debajoy.shortner.domain;

public class TranslationRecord {
	String key;
	String longURL;

	public TranslationRecord(String key, String longURL) {
		super();
		this.key = key;
		this.longURL = longURL;
	}

	public String getKey() {
		return key;
	}

	public String getLongURL() {
		return longURL;
	}
	
}
