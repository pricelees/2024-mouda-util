package mouda.util.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.util.domain.AssignedSeats;
import mouda.util.domain.Level3Week;

public interface AssignedSeatsRepository extends JpaRepository<AssignedSeats, Long> {
	AssignedSeats findByWeek(Level3Week week);

	boolean existsByWeek(Level3Week week);

	void deleteByWeek(Level3Week week);
}
