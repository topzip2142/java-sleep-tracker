package ru.yandex.practicum.sleeptracker.analyzers;

import ru.yandex.practicum.sleeptracker.tools.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.tools.SleepAnalyzable;
import ru.yandex.practicum.sleeptracker.tools.SleepingSession;
import ru.yandex.practicum.sleeptracker.enums.SleepQuality;

import java.util.List;

public class BadQualitySessionsAnalyzer implements SleepAnalyzable {
    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        long count = sessions.stream()
                .filter(session -> session.getQuality() == SleepQuality.BAD)
                .count();

        return new SleepAnalysisResult("Количество сессий с плохим качеством сна", String.valueOf(count));
    }
}
