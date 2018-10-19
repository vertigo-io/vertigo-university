package io.mars.util;

/**
 * Util classes publish easier method or are stateLess.
 * Check for no duplication.
 * @author pchretien
 */
public final class SampleUtil {
	private SampleUtil() {
		//only static methods are allowed
	}

	public static String hello() {
		return "hello";
	}
}
