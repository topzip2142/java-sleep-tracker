package ru.yandex.practicum.sleeptracker.analyzers;

import ru.yandex.practicum.sleeptracker.tools.*;

import java.util.List;

public class InsufficientSessionsAnalyzer implements SleepAnalyzable {
    private static final long MIN_RECOMMENDED_MINUTES = 7 * 60;

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        long count = sessions.stream()
                .filter(session -> session.getDurationMinutes() < MIN_RECOMMENDED_MINUTES)
                .count();

        return new SleepAnalysisResult("Количество сессий с недостаточным сном (<7 ч)",
                                        String.valueOf(count));
    }
}
