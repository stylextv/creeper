package io.creeper.web.crawl;

import io.creeper.web.WebPath;

public class WebCrawlerConfig {
	
	public static WebCrawlerConfig DEFAULT = new WebCrawlerConfig();
	
	private static final WebPath DEFAULT_CRAWL_START_PATH = null;
	private static final int DEFAULT_CRAWL_THREAD_AMOUNT = 4;
	
	private final WebPath crawlStartPath;
	private final int crawlThreadAmount;
	
	public WebCrawlerConfig() {
		this(DEFAULT_CRAWL_THREAD_AMOUNT);
	}
	
	public WebCrawlerConfig(int crawlThreadAmount) {
		this(DEFAULT_CRAWL_START_PATH, crawlThreadAmount);
	}
	
	public WebCrawlerConfig(WebPath crawlStartPath) {
		this(crawlStartPath, DEFAULT_CRAWL_THREAD_AMOUNT);
	}
	
	public WebCrawlerConfig(WebPath crawlStartPath, int crawlThreadAmount) {
		this.crawlStartPath = crawlStartPath;
		this.crawlThreadAmount = crawlThreadAmount;
	}
	
	public WebPath getCrawlStartPath() {
		return crawlStartPath;
	}
	
	public int getCrawlThreadAmount() {
		return crawlThreadAmount;
	}
	
}
