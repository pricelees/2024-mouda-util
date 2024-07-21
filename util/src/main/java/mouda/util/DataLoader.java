package mouda.util;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.util.domain.Board;
import mouda.util.domain.Level3Week;
import mouda.util.domain.Member;
import mouda.util.domain.Pair;
import mouda.util.domain.Part;
import mouda.util.domain.Team;
import mouda.util.domain.repository.BoardRepository;
import mouda.util.domain.repository.MemberRepository;
import mouda.util.domain.repository.PairRepository;
import mouda.util.domain.repository.TeamRepository;
import mouda.util.properties.TeamProperties;

@Component
@EnableConfigurationProperties(TeamProperties.class)
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

	private final TeamProperties teamProperties;
	private final TeamRepository teamRepository;
	private final MemberRepository memberRepository;
	private final PairRepository pairRepository;
	private final BoardRepository boardRepository;

	@Override
	public void run(String... args) throws Exception {
		Team team = teamRepository.save(Team.builder().name(teamProperties.name()).build());

		teamProperties.parts().backend().members().forEach(memberName -> memberRepository.save(
			Member.builder().name(memberName).part(Part.BACKEND).team(team).build()
		));

		teamProperties.parts().frontend().members().forEach(memberName -> memberRepository.save(
			Member.builder().name(memberName).part(Part.FRONTEND).team(team).build()
		));

		addPairRecords();

		Board board = Board.builder().teamName(team.getName()).build();
		boardRepository.save(board);
	}

	private void addPairRecords() {
		String 안나 = "안나";
		String 호기 = "호기";
		String 치코 = "치코";
		String 테바 = "테바";
		String 소파 = "소파";
		String 테니 = "테니";
		String 수야 = "수야";
		String 상돌 = "상돌";

		pairRepository.saveAll(List.of(
			Pair.builder().member1(테바).member2(테니).week(Level3Week.WEEK_2).build(),
			Pair.builder().member1(안나).member2(상돌).week(Level3Week.WEEK_2).build(),
			Pair.builder().member1(호기).member2(수야).week(Level3Week.WEEK_2).build(),
			Pair.builder().member1(소파).member2(치코).week(Level3Week.WEEK_2).build()
		));

		pairRepository.saveAll(List.of(
			Pair.builder().member1(테바).member2(소파).week(Level3Week.WEEK_3).build(),
			Pair.builder().member1(테니).member2(호기).week(Level3Week.WEEK_3).build(),
			Pair.builder().member1(상돌).member2(수야).week(Level3Week.WEEK_3).build(),
			Pair.builder().member1(안나).member2(치코).week(Level3Week.WEEK_3).build()
		));
	}
}
