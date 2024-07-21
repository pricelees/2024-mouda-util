package mouda.util.domain;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class Pair {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String member1;
	private String member2;

	@Enumerated(value = EnumType.STRING)
	private Level3Week week;

	@Builder
	public Pair(String member1, String member2, Level3Week week) {
		if (member1.equals(member2)) {
			throw new IllegalArgumentException("member1 and member2 must be different");
		}
		this.member1 = member1;
		this.member2 = member2;
		this.week = week;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Pair pair = (Pair)obj;
		return (Objects.equals(member1, pair.member1) && Objects.equals(member2, pair.member2)) ||
			(Objects.equals(member1, pair.member2) && Objects.equals(member2, pair.member1));
	}
}
