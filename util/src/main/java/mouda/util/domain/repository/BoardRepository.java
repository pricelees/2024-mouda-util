package mouda.util.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.util.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

	Board findByTeamName(String teamName);
}
