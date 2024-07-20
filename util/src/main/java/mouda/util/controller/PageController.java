package mouda.util.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import mouda.util.domain.Level3Week;

@Controller
public class PageController {

	@GetMapping("/")
	public String index(Model model) {
		LocalDate searchDate = LocalDate.now();
		Level3Week week = Level3Week.getWeekFromDate(searchDate);
		model.addAttribute("startFrom", week.getStartFrom());
		model.addAttribute("endAt", week.getEndAt());
		model.addAttribute("searchDate", searchDate);

		return "index";
	}
}
