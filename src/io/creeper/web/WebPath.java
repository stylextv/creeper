package io.creeper.web;

import io.creeper.util.WebUtil;

public class WebPath {
	
	private final String string;
	
	public WebPath(String string) {
		this.string = string;
	}
	
	public boolean matchesDomainName(WebPath path) {
		String domainName1 = path.domainName();
		String domainName2 = domainName();
		
		if(domainName1 == null || domainName2 == null) return false;
		return domainName1.equals(domainName2);
	}
	
	public String fileEnding() {
		return WebUtil.pathFileEnding(string);
	}
	
	public String domainName() {
		return WebUtil.pathDomainName(string);
	}
	
	@Override
	public String toString() {
		return string;
	}
	
	public boolean equals(WebPath path) {
		String pathString = path.toString();
		return pathString.equals(string);
	}
	
}
