package io.creeper;

import io.creeper.web.WebPath;
import io.creeper.web.crawl.WebCrawler;
import io.creeper.web.document.WebDocument;

public class Creeper {
	
	private static final WebPath CRAWLER_PATH = new WebPath("https://fandom.overwatch.com");
	
	public static void main(String[] args) {
		WebCrawler crawler = new WebCrawler() {
			
			@Override
			public void exploreDocument(WebDocument document) {
				WebPath path = document.getPath();
				if(path.matchesDomainName(CRAWLER_PATH)) queueDocumentsOfDocument(document);
			}
			
			@Override
			public void discoverDocument(WebDocument document) {
				if(document.isImage()) System.out.println(document);
			}
		};
		
		crawler.crawl(CRAWLER_PATH);
	}
	
}
