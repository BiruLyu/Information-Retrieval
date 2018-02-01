package com.birulyu.data;

public class DiscoverURLData {
	
	String url;
	String isOK;
	public DiscoverURLData(String url, String isOK) {
		super();
		this.url = url;
		this.isOK = isOK;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIsOK() {
		return isOK;
	}
	public void setIsOK(String isOK) {
		this.isOK = isOK;
	}
	
}
