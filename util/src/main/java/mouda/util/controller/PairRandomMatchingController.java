package mouda.util.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import mouda.util.dto.MatchedPairResponses;
import mouda.util.dto.MatchedProgrammingPairsResponse;
import mouda.util.service.PairRandomMatchingService;
import mouda.util.service.ProgrammingPairMatchingService;

@Controller
@RequiredArgsConstructor
public class PairRandomMatchingController {

	private final PairRandomMatchingService pairRandomMatchingService;
	private final ProgrammingPairMatchingService programmingPairMatchingService;

	@PostMapping("/pairs/randomMatching")
	public String pairsRandomMatching(
		@RequestParam(name = "team") String teamName,
		@RequestParam(name = "searchDate") String date,
		Model model
	) {
		LocalDate searchDate = LocalDate.parse(date);
		MatchedPairResponses response = pairRandomMatchingService.matchPairs(teamName, searchDate);
		System.out.println("response = " + response);
		model.addAttribute("pairs", response.matchedPairs());

		return "randomPairMatching";
	}

	@PostMapping("/pairs/programming")
	public String programmingPairs(
		@RequestParam(name = "team") String teamName,
		@RequestParam(name = "searchDate") String date,
		Model model
	) {
		LocalDate searchDate = LocalDate.parse(date);
		MatchedProgrammingPairsResponse response = programmingPairMatchingService.matchPairs(
			teamName, searchDate);
		model.addAttribute("pairs", response.pairs());
		model.addAttribute("seats", response.seats());

		return "randomProgrammingPairMatching";
	}
}
