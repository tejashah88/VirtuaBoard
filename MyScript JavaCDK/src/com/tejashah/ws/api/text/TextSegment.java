package com.tejashah.ws.api.text;
/**
 * MyScript Cloud Sample
 */
import java.util.List;

public class TextSegment {
	public List<String> inkRanges;
	public List<TextCandidate> candidates;
	public int candidatesSize;
	public int selectedCandidateIdx;

	public List<String> getInkRanges() {
		return inkRanges;
	}

	public void setInkRanges(List<String> inkRanges) {
		this.inkRanges = inkRanges;
	}

	public List<TextCandidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<TextCandidate> candidates) {
		this.candidates = candidates;
	}

	public int getCandidatesSize() {
		return candidatesSize;
	}

	public void setCandidatesSize(int candidatesSize) {
		this.candidatesSize = candidatesSize;
	}

	public int getSelectedCandidateIdx() {
		return selectedCandidateIdx;
	}

	public void setSelectedCandidateIdx(int selectedCandidateIdx) {
		this.selectedCandidateIdx = selectedCandidateIdx;
	}

}
