package mouda.util.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Seat {

	private Long position;
	private String memberName;
}
