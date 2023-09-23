package io.creeper.web.document.documents;

import io.creeper.web.document.WebDocument;
import io.creeper.web.WebPath;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class ImageWebDocument extends WebDocument {
	
	private Image image;
	
	public ImageWebDocument(WebPath path) {
		super(path);
	}
	
	@Override
	protected void fetch() throws IOException {
		InputStream inputStream = inputStream();
		
		image = ImageIO.read(inputStream);
	}
	
	public Image image() {
		fetchSynchronized();
		
		return image;
	}
	
}
