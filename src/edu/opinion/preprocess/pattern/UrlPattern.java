package edu.opinion.preprocess.pattern;

public class UrlPattern {
	
	private String name;
	private String url;
	private String regex;
	private String module;
	private String sort;
	private String idKey;
	private String prefix;
	private String sufix;
	private String idName;
	
	public String getIdKey() {
		return idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSufix() {
		return sufix;
	}

	public void setSufix(String sufix) {
		this.sufix = sufix;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public UrlPattern() {		
	}
	
	public String getName(){
		return name;
	}
	
	public String getUrl(){
		return url;
	}
	
	public String getRegex(){
		return regex;
	}
	
	public String getModule(){
		return module;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public void setRegex(String regex){
		this.regex = regex;
	}
	
	public void setModule(String module){
		this.module = module;
	}
	
}
