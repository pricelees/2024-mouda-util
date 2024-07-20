package mouda.util.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import mouda.util.dto.AssignedSeatsResponse;
import mouda.util.service.SeatRandomAssignService;

@Controller
@RequiredArgsConstructor
public class SeatRandomAssignController {

	private final SeatRandomAssignService seatRandomAssignService;

	@PostMapping("/seats/randomAssigned")
	public String randomAssigned(
		@RequestParam(name = "team") String teamName,
		@RequestParam(name = "searchDate") String date,
		@RequestParam(name = "retry", defaultValue = "false") boolean isRetry,
		Model model
	) {
		LocalDate searchDate = LocalDate.parse(date);
		AssignedSeatsResponse response = seatRandomAssignService.assignSeats(teamName, searchDate, isRetry);

		model.addAttribute("seats", response.names());
		model.addAttribute("teamName", teamName);
		model.addAttribute("searchDate", searchDate);

		return "randomAssignedSeats";
	}

	@PostMapping("/pairs/randomMatching")
	public String pairsRandomMatching(
		@RequestParam(name = "team") String teamName,
		@RequestParam(name = "searchDate") String date,
		Model model
	) {
		LocalDate searchDate = LocalDate.parse(date);

		model.addAttribute("teamName", teamName);
		model.addAttribute("searchDate", searchDate);

		return "randomPairMatching";
	}
}
