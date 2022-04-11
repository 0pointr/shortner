package com.debajoy.shortner.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;

import com.debajoy.shortner.domain.TranslationRecord;

@Service
public class ShortnerService {

	private static final int URL_KEY_LEN = 6;

	private static final String DEFAULT_PROTOCOL = "http";
	
	private final RandomStringGenerator randGen = new RandomStringGenerator
														.Builder()
														.withinRange(new char[]{'A', 'Z'},
																	 new char[]{'a', 'z'},
																	 new char[]{'0', '9'})
														.build();
	
	private final HashMap<String, TranslationRecord> shortenCache = new HashMap<>();
	private final HashMap<String, TranslationRecord> storage = new HashMap<>();

	
	private static final Pattern STATRTS_WITH_SCHEME = Pattern.compile("^[a-z]+://");
	private String sanitizeURL(String longURL) throws IllegalURLException, MalformedURLException {
		if (!STATRTS_WITH_SCHEME.matcher(longURL).find())
			longURL = DEFAULT_PROTOCOL + "://" + longURL;
		
		URL s = new URL(longURL);
		
		if (s.getHost().equals(ServerLinkUtil.host))
			throw new IllegalURLException("URL not allowed");
		
		return longURL;
	}
	
	public TranslationRecord shorten(String longURL) throws  MalformedURLException, IllegalURLException {
		
		longURL = sanitizeURL(longURL);
		
		if (shortenCache.containsKey(longURL))
			return shortenCache.get(longURL);
		
		String key = randGen.generate(URL_KEY_LEN);
		TranslationRecord shortened = new TranslationRecord(key, longURL);
		shortenCache.put(longURL, shortened);
		storage.put(key, shortened);
		
		return shortened;
	}

	public TranslationRecord unshorten(String key) throws ShortKeyNotFoundException {
		if (storage.containsKey(key))
			return storage.get(key);
		
		throw new ShortKeyNotFoundException("");
	}
	
	public class ShortKeyNotFoundException extends Exception {

		public ShortKeyNotFoundException(String arg0, Throwable arg1) {
			super(arg0, arg1);
			// TODO Auto-generated constructor stub
		}

		public ShortKeyNotFoundException(String arg0) {
			super(arg0);
			// TODO Auto-generated constructor stub
		}

		public ShortKeyNotFoundException(Throwable arg0) {
			super(arg0);
			// TODO Auto-generated constructor stub
		}

	}


	public class IllegalURLException extends Exception {

		public IllegalURLException(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		public IllegalURLException(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		public IllegalURLException(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}
		
	}

}
