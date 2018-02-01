package com.birulyu.data;

public class SuccessfulDownloadData {
	String url;
	long size;
	int numOfOutLinks;
	String contentType;
	public SuccessfulDownloadData(String url, long size, int numOfOutLinks, String contentType) {
		super();
		this.url = url;
		this.size = size;
		this.numOfOutLinks = numOfOutLinks;
		this.contentType = contentType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public int getNumOfOutLinks() {
		return numOfOutLinks;
	}
	public void setNumOfOutLinks(int numOfOutLinks) {
		this.numOfOutLinks = numOfOutLinks;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}	
	
}
