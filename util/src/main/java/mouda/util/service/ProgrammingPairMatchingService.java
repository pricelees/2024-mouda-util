package mouda.util.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.util.domain.AssignedSeats;
import mouda.util.domain.Level3Week;
import mouda.util.domain.Member;
import mouda.util.domain.Part;
import mouda.util.domain.ProgrammingPair;
import mouda.util.domain.Team;
import mouda.util.domain.repository.AssignedSeatsRepository;
import mouda.util.domain.repository.MemberRepository;
import mouda.util.domain.repository.ProgrammingPairRepository;
import mouda.util.domain.repository.TeamRepository;
import mouda.util.dto.MatchedProgrammingPairResponse;
import mouda.util.dto.MatchedProgrammingPairsResponse;

@Service
@RequiredArgsConstructor
public class ProgrammingPairMatchingService {

	private final ProgrammingPairRepository programmingPairRepository;
	private final TeamRepository teamRepository;
	private final MemberRepository memberRepository;
	private final AssignedSeatsRepository assignedSeatsRepository;

	public MatchedProgrammingPairsResponse matchPairs(String teamName, LocalDate searchDate) {
		Level3Week week = Level3Week.getWeekFromDate(searchDate);

		Team team = teamRepository.findByName(teamName);
		List<ProgrammingPair> pairs = getPairs(team, week);

		List<MatchedProgrammingPairResponse> matchedPairs = pairs.stream()
			.map(pair -> new MatchedProgrammingPairResponse(pair.getMembers()))
			.toList();
		List<String> seats = getRandomAssignedSeats(pairs, week);

		return new MatchedProgrammingPairsResponse(matchedPairs, seats);
	}

	private List<ProgrammingPair> getPairs(Team team, Level3Week week) {
		if (programmingPairRepository.existsByWeek(week)) {
			return programmingPairRepository.findAllByWeek(week);
		}
		List<Member> frontendMembers = memberRepository.findAllByPartAndTeam(Part.FRONTEND, team);
		List<Member> backendMembers = memberRepository.findAllByPartAndTeam(Part.BACKEND, team);
		Collections.shuffle(frontendMembers);
		Collections.shuffle(backendMembers);

		List<ProgrammingPair> result = new ArrayList<>();
		int backendMemberIndex = 0;

		for (Member frontendMember : frontendMembers) {
			String frontendMemberName = frontendMember.getName();
			String backendMember1 = backendMembers.get(backendMemberIndex++).getName();
			String backendMember2 =
				backendMemberIndex < backendMembers.size() ? backendMembers.get(backendMemberIndex++).getName() : "";
			ProgrammingPair pair = ProgrammingPair.builder()
				.frontend(frontendMemberName)
				.backend1(backendMember1)
				.backend2(backendMember2)
				.week(week)
				.build();
			result.add(pair);
		}
		Collections.shuffle(result);
		programmingPairRepository.saveAll(result);
		return result;
	}

	private List<String> getRandomAssignedSeats(List<ProgrammingPair> pairs, Level3Week week) {
		if (assignedSeatsRepository.existsByWeek(week)) {
			return Arrays.asList(assignedSeatsRepository.findByWeek(week).getSeats().split(","));
		}

		Collections.shuffle(pairs);
		List<String> seats = pairs.stream()
			.map(ProgrammingPair::getMembers)
			.peek(Collections::shuffle)
			.flatMap(Collection::stream)
			.map(name -> {
				return name.isBlank() ? "빈자리" : name;
			})
			.toList();
		assignedSeatsRepository.save(AssignedSeats.builder().week(week).seats(String.join(", ", seats)).build());
		return seats;
	}
}
