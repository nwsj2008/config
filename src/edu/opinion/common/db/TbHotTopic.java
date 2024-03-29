package edu.opinion.common.db;

/**
 * TbHotTopic entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TbHotTopic implements java.io.Serializable {

	// Fields

	private String id;
	private TbCluster tbCluster;

	// Constructors

	/** default constructor */
	public TbHotTopic() {
	}

	/** full constructor */
	public TbHotTopic(TbCluster tbCluster) {
		this.tbCluster = tbCluster;
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

}