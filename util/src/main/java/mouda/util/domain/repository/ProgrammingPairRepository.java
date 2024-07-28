package mouda.util.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.util.domain.Level3Week;
import mouda.util.domain.ProgrammingPair;

public interface ProgrammingPairRepository extends JpaRepository<ProgrammingPair, Long> {

	boolean existsByWeek(Level3Week week);

	List<ProgrammingPair> findAllByWeek(Level3Week week);
}
