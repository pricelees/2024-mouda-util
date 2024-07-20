package mouda.util.dto;

import java.util.List;

public record AssignedSeatsResponse(
	List<String> names,
	int count
) {

	public AssignedSeatsResponse(List<String> names) {
		this(names, names.size());
	}
}
