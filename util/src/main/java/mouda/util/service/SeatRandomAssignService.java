package mouda.util.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.util.domain.AssignedSeats;
import mouda.util.domain.Level3Week;
import mouda.util.domain.Member;
import mouda.util.domain.Part;
import mouda.util.domain.Team;
import mouda.util.domain.repository.AssignedSeatsRepository;
import mouda.util.domain.repository.MemberRepository;
import mouda.util.domain.repository.TeamRepository;
import mouda.util.dto.AssignedSeatsResponse;
import mouda.util.properties.SeatsProperties;

@Service
@EnableConfigurationProperties(SeatsProperties.class)
@RequiredArgsConstructor
public class SeatRandomAssignService {

	private final SeatsProperties seatsProperties;
	private final AssignedSeatsRepository assignedSeatsRepository;
	private final TeamRepository teamRepository;
	private final MemberRepository memberRepository;

	public AssignedSeatsResponse assignSeats(String teamName, LocalDate searchDate) {
		Team team = teamRepository.findByName(teamName);
		Level3Week week = Level3Week.getWeekFromDate(searchDate);
		if (assignedSeatsRepository.existsByWeek(week)) {
			return getAssignedSeatsIfExist(week);
		}

		List<String> frontendSeats = getRandomAssignedSeats(Part.FRONTEND, team);
		List<String> backendSeats = getRandomAssignedSeats(Part.BACKEND, team);

		return assign(frontendSeats, backendSeats, week);
	}

	private AssignedSeatsResponse getAssignedSeatsIfExist(Level3Week week) {
		AssignedSeats assignedSeats = assignedSeatsRepository.findByWeek(week);
		List<String> seats = Arrays.asList(assignedSeats.getSeats().split(seatsProperties.seatsDelimiter()));

		return new AssignedSeatsResponse(seats);
	}

	private AssignedSeatsResponse assign(List<String> frontendSeats, List<String> backendSeats, Level3Week week) {
		int totalSeats = seatsProperties.totalCount();
		List<String> result = new ArrayList<>(Collections.nCopies(totalSeats, seatsProperties.emptySeatName()));

		SeatsProperties.AssignedCountByTeam assignedCountByTeam = seatsProperties.assignedCountByTeam();

		for (int i = 0; i < assignedCountByTeam.frontend(); i++) {
			result.set(seatsProperties.getFrontendAssignablePositions().get(i), frontendSeats.get(i));
		}

		for (int i = 0; i < assignedCountByTeam.backend(); i++) {
			result.set(seatsProperties.getBackendAssignablePositions().get(i), backendSeats.get(i));
		}

		saveAssignedSeats(result, week);
		return new AssignedSeatsResponse(result);
	}

	private void saveAssignedSeats(List<String> result, Level3Week week) {
		AssignedSeats assignedSeats = AssignedSeats.builder()
			.week(week)
			.seats(String.join(seatsProperties.seatsDelimiter(), result))
			.build();
		assignedSeatsRepository.save(assignedSeats);
	}

	private List<String> getRandomAssignedSeats(Part part, Team team) {
		List<String> names = getMembersByTeamAndParts(part, team);

		SeatsProperties.AssignedCountByTeam assignedCountByTeam = seatsProperties.assignedCountByTeam();

		int assignedCount = part == Part.FRONTEND ? assignedCountByTeam.frontend() : assignedCountByTeam.backend();

		for (int i = names.size(); i < assignedCount; i++) {
			names.add(seatsProperties.emptySeatName());
		}

		Collections.shuffle(names);
		return names;
	}

	private List<String> getMembersByTeamAndParts(Part part, Team team) {
		List<String> names = memberRepository.findAllByPartAndTeam(part, team).stream()
			.map(Member::getName).toList();

		return new ArrayList<>(names);
	}
}
