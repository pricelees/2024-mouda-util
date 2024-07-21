package mouda.util.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import mouda.util.dto.MatchedPairResponses;
import mouda.util.service.PairRandomMatchingService;

@Controller
@RequiredArgsConstructor
public class PairRandomMatchingController {

	private final PairRandomMatchingService pairRandomMatchingService;

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
}
