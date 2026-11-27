package ru.yandex.practicum.sleeptracker.analyzers;

import ru.yandex.practicum.sleeptracker.tools.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.tools.SleepAnalyzable;
import ru.yandex.practicum.sleeptracker.tools.SleepingSession;

import java.util.List;

public class AverageDurationAnalyzer implements SleepAnalyzable {
    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        double avgMinutes = sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .average()
                .orElse(0.0);

        long avgHours = (long) avgMinutes / 60;
        long avgMinutesRemainder = (long) avgMinutes % 60;
        String formatted = avgHours + " ч " + avgMinutesRemainder + " мин";

        return new SleepAnalysisResult("Средняя продолжительность сна", formatted);
    }
}
