package io.creeper.web.document;

import io.creeper.util.WebUtil;
import io.creeper.web.WebPath;
import io.creeper.web.document.documents.AudioWebDocument;
import io.creeper.web.document.documents.ImageWebDocument;
import io.creeper.web.document.documents.TextWebDocument;
import io.creeper.web.document.documents.VideoWebDocument;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public abstract class WebDocument {
	
	private static final WebDocumentMap DOCUMENTS = new WebDocumentMap();
	
	private final WebPath path;
	
	private boolean fetched;
	
	public WebDocument(WebPath path) {
		this.path = path;
	}
	
	protected abstract void fetch() throws IOException;
	
	public void fetchSynchronized() {
		synchronized(this) {
			
			if(fetched) return;
			fetched = true;
			
			try {
				
				fetch();
				
			} catch(Exception exception) {}
		}
	}
	
	public InputStream inputStream() throws IOException {
		String pathString = path.toString();
		
		URL url = new URL(pathString);
		return url.openStream();
	}
	
	@Override
	public String toString() {
		return path.toString();
	}
	
	public AudioWebDocument asAudio() {
		return (AudioWebDocument) this;
	}
	
	public VideoWebDocument asVideo() {
		return (VideoWebDocument) this;
	}
	
	public ImageWebDocument asImage() {
		return (ImageWebDocument) this;
	}
	
	public TextWebDocument asText() {
		return (TextWebDocument) this;
	}
	
	public boolean isAudio() {
		return this instanceof AudioWebDocument;
	}
	
	public boolean isVideo() {
		return this instanceof VideoWebDocument;
	}
	
	public boolean isImage() {
		return this instanceof ImageWebDocument;
	}
	
	public boolean isText() {
		return this instanceof TextWebDocument;
	}
	
	public boolean equals(WebDocument document) {
		WebPath documentPath = document.getPath();
		return documentPath.equals(path);
	}
	
	public WebPath getPath() {
		return path;
	}
	
	public boolean isFetched() {
		return fetched;
	}
	
	public static WebDocument ofPath(WebPath path) {
		WebDocument document = DOCUMENTS.getDocument(path);
		if(document != null) return document;
		
		String fileEnding = path.fileEnding();
		
		if(fileEnding == null) document = new TextWebDocument(path);
		else if(WebUtil.isTextFileEnding(fileEnding)) document = new TextWebDocument(path);
		else if(WebUtil.isImageFileEnding(fileEnding)) document = new ImageWebDocument(path);
		else if(WebUtil.isVideoFileEnding(fileEnding)) document = new VideoWebDocument(path);
		else if(WebUtil.isAudioFileEnding(fileEnding)) document = new AudioWebDocument(path);
		
		if(document == null) return null;
		
		DOCUMENTS.addDocument(document);
		return document;
	}
	
}
