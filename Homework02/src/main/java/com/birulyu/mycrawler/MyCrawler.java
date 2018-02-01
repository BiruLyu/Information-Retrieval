package com.birulyu.mycrawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import com.birulyu.data.DiscoverURLData;
import com.birulyu.data.FetchData;
import com.birulyu.data.SuccessfulDownloadData;
import com.birulyu.mycrawler.Controller;

/*
 * Two methods should be overridden in this class:
 * 1. shouldVisit: This function decides whether the given URL should be crawled or not.
 * 2. visit: This function is called after the content of a URL is downloaded successfully. 
 *        You can easily get the URL, text, links, html, and unique id of the downloaded page.
 * */
public class MyCrawler extends WebCrawler {
	private static Logger logger = LoggerFactory.getLogger(MyCrawler.class);
	//private final static Pattern PATTERN_ALLOWED_SIGNATURE = Pattern.compile(".*(\\.(html|doc|pdf"
           // + "|png|jpg|gif|bmp))$");
	//private final static Pattern PATTERN_ENDED_EMPTY = Pattern.compile("(^$|.*\\/[^(\\/\\.)]*$)");
	
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|rss"
            + "|mp3|mp4|zip|gz))$");
	
	
	private List<FetchData> fetchDataList = new ArrayList<>();
	private List<SuccessfulDownloadData> successfulDownloadDataList = new ArrayList<>();
	private List<DiscoverURLData> discoverURLDataList = new ArrayList<>();
   /*
	* This method receives two parameters. The first parameter is the page
	* in which we have discovered this new url and the second parameter is
	* the new url. You should implement this function to specify whether
	* the given url should be crawled or not (based on your crawling logic).
	* In this example, we are instructing the crawler to ignore urls that
	* have css, js, git, ... extensions and to only accept urls that start
	* with "http://www.viterbi.usc.edu/". In this case, we didn't need the
	* referringPage parameter to make the decision.
	*/
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		URL checkUrl;
		try {
			checkUrl = new URL(href);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.error("Can't process URL:" + href, e);
			return false;
		}
		if (!checkUrl.getHost().equals(Controller.CrawlSite)) {
			return false;
		}
		String checkPath = url.getPath();
		 
//		if(PATTERN_ENDED_EMPTY.matcher(checkPath).matches()){
//			return true;
//		}
		return !FILTERS.matcher(checkPath).matches();	
	}
	
	/*
	 * This function is called when a page is fetched and 
	 * ready to be processed by your program.
     */
	@Override
	protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
		fetchDataList.add(new FetchData(webUrl.getURL(),statusCode));
	}
	
	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL();
		System.out.println("URL: " + url);
		int numBytes = page.getContentData().length;
		int numOutLinks = page.getParseData().getOutgoingUrls().size();
		String contentType = page.getContentType();
		contentType = contentType.toLowerCase().indexOf("text/html") > -1 ? "text/html" : contentType;
		successfulDownloadDataList.add(new SuccessfulDownloadData(url, numBytes, numOutLinks, contentType));
		
		for (WebURL webUrl : page.getParseData().getOutgoingUrls()) {
			String encounterURL = webUrl.getURL().toLowerCase();
			boolean isOK = true;
			try {
				URL checkUrl = new URL(encounterURL);
				isOK = Controller.CrawlSite.equals(checkUrl.getHost());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				isOK = encounterURL.startsWith("http://" + Controller.CrawlSite) 
						|| encounterURL.startsWith("https://" + Controller.CrawlSite);
				e.printStackTrace();
			}
			discoverURLDataList.add(new DiscoverURLData(encounterURL, isOK ? "OK" : "N_OK"));
			
		}
		
	}
	
	
	@Override
	public MyCrawler getMyLocalData() {
		return this;
	}
	
	public List<FetchData> getFetchDataList() {
		return fetchDataList;
	}

	public List<SuccessfulDownloadData> getSuccessfulDownloadDataList() {
		return successfulDownloadDataList;
	}

	public List<DiscoverURLData> getDiscoverURLDataList() {
		return discoverURLDataList;
	}
}
