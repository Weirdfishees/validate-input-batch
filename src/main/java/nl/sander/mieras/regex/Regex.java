package nl.sander.mieras.regex;


public enum Regex {
	
	ALPHABET_UPPER_LOWERCASE("[a-zA-Z]+"),
	DIGITS_ONLY("^\\d+");
	
	private String RegexAsString;
	
	private Regex(String regexAsString){
		this.RegexAsString = regexAsString;
	}
	
	@Override
	public String toString() {		
		return this.RegexAsString;
	}
}
