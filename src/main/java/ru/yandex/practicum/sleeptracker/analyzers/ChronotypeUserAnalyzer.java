package ru.yandex.practicum.sleeptracker.analyzers;

import ru.yandex.practicum.sleeptracker.enums.Chronotype;
import ru.yandex.practicum.sleeptracker.tools.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class ChronotypeUserAnalyzer implements SleepAnalyzable {
    private static final LocalTime OWL_SLEEP_START = LocalTime.of(23, 0);
    private static final LocalTime OWL_WAKE_END = LocalTime.of(9, 0);
    private static final LocalTime LARK_SLEEP_END = LocalTime.of(22, 0);
    private static final LocalTime LARK_WAKE_END = LocalTime.of(7, 0);
    private static final LocalTime NIGHT_START = LocalTime.of(0, 0);
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    private static final String OWL_TEXT = "сова";
    private static final String LARK_TEXT = "жаворонок";
    private static final String PIGEON_TEXT = "голубь";

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Хронотип пользователя", PIGEON_TEXT);
        }

        List<SleepingSession> nightSessions = sessions.stream()
                .filter(this::isNightSession)
                .toList();

        if (nightSessions.isEmpty()) {
            return new SleepAnalysisResult("Хронотип пользователя", PIGEON_TEXT);
        }

        long owlCount = nightSessions.stream()
                .filter(session -> classifyNight(session) == Chronotype.OWL)
                .count();

        long larkCount = nightSessions.stream()
                .filter(session -> classifyNight(session) == Chronotype.LARK)
                .count();

        long pigeonCount = nightSessions.stream()
                .filter(session -> classifyNight(session) == Chronotype.PIGEON)
                .count();

        String resultDisplayName;
        if (owlCount > larkCount && owlCount > pigeonCount) {
            resultDisplayName = OWL_TEXT;
        } else if (larkCount > owlCount && larkCount > pigeonCount) {
            resultDisplayName = LARK_TEXT;
        } else {
            resultDisplayName = PIGEON_TEXT;
        }

        return new SleepAnalysisResult("Хронотип пользователя", resultDisplayName);
    }

    private boolean isNightSession(SleepingSession session) {
        LocalDateTime start = session.getStartDateTime();
        LocalDateTime end = session.getEndDateTime();

        LocalDate startDate = start.toLocalDate();
        LocalDate endDate = end.toLocalDate();

        if (endDate.isAfter(startDate)) {
            return true;
        }

        LocalTime startTime = start.toLocalTime();
        LocalTime endTime = end.toLocalTime();

        return startTime.isBefore(NIGHT_END) || endTime.isAfter(NIGHT_START);
    }

    private Chronotype classifyNight(SleepingSession session) {
        LocalTime sleepTime = session.getStartDateTime().toLocalTime();
        LocalTime wakeTime = session.getEndDateTime().toLocalTime();

        if (sleepTime.isAfter(OWL_SLEEP_START) && wakeTime.isAfter(OWL_WAKE_END)) {
            return Chronotype.OWL;
        }

        if (sleepTime.isBefore(LARK_SLEEP_END) && wakeTime.isBefore(LARK_WAKE_END)) {
            return Chronotype.LARK;
        }

        return Chronotype.PIGEON;
    }
}
