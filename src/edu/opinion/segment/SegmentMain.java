package edu.opinion.segment;

import java.util.ArrayList;
import java.util.HashMap;
import ICTCLAS.I3S.AC.ICTCLAS30;


public class SegmentMain {
	
	private ArrayList<String> stopWords = new ArrayList<String>();
	private final static String WORD = "/b/c/e/f/h/k/o/p/q/r/s/u/w/y/";
	
	/**
	 * ��ŷִʽ��������
	 */
	private String[] resultArray;
	
//	private static Logger log = Logger.getLogger(SegmentMain.class);
	
//	class Result {
//		int start; //start position,��������������еĿ�ʼλ��
//		int length; //length,����ĳ���
//		//char sPOS[8];//����
//
//		int posId;//word type������IDֵ�����Կ��ٵĻ�ȡ���Ա�
//		int wordId; //�����δ��¼�ʣ����0����-1
//		int word_type; //add by qp 2008.10.29 �����û��ʵ�;1�����û��ʵ��еĴʣ�0�����û��ʵ��еĴ�
//
//	  int weight;//add by qp 2008.11.17 word weight
//	};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String textString = "��������������Լ����Ե�����۵�������Ȥ��ֻ��ԧ�첻���ɣ���-�������ݶ�";
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
		 * ���ô��Ա�ע��
		        ID		    ������Լ� 
				1			������һ����ע��
				0			������������ע��
				2			���������ע��
				3			����һ����ע��
		*/
		ict.ICTCLAS_SetPOSmap(1);

		//�����û��ʵ�ǰ
//		byte nativeBytes[] = ict.ICTCLAS_ParagraphProcess(sInput.getBytes("GBK"), 1);
//		String nativeStr = new String(nativeBytes, 0, nativeBytes.length, "GBK");
//
//		System.out.println("δ�����û��ʵ䣺 " + nativeStr);

		//�����û��ʵ�
		String sUserDict = "userdic.txt";
		int nCount = ict.ICTCLAS_ImportUserDict(sUserDict.getBytes("GBK"));
		ict.ICTCLAS_SaveTheUsrDic();//�����û��ʵ�
//		System.out.println("������û��ʣ� " + nCount);

		byte nativeBytes[] = ict.ICTCLAS_ParagraphProcess(sInput.getBytes("GBK"), 1);
		String nativeStr = new String(nativeBytes, 0, nativeBytes.length, "GBK");
		nativeStr = nativeStr.substring(0,nativeStr.length()-1);

//		System.out.println("�����û��ʵ�� " + nativeStr);

		//��̬����û���
//		String sWordUser = "973ר������֯������	ict";
//		ict.ICTCLAS_AddUserWord(sWordUser.getBytes("GBK"));
//		ict.ICTCLAS_SaveTheUsrDic();//�����û��ʵ�			
//		
//		nativeBytes = ict.ICTCLAS_ParagraphProcess(sInput.getBytes("GBK"), 1);
//		nativeStr = new String(nativeBytes, 0, nativeBytes.length, "GBK");
//		System.out.println("��̬����û��ʺ�: " + nativeStr);

		//�ļ��ִ�
//		String argu1 = "Test.txt";
//		String argu2 = "Test_result.txt";
//		ict.ICTCLAS_FileProcess(argu1.getBytes("GBK"), argu2.getBytes("GBK"), 1);

		
		//�ͷŷִ������Դ
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
	 * ����������ԵĴʴӷִʽ�����޳�����������<br>
	 * Ag ������ a ���ݴ� ad ���δ� an ���δ� b ����� c ���� dg ������ d ���� e ̾�� f ��λ�� g ���� h ǰ�ӳɷ�
	 * i ���� j ������� k ��ӳɷ� l ϰ���� m ���� Ng ������ n ���� nr ���� ns ���� nt �������� nz ����ר��
	 * o ������ p ��� q ���� r ���� s ������ tg ʱ���� t ʱ��� u ���� vg ������ v ���� vd ������ vn ������ 
	 * w ������ x �������� y ������ z ״̬�� un δ֪��
	 * 
	 * @param content
	 *            ԭʼ�ַ���
	 * @return ��������ɸѡ���ַ���
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
		stopWords.add("����");
		stopWords.add("����");
		stopWords.add("�Ǻ�");
	}
	
	/**
	 * ���д�Ƶͳ��
	 * 
	 * @return �Զ��ŷָ��Ĵ�Ƶ����"��,n,1,��,n,1"
	 */
	public String getFrequency(String contextSplit) {
		HashMap<String, Integer> freq = new HashMap<String, Integer>();

		try{
//			 ������/n ��/n�����ո�ֳ�����
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

			// ��ϳ�"��,n,1,��,n,1"��StringBuilder
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
