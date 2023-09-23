package io.creeper.web.crawl;

import io.creeper.util.WebUtil;
import io.creeper.web.WebPath;
import io.creeper.web.document.WebDocument;
import io.creeper.web.document.WebDocumentQueue;
import io.creeper.web.document.WebDocumentSet;
import io.creeper.web.document.documents.TextWebDocument;

import java.util.List;

public abstract class WebCrawler {
	
	private static final int IDLE_ACTIVE_THREAD_AMOUNT = 0;
	
	private final WebCrawlerConfig config;
	
	private final WebDocumentQueue documents;
	private final WebDocumentSet discoveredDocuments;
	
	private boolean crawling;
	private int threadAmount;
	private int activeThreadAmount;
	
	public WebCrawler() {
		this(WebCrawlerConfig.DEFAULT);
	}
	
	public WebCrawler(WebCrawlerConfig config) {
		this.config = config;
		this.documents = new WebDocumentQueue();
		this.discoveredDocuments = new WebDocumentSet();
	}
	
	public void crawl() {
		WebPath path = config.getCrawlStartPath();
		crawl(path);
	}
	
	public void crawl(WebPath path) {
		queueDocument(path);
		
		synchronized(this) {
			crawling = true;
			
			for(int i = 0; i < config.getCrawlThreadAmount() - threadAmount; i++) {
				
				Thread thread = new Thread(() -> {
					synchronized(this) {
						threadAmount++;
					}
					
					while(true) {
						WebDocument document;
						
						synchronized(this) {
							if(!crawling) {
								threadAmount--;
								break;
							}
							
							document = documents.pollDocument();
							if(document == null) {
								
								if(activeThreadAmount == IDLE_ACTIVE_THREAD_AMOUNT) shutdown();
								continue;
							}
							
							activeThreadAmount++;
						}
						
						exploreDocument(document);
						
						synchronized(this) {
							activeThreadAmount--;
						}
					}
				});
				
				thread.start();
			}
		}
	}
	
	public void shutdown() {
		synchronized(this) {
			crawling = false;
		}
	}
	
	public abstract void exploreDocument(WebDocument document);
	public abstract void discoverDocument(WebDocument document);
	
	public void queueDocumentsOfDocument(WebDocument document) {
		if(!document.isText()) return;
		
		TextWebDocument textDocument = document.asText();
		String text = textDocument.text();
		if(text == null) return;
		
		WebPath textPath = document.getPath();
		List<String> pathStrings = WebUtil.pathStringsOfText(text, textPath);
		for(String pathString : pathStrings) queueDocument(pathString);
	}
	
	public void queueDocument(String pathString) {
		WebPath path = new WebPath(pathString);
		queueDocument(path);
	}
	
	public void queueDocument(WebPath documentPath) {
		WebDocument document = WebDocument.ofPath(documentPath);
		if(document == null) return;
		
		queueDocument(document);
	}
	
	public void queueDocument(WebDocument document) {
		synchronized(this) {
			
			if(discoveredDocuments.containsDocument(document)) return;
			discoveredDocuments.addDocument(document);
			discoverDocument(document);
			
			documents.addDocument(document);
		}
	}
	
	public WebCrawlerConfig getConfig() {
		return config;
	}
	
	public boolean isCrawling() {
		return crawling;
	}
	
}
