package nl.sander.mieras.regex;


public abstract class GeneralRegexPatterns {
	
	public final static String DIGITS_ONLY = "^\\d+";
	public final static String ALPHABET_UPPER_LOWERCASE = "[a-zA-Z]+";
	
	/**	The original specification of hostnames in RFC 952, 
	 *	mandated that labels could not start with a digit or with a hyphen, and must not end with a hyphen. 
	 *	However, a subsequent specification (RFC 1123) permitted hostname labels to start with digits.
	 */
	public final static String VALID_952_HOSTNAME = "^(([a-zA-Z]|[a-zA-Z][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z]|[A-Za-z][A-Za-z0-9\\-]*[A-Za-z0-9])$";
	
	// http://code.tutsplus.com/tutorials/8-regular-expressions-you-should-know--net-6149
	public final static String VALID_URL = "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$";
	
	// http://stackoverflow.com/questions/11757013/regular-expressions-for-city-name
	public final static String VALID_CITY = "^([a-zA-Z\\u0080-\\u024F]+(?:. |-| |'))*[a-zA-Z\\u0080-\\u024F]*$";
	
	// http://emailregex.com/
	public final static String VALID_EMAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";


}
