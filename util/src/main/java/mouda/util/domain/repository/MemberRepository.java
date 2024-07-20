package mouda.util.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.util.domain.Member;
import mouda.util.domain.Part;
import mouda.util.domain.Team;

public interface MemberRepository extends JpaRepository<Member, Long> {
	List<Member> findAllByTeam(Team team);

	List<Member> findAllByPartAndTeam(Part part, Team team);
}
