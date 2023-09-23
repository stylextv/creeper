package io.creeper.util;

import io.creeper.web.WebPath;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WebUtil {
	
	private static final String[] PATH_FILE_ENDING_SUFFIXES = new String[] { "/", "?" };
	private static final String PATH_FILE_ENDING_PREFIX = ".";
	private static final String PATH_PROTOCOL_SUFFIX = "//";
	private static final String DEFAULT_PATH_PROTOCOL = "https://";
	private static final String PATH_DOMAIN_NAME_SUFFIX = "/";
	private static final String RELATIVE_PATH_INDICATOR = "/";
	private static final String ABSOLUTE_PATH_INDICATOR = ".";
	private static final String PATH_SEPARATOR = "[^a-zA-z0-9$\\-_.+?!*:=/\\\\]";
	
	private static final Set<String> TEXT_FILE_ENDINGS = new HashSet<>();
	private static final Set<String> IMAGE_FILE_ENDINGS = new HashSet<>();
	private static final Set<String> VIDEO_FILE_ENDINGS = new HashSet<>();
	private static final Set<String> AUDIO_FILE_ENDINGS = new HashSet<>();
	
	private static final int FIRST_STRING_CHARACTER_INDEX = 0;
	private static final int INVALID_STRING_CHARACTER_INDEX = -1;
	
	static {
		TEXT_FILE_ENDINGS.add("txt");
		TEXT_FILE_ENDINGS.add("csv");
		TEXT_FILE_ENDINGS.add("json");
		TEXT_FILE_ENDINGS.add("toml");
		TEXT_FILE_ENDINGS.add("yml");
		TEXT_FILE_ENDINGS.add("yaml");
		TEXT_FILE_ENDINGS.add("html");
		TEXT_FILE_ENDINGS.add("js");
		TEXT_FILE_ENDINGS.add("css");
		
		IMAGE_FILE_ENDINGS.add("png");
		IMAGE_FILE_ENDINGS.add("jpg");
		IMAGE_FILE_ENDINGS.add("jpeg");
		IMAGE_FILE_ENDINGS.add("bmp");
		IMAGE_FILE_ENDINGS.add("webm");
		IMAGE_FILE_ENDINGS.add("svg");
		IMAGE_FILE_ENDINGS.add("ico");
		
		VIDEO_FILE_ENDINGS.add("mp4");
		VIDEO_FILE_ENDINGS.add("mov");
		VIDEO_FILE_ENDINGS.add("h264");
		
		AUDIO_FILE_ENDINGS.add("mp3");
		AUDIO_FILE_ENDINGS.add("ogg");
		AUDIO_FILE_ENDINGS.add("wav");
		AUDIO_FILE_ENDINGS.add("mid");
		AUDIO_FILE_ENDINGS.add("midi");
	}
	
	public static boolean isAudioFileEnding(String fileEnding) {
		return AUDIO_FILE_ENDINGS.contains(fileEnding);
	}
	
	public static boolean isVideoFileEnding(String fileEnding) {
		return VIDEO_FILE_ENDINGS.contains(fileEnding);
	}
	
	public static boolean isImageFileEnding(String fileEnding) {
		return IMAGE_FILE_ENDINGS.contains(fileEnding);
	}
	
	public static boolean isTextFileEnding(String fileEnding) {
		return TEXT_FILE_ENDINGS.contains(fileEnding);
	}
	
	public static String pathFileEnding(String pathString) {
		String domainName = pathDomainName(pathString);
		
		int index = pathString.indexOf(domainName);
		if(index == INVALID_STRING_CHARACTER_INDEX) return null;
		
		index += domainName.length();
		pathString = pathString.substring(index);
		
		int prefixIndex = pathString.lastIndexOf(PATH_FILE_ENDING_PREFIX);
		if(prefixIndex == INVALID_STRING_CHARACTER_INDEX) return null;
		
		prefixIndex += PATH_FILE_ENDING_PREFIX.length();
		pathString = pathString.substring(prefixIndex);
		
		for(String suffix : PATH_FILE_ENDING_SUFFIXES) {
			
			int suffixIndex = pathString.indexOf(suffix);
			if(suffixIndex == INVALID_STRING_CHARACTER_INDEX) continue;
			
			pathString = pathString.substring(FIRST_STRING_CHARACTER_INDEX, suffixIndex);
		}
		
		return pathString;
	}
	
	public static String pathDomainName(String pathString) {
		int suffixIndex = pathString.lastIndexOf(PATH_PROTOCOL_SUFFIX);
		if(suffixIndex != INVALID_STRING_CHARACTER_INDEX) {
			
			suffixIndex += PATH_PROTOCOL_SUFFIX.length();
			pathString = pathString.substring(suffixIndex);
		}
		
		suffixIndex = pathString.indexOf(PATH_DOMAIN_NAME_SUFFIX);
		if(suffixIndex == INVALID_STRING_CHARACTER_INDEX) return pathString;
		
		return pathString.substring(FIRST_STRING_CHARACTER_INDEX, suffixIndex);
	}
	
	public static List<String> pathStringsOfText(String text, WebPath textPath) {
		List<String> pathStrings = new ArrayList<>();
		String[] splitText = text.split(PATH_SEPARATOR);
		String domainName = textPath.domainName();
		
		for(String pathString : splitText) {
			
			if(pathString.startsWith(RELATIVE_PATH_INDICATOR)) pathString = DEFAULT_PATH_PROTOCOL + domainName + pathString;
			if(pathString.contains(ABSOLUTE_PATH_INDICATOR)) pathStrings.add(pathString);
		}
		
		return pathStrings;
	}
	
}
