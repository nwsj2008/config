package edu.opinion.common.db;

/**
 * TbClusterTopic entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TbClusterTopic implements java.io.Serializable {

	// Fields

	private String id;
	private TbCluster tbCluster;
	private TbParserBbs tbParserBbs;
	private TbParserNews tbParserNews;

	// Constructors

	/** default constructor */
	public TbClusterTopic() {
	}

	/** minimal constructor */
	public TbClusterTopic(TbCluster tbCluster) {
		this.tbCluster = tbCluster;
	}

	/** full constructor */
	public TbClusterTopic(TbCluster tbCluster, TbParserBbs tbParserBbs,
			TbParserNews tbParserNews) {
		this.tbCluster = tbCluster;
		this.tbParserBbs = tbParserBbs;
		this.tbParserNews = tbParserNews;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TbCluster getTbCluster() {
		return this.tbCluster;
	}

	public void setTbCluster(TbCluster tbCluster) {
		this.tbCluster = tbCluster;
	}

	public TbParserBbs getTbParserBbs() {
		return this.tbParserBbs;
	}

	public void setTbParserBbs(TbParserBbs tbParserBbs) {
		this.tbParserBbs = tbParserBbs;
	}

	public TbParserNews getTbParserNews() {
		return this.tbParserNews;
	}

	public void setTbParserNews(TbParserNews tbParserNews) {
		this.tbParserNews = tbParserNews;
	}

}