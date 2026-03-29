package ru.yandex.practicum.sleeptracker.analyzers;

import ru.yandex.practicum.sleeptracker.tools.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SleeplessSessionsAnalyzer implements SleepAnalyzable {
    private static final LocalTime NIGHT_START = LocalTime.of(0, 0);
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);
    private static final LocalTime NOON = LocalTime.of(12, 0);

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Количество бессонных ночей", "0");
        }

        SleepingSession firstSession = sessions.stream()
                .min((s1, s2) -> s1.getStartDateTime().compareTo(s2.getStartDateTime()))
                .orElseThrow();

        SleepingSession lastSession = sessions.stream()
                .max((s1, s2) -> s1.getEndDateTime().compareTo(s2.getEndDateTime()))
                .orElseThrow();

        LocalDate firstNight = determineFirstNight(firstSession);
        LocalDate lastNight = determineLastNight(lastSession);

        Set<LocalDate> nightsWithSleep = sessions.stream()
                .flatMap(session -> getNightsCoveredBySession(session).stream())
                .collect(Collectors.toSet());

        long totalNights = java.time.temporal.ChronoUnit.DAYS.between(firstNight, lastNight) + 1;

        long nightsWithSleepInRange = nightsWithSleep.stream()
                .filter(night -> !night.isBefore(firstNight) && !night.isAfter(lastNight))
                .count();

        long sleeplessNights = totalNights - nightsWithSleepInRange;

        return new SleepAnalysisResult("Количество бессонных ночей", String.valueOf(sleeplessNights));
    }

    private LocalDate determineFirstNight(SleepingSession firstSession) {
        LocalDateTime start = firstSession.getStartDateTime();
        LocalTime startTime = start.toLocalTime();
        LocalDate startDate = start.toLocalDate();

        if (startTime.isAfter(NOON)) {
            return startDate.plusDays(1);
        } else {
            return startDate;
        }
    }

    private LocalDate determineLastNight(SleepingSession lastSession) {
        LocalDateTime end = lastSession.getEndDateTime();
        LocalTime endTime = end.toLocalTime();
        LocalDate endDate = end.toLocalDate();

        if (endTime.isBefore(NOON)) {
            return endDate.minusDays(1);
        } else {
            return endDate;
        }
    }

    private List<LocalDate> getNightsCoveredBySession(SleepingSession session) {
        LocalDateTime start = session.getStartDateTime();
        LocalDateTime end = session.getEndDateTime();

        LocalDate startNight = start.toLocalDate();
        LocalTime startTime = start.toLocalTime();

        if (startTime.isAfter(NIGHT_END)) {
            startNight = startNight.plusDays(1);
        }

        LocalDate endNight = end.toLocalDate();
        LocalTime endTime = end.toLocalTime();

        if (endTime.isBefore(NIGHT_END)) {
            endNight = endNight.minusDays(1);
        }

        if (endNight.isBefore(startNight)) {
            return new ArrayList<>();
        }

        List<LocalDate> nights = new ArrayList<>();
        LocalDate current = startNight;
        while (!current.isAfter(endNight)) {
            nights.add(current);
            current = current.plusDays(1);
        }
        return nights;
    }
}
