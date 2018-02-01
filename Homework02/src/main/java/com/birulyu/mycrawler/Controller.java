package com.birulyu.mycrawler;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.birulyu.data.DiscoverURLData;
import com.birulyu.data.FetchData;
import com.birulyu.data.SuccessfulDownloadData;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {
	
	public static final String CrawlSite = "www.nydailynews.com";
	public static final String NewsSite = "New_York_Daily_News";
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String crawlStorageFolder = "../data/file/";
		String fetchDataFile = "fetch_" + NewsSite + ".csv";
		String successfulDownloadDataFile = "visit_" + NewsSite + ".csv";
		String discoverURLDataFile = "urls_" + NewsSite + ".csv";
		
		int numberOfCrawlers = 7;
		CrawlConfig config = new CrawlConfig();
		config.setIncludeHttpsPages(true);
		config.setFollowRedirects(true);
		config.setMaxDepthOfCrawling(16);
		config.setMaxPagesToFetch(20000);
		config.setCrawlStorageFolder(crawlStorageFolder);
		
		
		/*
		 * Instantiate the controller for this crawl.
		 */
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
		
		/*
		 * For each crawl, you need to add some seed urls.
		 * These are the first URLs that are fetched and then crawler starts following links
		 * which are found in these pages
		 */
		
		controller.addSeed("http://www.nydailynews.com/");
		
	
		/*
		 * Start the crawl. This is blocking operation, 
		 * meaning that your code will reach the line after this 
		 * only when crawling is finished
		 */
		controller.start(MyCrawler.class, numberOfCrawlers);
		
		List<FetchData> fetchDataList = new ArrayList<>();
		List<SuccessfulDownloadData> successfulDownloadDataList = new ArrayList<>();
		List<DiscoverURLData> discoverURLDataList = new ArrayList<>();
		
		List<Object> crawlers = controller.getCrawlersLocalData();
		for(Object crawler:crawlers){
			MyCrawler myCrawler = (MyCrawler)crawler;
			fetchDataList.addAll(myCrawler.getFetchDataList());
			successfulDownloadDataList.addAll(myCrawler.getSuccessfulDownloadDataList());
			discoverURLDataList.addAll(myCrawler.getDiscoverURLDataList());
		}
		CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader("url","httpStatusCode");
		try(
				FileWriter fw = new FileWriter(crawlStorageFolder + fetchDataFile);
				CSVPrinter csvPrinter = new CSVPrinter(fw,csvFormat);
		){
			for(FetchData fetchData: fetchDataList){
				List<String> record = new ArrayList<>();
				record.add(fetchData.getUrl());
				record.add(fetchData.getHttpStatusCode() + "");
				csvPrinter.printRecord(record);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		csvFormat = CSVFormat.DEFAULT.withHeader("url","size","numOutlinks","content-type");
		try(
				FileWriter fw = new FileWriter(crawlStorageFolder + successfulDownloadDataFile);
				CSVPrinter csvPrinter = new CSVPrinter(fw,csvFormat);
		){
			for(SuccessfulDownloadData successfulDownloadData: successfulDownloadDataList){
				List<String> record = new ArrayList<>();
				record.add(successfulDownloadData.getUrl());
				record.add(successfulDownloadData.getSize() + "");
				record.add(successfulDownloadData.getNumOfOutLinks() + "");
				record.add(successfulDownloadData.getContentType());
				csvPrinter.printRecord(record);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		csvFormat = CSVFormat.DEFAULT.withHeader("url","isOk");
		try(
				FileWriter fw = new FileWriter(crawlStorageFolder + discoverURLDataFile);
				CSVPrinter csvPrinter = new CSVPrinter(fw,csvFormat);
		){
			for(DiscoverURLData discoverURLData: discoverURLDataList){
				List<String> record = new ArrayList<>();
				record.add(discoverURLData.getUrl());
				record.add(discoverURLData.getIsOK());
				csvPrinter.printRecord(record);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
