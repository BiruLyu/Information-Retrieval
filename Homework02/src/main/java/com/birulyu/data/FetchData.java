package com.birulyu.data;

public class FetchData {
	String url;
	int httpStatusCode;
	public FetchData(String url, int httpStatusCode) {
		super();
		this.url = url;
		this.httpStatusCode = httpStatusCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getHttpStatusCode() {
		return httpStatusCode;
	}
	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
	
	
}
