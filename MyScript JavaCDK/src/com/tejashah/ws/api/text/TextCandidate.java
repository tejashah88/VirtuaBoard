package com.tejashah.ws.api.text;
/**
 * MyScript Cloud Sample
 */
import java.util.List;

public class TextCandidate {
	public String label;
	public float normalizedScore;
	public float resemblanceScore;
	public List<TextChild> children;
	public int childrenSize;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public float getNormalizedScore() {
		return normalizedScore;
	}

	public void setNormalizedScore(float normalizedScore) {
		this.normalizedScore = normalizedScore;
	}

	public float getResemblanceScore() {
		return resemblanceScore;
	}

	public void setResemblanceScore(float resemblanceScore) {
		this.resemblanceScore = resemblanceScore;
	}

	public List<TextChild> getChildren() {
		return children;
	}

	public void setChildren(List<TextChild> children) {
		this.children = children;
	}

	public int getChildrenSize() {
		return childrenSize;
	}

	public void setChildrenSize(int childrenSize) {
		this.childrenSize = childrenSize;
	}

}
