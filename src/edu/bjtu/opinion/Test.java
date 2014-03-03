package edu.bjtu.opinion;


public class Test {
	public static void main(String[] args) {
		//System.out.println(System.getProperty("java.library.path",""));
		char[] cs = new char[32];
		for(int i = 0; i < 32; i++){
			cs[i] = (char)i;
//			System.out.println(cs[i]);
			StringBuilder sb = new StringBuilder();
			sb.append(cs[i]);
		//	System.out.println(i+"jgkgkjg"+sb.toString());
		}
//		System.out.println(cs);
		String str = new String(new byte[] { -96});
	System.out.println(str);
		
		String str2 = new String(new char[32]);
//		System.out.println(str2);

		
		
	}
}
