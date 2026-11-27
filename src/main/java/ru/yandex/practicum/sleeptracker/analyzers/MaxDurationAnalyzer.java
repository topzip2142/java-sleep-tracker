package ru.yandex.practicum.sleeptracker.analyzers;

import ru.yandex.practicum.sleeptracker.tools.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.tools.SleepAnalyzable;
import ru.yandex.practicum.sleeptracker.tools.SleepingSession;

import java.util.List;

public class MaxDurationAnalyzer implements SleepAnalyzable {

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        long maxMinutes = sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .max()
                .orElse(0);

        long hours = maxMinutes / 60;
        long minutes = maxMinutes % 60;
        String formatted = hours + " ч " + minutes + " мин";

        return new SleepAnalysisResult("Максимальная продолжительность сессии сна", formatted);
    }
}
