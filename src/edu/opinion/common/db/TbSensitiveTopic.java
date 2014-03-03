package edu.opinion.common.db;

import java.util.Date;

/**
 * TbSensitiveTopic entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TbSensitiveTopic implements java.io.Serializable {

	// Fields

	private String id;
	private TbParserNews tbParserNews;
	private TbParserBbs tbParserBbs;
	private TbCluster tbCluster;
	private TbReBbs tbReBbs;
	private TbReNews tbReNews;
	private Integer frequency;
	private String sensitiveWords;
	private String clustersId;
	private Date dealTime;

	// Constructors

	/** default constructor */
	public TbSensitiveTopic() {
	}

	/** full constructor */
	public TbSensitiveTopic(TbParserNews tbParserNews, TbParserBbs tbParserBbs,
			TbCluster tbCluster, TbReBbs tbReBbs, TbReNews tbReNews,
			Integer frequency, String sensitiveWords, String clustersId,
			Date dealTime) {
		this.tbParserNews = tbParserNews;
		this.tbParserBbs = tbParserBbs;
		this.tbCluster = tbCluster;
		this.tbReBbs = tbReBbs;
		this.tbReNews = tbReNews;
		this.frequency = frequency;
		this.sensitiveWords = sensitiveWords;
		this.clustersId = clustersId;
		this.dealTime = dealTime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TbParserNews getTbParserNews() {
		return this.tbParserNews;
	}

	public void setTbParserNews(TbParserNews tbParserNews) {
		this.tbParserNews = tbParserNews;
	}

	public TbParserBbs getTbParserBbs() {
		return this.tbParserBbs;
	}

	public void setTbParserBbs(TbParserBbs tbParserBbs) {
		this.tbParserBbs = tbParserBbs;
	}

	public TbCluster getTbCluster() {
		return this.tbCluster;
	}

	public void setTbCluster(TbCluster tbCluster) {
		this.tbCluster = tbCluster;
	}

	public TbReBbs getTbReBbs() {
		return this.tbReBbs;
	}

	public void setTbReBbs(TbReBbs tbReBbs) {
		this.tbReBbs = tbReBbs;
	}

	public TbReNews getTbReNews() {
		return this.tbReNews;
	}

	public void setTbReNews(TbReNews tbReNews) {
		this.tbReNews = tbReNews;
	}

	public Integer getFrequency() {
		return this.frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public String getSensitiveWords() {
		return this.sensitiveWords;
	}

	public void setSensitiveWords(String sensitiveWords) {
		this.sensitiveWords = sensitiveWords;
	}

	public String getClustersId() {
		return this.clustersId;
	}

	public void setClustersId(String clustersId) {
		this.clustersId = clustersId;
	}

	public Date getDealTime() {
		return this.dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

}