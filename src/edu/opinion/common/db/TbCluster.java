package edu.opinion.common.db;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TbCluster entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TbCluster implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private Date updateTime;
	private Date forecastTime;
	private Set tbSensitiveTopics = new HashSet(0);
	private Set tbHotTopics = new HashSet(0);
	private Set tbClusterTopics = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbCluster() {
	}

	/** minimal constructor */
	public TbCluster(String name, Date updateTime) {
		this.name = name;
		this.updateTime = updateTime;
	}

	/** full constructor */
	public TbCluster(String name, Date updateTime, Date forecastTime,
			Set tbSensitiveTopics, Set tbHotTopics, Set tbClusterTopics) {
		this.name = name;
		this.updateTime = updateTime;
		this.forecastTime = forecastTime;
		this.tbSensitiveTopics = tbSensitiveTopics;
		this.tbHotTopics = tbHotTopics;
		this.tbClusterTopics = tbClusterTopics;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getForecastTime() {
		return this.forecastTime;
	}

	public void setForecastTime(Date forecastTime) {
		this.forecastTime = forecastTime;
	}

	public Set getTbSensitiveTopics() {
		return this.tbSensitiveTopics;
	}

	public void setTbSensitiveTopics(Set tbSensitiveTopics) {
		this.tbSensitiveTopics = tbSensitiveTopics;
	}

	public Set getTbHotTopics() {
		return this.tbHotTopics;
	}

	public void setTbHotTopics(Set tbHotTopics) {
		this.tbHotTopics = tbHotTopics;
	}

	public Set getTbClusterTopics() {
		return this.tbClusterTopics;
	}

	public void setTbClusterTopics(Set tbClusterTopics) {
		this.tbClusterTopics = tbClusterTopics;
	}

}