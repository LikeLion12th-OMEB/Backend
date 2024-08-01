package com.example.OMEB.global.utils;

public class StringBlankUtils {

	/**
	 * 문자열 모든 공백을 제거한다.
	 * @param str
	 * @return 문자열이 제거된 문자열
	 */
	public static String stringAllNotBlank(String str) {
		return str.replace(" ", "");
	}

	/**
	 * 문자열의 양쪽 공백을 제거한다.
	 * @param str
	 * @return 양쪽 공백을 제거한 문자열
	 */
	public static String stringSideNotBlank(String str) {
		return str.trim();
	}

}
