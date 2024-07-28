package mouda.util.domain;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class ProgrammingPair {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String frontend;
	private String backend1;
	private String backend2;
	private Level3Week week;

	@Builder
	public ProgrammingPair(String frontend, String backend1, String backend2, Level3Week week) {
		this.frontend = frontend;
		this.backend1 = backend1;
		this.backend2 = backend2;
		this.week = week;
	}

	public List<String> getMembers() {
		return Arrays.asList(frontend, backend1, backend2);
	}
}
