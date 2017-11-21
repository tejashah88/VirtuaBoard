package com.tejashah.ws.api.text;
/**
 * MyScript Cloud Sample
 */
import java.util.List;

public class TextChild {
	public int selectedCandidateIdx;
	public List<String> inkRanges;

	public int getSelectedCandidateIdx() {
		return selectedCandidateIdx;
	}

	public void setSelectedCandidateIdx(int selectedCandidateIdx) {
		this.selectedCandidateIdx = selectedCandidateIdx;
	}

	public List<String> getInkRanges() {
		return inkRanges;
	}

	public void setInkRanges(List<String> inkRanges) {
		this.inkRanges = inkRanges;
	}

}
