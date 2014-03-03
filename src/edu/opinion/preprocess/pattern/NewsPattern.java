package edu.opinion.preprocess.pattern;

public class NewsPattern {
	String news_name;
	String news_module;
	String news_url;
	public String getNews_name() {
		return news_name;
	}
	public void setNews_name(String news_name) {
		this.news_name = news_name;
	}
	public String getNews_module() {
		return news_module;
	}
	public void setNews_module(String news_module) {
		this.news_module = news_module;
	}
	public String getNews_url() {
		return news_url;
	}
	public void setNews_url(String news_url) {
		this.news_url = news_url;
	}
	
	String title_XPath;
	String edition_XPath;	
	String author_XPath;	
	String time_XPath;
	String content_XPath;
	String reNum_XPath;
	/**
	 * @return the title_XPath
	 */
	public String getTitle_XPath() {
		return title_XPath;
	}
	/**
	 * @param title_XPath the title_XPath to set
	 */
	public void setTitle_XPath(String title_XPath) {
		this.title_XPath = title_XPath;
	}
	/**
	 * @return the edition_XPath
	 */
	public String getEdition_XPath() {
		return edition_XPath;
	}
	/**
	 * @param edition_XPath the edition_XPath to set
	 */
	public void setEdition_XPath(String edition_XPath) {
		this.edition_XPath = edition_XPath;
	}
	/**
	 * @return the author_XPath
	 */
	public String getAuthor_XPath() {
		return author_XPath;
	}
	/**
	 * @param author_XPath the author_XPath to set
	 */
	public void setAuthor_XPath(String author_XPath) {
		this.author_XPath = author_XPath;
	}
	/**
	 * @return the time_XPath
	 */
	public String getTime_XPath() {
		return time_XPath;
	}
	/**
	 * @param time_XPath the time_XPath to set
	 */
	public void setTime_XPath(String time_XPath) {
		this.time_XPath = time_XPath;
	}
	/**
	 * @return the content_XPath
	 */
	public String getContent_XPath() {
		return content_XPath;
	}
	/**
	 * @param content_XPath the content_XPath to set
	 */
	public void setContent_XPath(String content_XPath) {
		this.content_XPath = content_XPath;
	}
	/**
	 * @return the reNum_XPath
	 */
	public String getReNum_XPath() {
		return reNum_XPath;
	}
	/**
	 * @param reNum_XPath the reNum_XPath to set
	 */
	public void setReNum_XPath(String reNum_XPath) {
		this.reNum_XPath = reNum_XPath;
	}	
}
