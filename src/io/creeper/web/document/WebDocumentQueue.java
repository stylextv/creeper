package io.creeper.web.document;

import java.util.LinkedList;
import java.util.Queue;

public class WebDocumentQueue {
	
	private final Queue<WebDocument> documents = new LinkedList<>();
	
	public WebDocument pollDocument() {
		synchronized(documents) {
			
			if(documents.isEmpty()) return null;
			return documents.poll();
		}
	}
	
	public void addDocument(WebDocument document) {
		synchronized(documents) {
			
			documents.add(document);
		}
	}
	
}
