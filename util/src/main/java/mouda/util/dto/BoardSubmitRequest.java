package mouda.util.dto;

import org.hibernate.validator.constraints.Length;

public record BoardSubmitRequest(
	String team,
	@Length(min = 1, max = 5)
	String author,
	@Length(min = 1, max = 100)
	String content,
	boolean isPublic
) {
}
