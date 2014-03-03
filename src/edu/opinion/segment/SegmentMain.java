package edu.opinion.segment;

import java.util.ArrayList;
import java.util.HashMap;
import ICTCLAS.I3S.AC.ICTCLAS30;


public class SegmentMain {
	
	private ArrayList<String> stopWords = new ArrayList<String>();
	private final static String WORD = "/b/c/e/f/h/k/o/p/q/r/s/u/w/y/";
	
	/**
	 * 存放分词结果的数组
	 */
	private String[] resultArray;
	
//	private static Logger log = Logger.getLogger(SegmentMain.class);
	
//	class Result {
//		int start; //start position,词语在输入句子中的开始位置
//		int length; //length,词语的长度
//		//char sPOS[8];//词性
//
//		int posId;//word type，词性ID值，可以快速的获取词性表
//		int wordId; //如果是未登录词，设成0或者-1
//		int word_type; //add by qp 2008.10.29 区分用户词典;1，是用户词典中的词；0，非用户词典中的词
//
//	  int weight;//add by qp 2008.11.17 word weight
//	};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String textString = "连就连，你我相约万世缘；不羡帝王浮生趣，只羡鸳鸯不羡仙；）-》：【惠尔";
		textString = textString.replaceAll(" ", "");		
		SegmentMain seg = new SegmentMain();
	
		textString = seg.split(textString);
		System.out.println(textString);
		
		textString = seg.extractWords(textString).toString();
		System.out.println("text1: "+textString);
		String finalString = seg.getFrequency(textString);
		System.out.println("finalstring: "+finalString);

	}
	
	public String segmentMain(String str){
		try{
			String finalString = this.extractWords(this.split(str)).toString();
			finalString = this.getFrequency(finalString);
			return finalString;		
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
	
	public String split(String sInput)
	{	
	try{	
        sInput = sInput.replaceAll(" ", "");
		ICTCLAS30 ict = new ICTCLAS30();

		String argu = ".";
		if (ict.ICTCLAS_Init(argu.getBytes("GBK")) == false)
		{
			System.out.println("Split Init Fail!");
			return null;
		}

		/*
		 * 设置词性标注集
		        ID		    代表词性集 
				1			计算所一级标注集
				0			计算所二级标注集
				2			北大二级标注集
				3			北大一级标注集
		*/
		ict.ICTCLAS_SetPOSmap(1);

		//导入用户词典前
//		byte nativeBytes[] = ict.ICTCLAS_ParagraphProcess(sInput.getBytes("GBK"), 1);
//		String nativeStr = new String(nativeBytes, 0, nativeBytes.length, "GBK");
//
//		System.out.println("未导入用户词典： " + nativeStr);

		//导入用户词典
		String sUserDict = "userdic.txt";
		int nCount = ict.ICTCLAS_ImportUserDict(sUserDict.getBytes("GBK"));
		ict.ICTCLAS_SaveTheUsrDic();//保存用户词典
//		System.out.println("导入个用户词： " + nCount);

		byte nativeBytes[] = ict.ICTCLAS_ParagraphProcess(sInput.getBytes("GBK"), 1);
		String nativeStr = new String(nativeBytes, 0, nativeBytes.length, "GBK");
		nativeStr = nativeStr.substring(0,nativeStr.length()-1);

//		System.out.println("导入用户词典后： " + nativeStr);

		//动态添加用户词
//		String sWordUser = "973专家组组织的评测	ict";
//		ict.ICTCLAS_AddUserWord(sWordUser.getBytes("GBK"));
//		ict.ICTCLAS_SaveTheUsrDic();//保存用户词典			
//		
//		nativeBytes = ict.ICTCLAS_ParagraphProcess(sInput.getBytes("GBK"), 1);
//		nativeStr = new String(nativeBytes, 0, nativeBytes.length, "GBK");
//		System.out.println("动态添加用户词后: " + nativeStr);

		//文件分词
//		String argu1 = "Test.txt";
//		String argu2 = "Test_result.txt";
//		ict.ICTCLAS_FileProcess(argu1.getBytes("GBK"), argu2.getBytes("GBK"), 1);

		
		//释放分词组件资源
		ict.ICTCLAS_Exit();
		return nativeStr;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
//			log.error("SegmentMain-split Error",ex);
			return null;
		}
	}
	
	/**
	 * 将无意义词性的词从分词结果中剔除，词性如下<br>
	 * Ag 形语素 a 形容词 ad 副形词 an 名形词 b 区别词 c 连词 dg 副语素 d 副词 e 叹词 f 方位词 g 语素 h 前接成分
	 * i 成语 j 简称略语 k 后接成分 l 习用语 m 数词 Ng 名语素 n 名词 nr 人名 ns 地名 nt 机构团体 nz 其他专名
	 * o 拟声词 p 介词 q 量词 r 代词 s 处所词 tg 时语素 t 时间词 u 助词 vg 动语素 v 动词 vd 副动词 vn 名动词 
	 * w 标点符号 x 非语素字 y 语气词 z 状态词 un 未知词
	 * 
	 * @param content
	 *            原始字符串
	 * @return 经过词性筛选的字符串
	 */
	public StringBuilder extractWords(String content) {
		String[] splitContent = content.split(" ");
//		System.out.println("splitContentLength:"+splitContent.length);
		StringBuilder contentBuilder = new StringBuilder();
		try{
			addStopWords();
			String wordChar;
			for (int i = 0; i < splitContent.length; i++) {
//				System.out.println("Run Times:"+i);
				String[] splitWord = splitContent[i].split("/");
//				System.out.println("0:"+splitWord[0]+" 1:"+splitWord[1]);
				if (!stopWords.contains(splitWord[0])){
					if (splitWord.length>=2){
						wordChar = "/"+splitWord[1]+"/";
						if (!WORD.contains(wordChar)){
							contentBuilder.append(splitContent[i] + " ");
						}
					}	
				}
						
//				if ((i % 3 == 0) && (!stopWords.contains(splitContent[i]))) {
//					if ((j < splitContent.length - 1)
//							&& (!splitContent[i + 1].equals("r"))
//							&& (!splitContent[i + 1].equals("w"))
//							&& (!splitContent[i + 1].equals("o"))
//							&& (!splitContent[i + 1].equals("e"))) {
//						contentBuilder.append(splitContent[i] + " ");
//					}
//				}
			}
			return contentBuilder; //
		}catch(Exception ex){
			ex.printStackTrace();
			return contentBuilder;
		}
	}
	
	private void addStopWords(){
		stopWords.add("哈哈");
		stopWords.add("嘻嘻");
		stopWords.add("呵呵");
	}
	
	/**
	 * 进行词频统计
	 * 
	 * @return 以逗号分隔的词频，如"我,n,1,你,n,1"
	 */
	public String getFrequency(String contextSplit) {
		HashMap<String, Integer> freq = new HashMap<String, Integer>();

		try{
//			 将“我/n 你/n”按空格分成数组
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
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}
	
}
