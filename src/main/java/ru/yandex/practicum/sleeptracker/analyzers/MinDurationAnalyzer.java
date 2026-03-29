package ru.yandex.practicum.sleeptracker.analyzers;

import ru.yandex.practicum.sleeptracker.tools.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.tools.SleepAnalyzable;
import ru.yandex.practicum.sleeptracker.tools.SleepingSession;

import java.util.List;

public class MinDurationAnalyzer implements SleepAnalyzable {

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        long minMinutes = sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .min()
                .orElse(0);

        long hours = minMinutes / 60;
        long minutes = minMinutes % 60;
        String formatted = hours + " ч " + minutes + " мин";

        return new SleepAnalysisResult("Минимальная продолжительность сессии сна", formatted);
    }
}
