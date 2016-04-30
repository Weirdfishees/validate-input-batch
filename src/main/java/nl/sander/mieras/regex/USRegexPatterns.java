package nl.sander.mieras.regex;


public class USRegexPatterns extends GeneralRegexPatterns {	
	
	public final static String VALID_US_STATES = "[A-Z]{2}";
	public final static String VALID_US_ZIP = "\\d{5}";
	public final static String VALID_US_PHONENUMBER = "(?:\\d{1}\\s)?\\(?(\\d{3})\\)?-?\\s?(\\d{3})-?\\s?(\\d{4})";
	
}


