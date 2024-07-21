package mouda.util.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.util.domain.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
	Team findByName(String teamName);
}
