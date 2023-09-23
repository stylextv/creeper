package io.creeper.web.document.documents;

import io.creeper.web.WebPath;
import io.creeper.web.document.WebDocument;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class AudioWebDocument extends WebDocument {
	
	private static final String LINE_SEPARATOR = System.lineSeparator();
	
	private String text;
	
	public AudioWebDocument(WebPath path) {
		super(path);
	}
	
	@Override
	protected void fetch() throws IOException {
		InputStream inputStream = inputStream();
		Scanner inputScanner = new Scanner(inputStream);
		StringBuilder builder = new StringBuilder();
		
		while(inputScanner.hasNextLine()) {
			String line = inputScanner.nextLine();
			
			builder.append(line);
			builder.append(LINE_SEPARATOR);
		}
		
		inputScanner.close();
		
		text = builder.toString();
	}
	
	public String text() {
		fetchSynchronized();
		
		return text;
	}
	
}
