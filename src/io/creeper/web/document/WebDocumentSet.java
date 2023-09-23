package io.creeper.web.document;

import java.util.HashSet;
import java.util.Set;

public class WebDocumentSet {
	
	private final Set<WebDocument> documentStrings = new HashSet<>();
	
	public void addDocument(WebDocument document) {
		synchronized(documentStrings) {
			documentStrings.add(document);
		}
	}
	
	public boolean containsDocument(WebDocument document) {
		synchronized(documentStrings) {
			return documentStrings.contains(document);
		}
	}
	
}
