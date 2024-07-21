package mouda.util.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mouda.util.domain.Level3Week;
import mouda.util.domain.Pair;

public interface PairRepository extends JpaRepository<Pair, Long> {

	default boolean existsByMember1AndMember2(String member1, String member2) {
		return countByMembers(member1, member2) > 0;
	}

	@Query("""
		SELECT COUNT(p)
		FROM Pair p
		WHERE (p.member1 = :member1 AND p.member2 = :member2)
		OR (p.member1 = :member2 AND p.member2 = :member1)
		""")
	Long countByMembers(String member1, String member2);

	boolean existsByWeek(Level3Week week);

	List<Pair> findAllByWeek(Level3Week week);
}
