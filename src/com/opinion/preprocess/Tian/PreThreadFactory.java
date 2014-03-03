package com.opinion.preprocess.Tian;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class PreThreadFactory {
	/**线程池
	 * 判断解析线程个数，设置解析线程最大个数为50，当解析线程数量超过50时不再启动
	 */
	private volatile static PreThreadFactory instance = null;   //volatile 确保静态变量对所有线程可见
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
					instance = new PreThreadFactory();			//获得单例
				}
			}
		}
		return instance;
	}

	// 如果线程数量小于最大线程数量，则启动新线程，线程数量加一
	public PreThread getThread(File file) {
		clearThread();
		if (threadNum < MaxThreadNum) {// 线程数量小于最大线程数量
			PreThread thread = new PreThread(file);
			synchronized (threadList) {
				threadList.add(thread);
			}
			threadNum++;// 线程数量加一
			return thread;
		}
		clearThread();
		return null;
	
	}

	// 如果线程释放，则移除线程，线程数量减一
	public  synchronized void clearThread() {
		for (PreThread thread : threadList) {
			if (thread.isFinish()) {
					threadList.remove(thread);// 移除list中的i
				threadNum--;// 线程数量减一
			}
		}
	}

}
