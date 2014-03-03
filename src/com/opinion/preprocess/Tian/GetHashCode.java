package com.opinion.preprocess.Tian;

import java.util.List;

public class GetHashCode {

	public static int getHashCode(List<String> hsCov) {
		StringBuilder r_hscodeString = new StringBuilder();

		for (String hsc : hsCov) {
			if (!("").equals(hsc)) {
				r_hscodeString.append(hsc);
				}
		}
		return r_hscodeString.toString().hashCode();
	}

}
