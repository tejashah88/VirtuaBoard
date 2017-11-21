package com.tejashah.ws.api.text;
/**
 * MyScript Cloud Sample
 */
public class TextParameter {
	public String resultDetail;
	public TextProperties textProperties;
	public String language;

	public TextParameter() {
		this.resultDetail = "TEXT";
		this.language = "en_US";
	}

	public String getResultDetail() {
		return resultDetail;
	}

	public void setResultDetail(String resultDetail) {
		this.resultDetail = resultDetail;
	}

	public TextProperties getTextProperties() {
		return textProperties;
	}

	public void setTextProperties(TextProperties textProperties) {
		this.textProperties = textProperties;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}