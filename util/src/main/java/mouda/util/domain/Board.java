package mouda.util.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class Board {

	@Id
	@GeneratedValue
	private Long id;

	private String teamName;

	@Builder
	public Board(String teamName) {
		this.teamName = teamName;
	}
}
