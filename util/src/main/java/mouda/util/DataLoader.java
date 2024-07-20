package mouda.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.util.domain.Member;
import mouda.util.domain.Part;
import mouda.util.domain.Team;
import mouda.util.domain.repository.MemberRepository;
import mouda.util.domain.repository.TeamRepository;
import mouda.util.properties.TeamProperties;

@Component
@EnableConfigurationProperties(TeamProperties.class)
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

	private final TeamProperties teamProperties;
	private final TeamRepository teamRepository;
	private final MemberRepository memberRepository;

	@Override
	public void run(String... args) throws Exception {
		Team team = teamRepository.save(Team.builder().name(teamProperties.name()).build());

		teamProperties.parts().backend().members().forEach(memberName -> memberRepository.save(
			Member.builder().name(memberName).part(Part.BACKEND).team(team).build()
		));

		teamProperties.parts().frontend().members().forEach(memberName -> memberRepository.save(
			Member.builder().name(memberName).part(Part.FRONTEND).team(team).build()
		));
	}
}
