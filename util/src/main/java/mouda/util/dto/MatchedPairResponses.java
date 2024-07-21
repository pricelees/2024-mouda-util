package mouda.util.dto;

import java.util.List;

public record MatchedPairResponses(
	List<MatchedPairResponse> matchedPairs,
	int count
) {

	public MatchedPairResponses(List<MatchedPairResponse> matchedPairs) {
		this(matchedPairs, matchedPairs.size());
	}
}
