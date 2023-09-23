package io.creeper.web.crawl;

import io.creeper.web.WebPath;
import io.creeper.web.document.WebDocument;

public class WebCrawlerState {
	
	private WebPath path;
	private WebDocument document;
	
	private int crawlDepth;
	
	public WebPath getPath() {
		return path;
	}
	
	public void setPath(WebPath path) {
		this.path = path;
	}
	
	public WebDocument getDocument() {
		return document;
	}
	
	public void setDocument(WebDocument document) {
		this.document = document;
	}
	
	public int getCrawlDepth() {
		return crawlDepth;
	}
	
	public void setCrawlDepth(int crawlDepth) {
		this.crawlDepth = crawlDepth;
	}
	
}
