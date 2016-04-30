package nl.sander.mieras.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import nl.sander.mieras.regex.GeneralRegexPatterns;

public class Ranking extends GeneralRegexPatterns {
	
	@NotNull(message = "GlobalRank may not be null")
	@Pattern(regexp=DIGITS_ONLY, message = "GlobalRank has invalid characters")
	private String GlobalRank;
	
	@NotNull(message = "TldRank may not be null")
	@Pattern(regexp=DIGITS_ONLY, message = "TldRank has invalid characters")
	private String TldRank;
	
	@NotNull(message = "Domain may not be null")
	@Pattern(regexp=VALID_URL, message = "Domain has invalid characters")
	private String Domain;
	
	private String TLD;
	private String RefSubNets;
	private String RefIPs;
	private String IDN_Domain;
	private String IDN_TLD;
	
	// Explicity assuming that the column can only contain positive digits, however the column contains alot of negative digits!
	@NotNull(message = "PrevGlobalRank may not be null")
	@Pattern(regexp=DIGITS_ONLY, message = "PrevGlobalRank has invalid characters")
	private String PrevGlobalRank;
	
	@NotNull(message = "PrevTldRank may not be null")
	@Pattern(regexp=DIGITS_ONLY, message = "PrevTldRank has invalid characters")
	private String PrevTldRank;
	
	@NotNull(message = "PrevRefSubNets may not be null")
	@Pattern(regexp=DIGITS_ONLY, message = "PrevRefSubNets has invalid characters")
	private String PrevRefSubNets;
	
	@NotNull(message = "PrevRefIPs may not be null")
	@Pattern(regexp=DIGITS_ONLY, message = "PrevRefIPs has invalid characters")
	private String PrevRefIPs;
	
	public String getGlobalRank() {
		return GlobalRank;
	}
	
	public void setGlobalRank(String globalRank) {
		GlobalRank = globalRank;
	}
	
	public String getTldRank() {
		return TldRank;
	}
	
	public void setTldRank(String tldRank) {
		TldRank = tldRank;
	}
	
	public String getDomain() {
		return Domain;
	}
	
	public void setDomain(String domain) {
		Domain = domain;
	}
	
	public String getTLD() {
		return TLD;
	}
	
	public void setTLD(String tLD) {
		TLD = tLD;
	}
	
	public String getRefSubNets() {
		return RefSubNets;
	}
	
	public void setRefSubNets(String refSubNets) {
		RefSubNets = refSubNets;
	}
	
	public String getRefIPs() {
		return RefIPs;
	}
	
	public void setRefIPs(String refIPs) {
		RefIPs = refIPs;
	}
	
	public String getIDN_Domain() {
		return IDN_Domain;
	}
	
	public void setIDN_Domain(String iDN_Domain) {
		IDN_Domain = iDN_Domain;
	}
	
	public String getIDN_TLD() {
		return IDN_TLD;
	}
	
	public void setIDN_TLD(String iDN_TLD) {
		IDN_TLD = iDN_TLD;
	}
	
	public String getPrevGlobalRank() {
		return PrevGlobalRank;
	}
	
	public void setPrevGlobalRank(String prevGlobalRank) {
		PrevGlobalRank = prevGlobalRank;
	}
	
	public String getPrevTldRank() {
		return PrevTldRank;
	}
	
	public void setPrevTldRank(String prevTldRank) {
		PrevTldRank = prevTldRank;
	}
	
	public String getPrevRefSubNets() {
		return PrevRefSubNets;
	}
	
	public void setPrevRefSubNets(String prevRefSubNets) {
		PrevRefSubNets = prevRefSubNets;
	}
	
	public String getPrevRefIPs() {
		return PrevRefIPs;
	}
	
	public void setPrevRefIPs(String prevRefIPs) {
		PrevRefIPs = prevRefIPs;
	}

	@Override
	public String toString() {
		return "[GlobalRank=" + GlobalRank + ", TldRank=" + TldRank + ", Domain="
				+ Domain + ", TLD=" + TLD + ", RefSubNets=" + RefSubNets + ", RefIPs="
				+ RefIPs + ", IDN_Domain=" + IDN_Domain + ", IDN_TLD=" + IDN_TLD
				+ ", PrevGlobalRank=" + PrevGlobalRank + ", PrevTldRank=" + PrevTldRank
				+ ", PrevRefSubNets=" + PrevRefSubNets + ", PrevRefIPs=" + PrevRefIPs
				+ "]";
	}	
}
