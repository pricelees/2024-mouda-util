package mouda.util.dto;

import java.util.List;

public record MatchedProgrammingPairsResponse(
	List<MatchedProgrammingPairResponse> pairs,
	List<String> seats
) {
}
