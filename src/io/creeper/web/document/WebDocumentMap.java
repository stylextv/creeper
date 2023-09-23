package io.creeper.web.document;

import io.creeper.web.WebPath;

import java.util.HashMap;
import java.util.Map;

public class WebDocumentMap {
	
	private final Map<String, WebDocument> documents = new HashMap<>();
	
	public void addDocument(WebDocument document) {
		WebPath path = document.getPath();
		String pathString = path.toString();
		
		synchronized(documents) {
			documents.put(pathString, document);
		}
	}
	
	public WebDocument getDocument(WebPath path) {
		String pathString = path.toString();
		
		synchronized(documents) {
			return documents.get(pathString);
		}
	}
	
}
