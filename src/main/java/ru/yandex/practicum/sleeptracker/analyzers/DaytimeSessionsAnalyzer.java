package ru.yandex.practicum.sleeptracker.analyzers;

import ru.yandex.practicum.sleeptracker.tools.*;

import java.util.List;

public class DaytimeSessionsAnalyzer implements SleepAnalyzable {
    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        long count = sessions.stream()
                .filter(session -> {
                    int hour = session.getStartDateTime().getHour();
                    return hour >= 6 && hour <= 20;
                })
                .count();

        return new SleepAnalysisResult("Количество дневных сессий сна", String.valueOf(count));
    }
}
