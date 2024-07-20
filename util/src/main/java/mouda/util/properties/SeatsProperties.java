package mouda.util.properties;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "seats")
public record SeatsProperties(
	int totalCount,
	String emptySeatName,
	String seatsDelimiter,
	AssignedCountByTeam assignedCountByTeam,
	Map<String, List<Integer>> assignablePositions
) {

	public record AssignedCountByTeam(int frontend, int backend) {
	}

	public List<Integer> getFrontendAssignablePositions() {
		return assignablePositions.get("frontend");
	}

	public List<Integer> getBackendAssignablePositions() {
		return assignablePositions.get("backend");
	}
}
