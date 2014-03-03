package com.opinion.preprocess.Tian;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class PreThreadFactory {
	/**�̳߳�
	 * �жϽ����̸߳��������ý����߳�������Ϊ50���������߳���������50ʱ��������
	 */
	private volatile static PreThreadFactory instance = null;   //volatile ȷ����̬�����������߳̿ɼ�
	private List<PreThread> threadList = null;
	private int MaxThreadNum =20;
	private int threadNum = 0;

	public int getThreadNum() {
		return threadNum;
	}

	//
	private PreThreadFactory() {
		threadList = new LinkedList<PreThread>();
	}

	public static PreThreadFactory getInstance() {
		if (instance == null) {
			synchronized (PreThreadFactory.class) {
				if (instance == null) {
					instance = new PreThreadFactory();			//��õ���
				}
			}
		}
		return instance;
	}

	// ����߳�����С������߳����������������̣߳��߳�������һ
	public PreThread getThread(File file) {
		clearThread();
		if (threadNum < MaxThreadNum) {// �߳�����С������߳�����
			PreThread thread = new PreThread(file);
			synchronized (threadList) {
				threadList.add(thread);
			}
			threadNum++;// �߳�������һ
			return thread;
		}
		clearThread();
		return null;
	
	}

	// ����߳��ͷţ����Ƴ��̣߳��߳�������һ
	public  synchronized void clearThread() {
		for (PreThread thread : threadList) {
			if (thread.isFinish()) {
					threadList.remove(thread);// �Ƴ�list�е�i
				threadNum--;// �߳�������һ
			}
		}
	}

}
