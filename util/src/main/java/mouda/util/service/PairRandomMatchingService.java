package mouda.util.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.util.domain.Level3Week;
import mouda.util.domain.Member;
import mouda.util.domain.Pair;
import mouda.util.domain.Team;
import mouda.util.domain.repository.MemberRepository;
import mouda.util.domain.repository.PairRepository;
import mouda.util.domain.repository.TeamRepository;
import mouda.util.dto.MatchedPairResponse;
import mouda.util.dto.MatchedPairResponses;

@Service
@RequiredArgsConstructor
public class PairRandomMatchingService {

	private final MemberRepository memberRepository;
	private final TeamRepository teamRepository;
	private final PairRepository pairRepository;

	public MatchedPairResponses matchPairs(String teamName, LocalDate searchDate) {
		Level3Week week = Level3Week.getWeekFromDate(searchDate);

		if (pairRepository.existsByWeek(week)) {
			return new MatchedPairResponses(pairRepository.findAllByWeek(week).stream()
				.map(pair -> new MatchedPairResponse(pair.getMember1(), pair.getMember2()))
				.toList());
		}

		Team team = teamRepository.findByName(teamName);
		List<Member> members = memberRepository.findAllByTeam(team);
		List<Pair> pairs;

		do {
			pairs = getPairs(members, week);
		} while (pairs.size() != (members.size() / 2));

		List<MatchedPairResponse> result = pairRepository.saveAll(pairs).stream()
			.map(pair -> new MatchedPairResponse(pair.getMember1(), pair.getMember2()))
			.toList();

		return new MatchedPairResponses(result);
	}

	private List<Pair> getPairs(List<Member> members, Level3Week week) {
		List<Pair> pairs;
		Collections.shuffle(members);
		Set<String> usedMembers = new HashSet<>();
		pairs = new ArrayList<>();

		for (int i = 0; i < members.size(); i++) {
			String member1 = members.get(i).getName();
			if (usedMembers.contains(member1)) {
				continue;
			}
			for (int j = i + 1; j < members.size(); j++) {
				String member2 = members.get(j).getName();
				if (usedMembers.contains(member2)) {
					continue;
				}
				Pair pair = Pair.builder()
					.member1(member1)
					.member2(member2)
					.week(week)
					.build();
				if (!pairRepository.existsByMember1AndMember2(member1, member2)) {
					pairs.add(pair);
					usedMembers.add(member1);
					usedMembers.add(member2);
					break;
				}
			}
		}
		return pairs;
	}
}
