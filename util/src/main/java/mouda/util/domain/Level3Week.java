package mouda.util.domain;

import java.time.LocalDate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Level3Week {

	WEEK_1(LocalDate.of(2024, 7, 1), LocalDate.of(2024, 7, 7)),
	WEEK_2(LocalDate.of(2024, 7, 8), LocalDate.of(2024, 7, 14)),
	WEEK_3(LocalDate.of(2024, 7, 15), LocalDate.of(2024, 7, 21)),
	WEEK_4(LocalDate.of(2024, 7, 22), LocalDate.of(2024, 7, 28)),
	WEEK_5(LocalDate.of(2024, 7, 29), LocalDate.of(2024, 8, 4)),
	WEEK_6(LocalDate.of(2024, 8, 5), LocalDate.of(2024, 8, 11)),
	WEEK_7(LocalDate.of(2024, 8, 12), LocalDate.of(2024, 8, 18)),
	WEEK_8(LocalDate.of(2024, 8, 19), LocalDate.of(2024, 8, 25));

	private final LocalDate startFrom;
	private final LocalDate endAt;

	public static Level3Week getWeekFromDate(LocalDate date) {
		for (Level3Week week : Level3Week.values()) {
			if (week.containsDate(date)) {
				return week;
			}
		}
		throw new RuntimeException("Level3 기간이 아니에요.");
	}

	private boolean containsDate(LocalDate date) {
		return date.isAfter(startFrom.minusDays(1)) && date.isBefore(endAt.plusDays(1));
	}
}
