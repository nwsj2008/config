package com.opinion.preprocess.Tian;

public class TagClean {

	public static String Clean(String nodeString) {
		String node = nodeString.replaceAll("<.*?>", "")
				.replaceAll("nbsp;", "").replaceAll("£”", "").replaceAll("¡¡", "").trim();
		return node;
	}

}
