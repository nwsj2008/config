package ICTCLAS.I3S.AC;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SegmentMain {
	
	private ArrayList<String> stopWords = new ArrayList<String>();
	private final static String WORD = "/b/c/e/f/h/k/o/p/q/r/s/u/w/y/";
	private String[] resultArray;
	
	public String segmentMain(String str) {
	
			String finalString = this.extractWords(Split(str)).toString();
			finalString = this.getFrequency(finalString);
			return finalString;
	}

	public static String Split(String sInput) {
		
			ICTCLAS30 testICTCLAS30 = new ICTCLAS30();

			String argu = ".";
			try {
				if (testICTCLAS30.ICTCLAS_Init(argu.getBytes("GBK")) == false) {
					System.out.println("Init Fail!");
					return "";
				}
					testICTCLAS30.ICTCLAS_SetPOSmap(2);
					byte nativeBytes[] = testICTCLAS30.ICTCLAS_ParagraphProcess(sInput
							.getBytes("GBK"), 1);
					String nativeStr = new String(nativeBytes, 0, nativeBytes.length,
							"GBK");
					return nativeStr ;
			
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
	}
	
	public StringBuilder extractWords(String content) {
		String[] splitContent = content.split(" ");
		StringBuilder contentBuilder = new StringBuilder();
		try {
			addStopWords();
			String wordChar;
			for (int i = 0; i < splitContent.length; i++) {
				String[] splitWord = splitContent[i].split("/");
				if (!stopWords.contains(splitWord[0])) {
					if (splitWord.length >= 2) {
						wordChar = "/" + splitWord[1] + "/";
						if (!WORD.contains(wordChar)) {
							contentBuilder.append(splitContent[i] + " ");
						}
					}
				}

			}
			return contentBuilder; 
		} catch (Exception ex) {
			ex.printStackTrace();
			return contentBuilder;
		}
	}
	
	private void addStopWords() {
		stopWords.add("哈哈");
		stopWords.add("嘻嘻");
		stopWords.add("呵呵");
	}
	
	public String getFrequency(String contextSplit) {
		HashMap<String, Integer> freq = new HashMap<String, Integer>();

		try {
			// 将“我/n 你/n”按空格分成数组
			resultArray = contextSplit.trim().split(" ");
			for (int i = 0; i < resultArray.length; i++) {
				String word = resultArray[i];
				if (!word.equals(" ")) {
					if (!freq.containsKey(word)) {
						freq.put(word, 1);
					} else {
						freq.put(word, freq.get(word) + 1);
					}
				}
			}

			// 组合成"我,n,1,你,n,1"的StringBuilder
			StringBuilder sb = new StringBuilder();
			for (String word : freq.keySet()) {
				String[] temp = word.split("/");
				for (int i = 0; i < temp.length; i++) {
					sb.append(temp[i]);
					sb.append(" ");
				}
				sb.append(freq.get(word));
				sb.append(" ");
			}
			return sb.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}
	public static void main(String[] args) throws Exception {
		try {
			String sInput = "河南三门峡水库停止发电截留被柴油污染黄河水http://news.sina.com.cn/c/2010-01-05/083319400949.shtml2010年01月05日08:33";
			SegmentMain seg = new SegmentMain();
			System.out.println(seg.segmentMain(sInput));

		} catch (Exception ex) {
		}
	}

}
