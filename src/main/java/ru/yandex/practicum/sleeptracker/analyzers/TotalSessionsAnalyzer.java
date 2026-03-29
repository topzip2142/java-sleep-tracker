package ru.yandex.practicum.sleeptracker.analyzers;

import ru.yandex.practicum.sleeptracker.tools.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.tools.SleepAnalyzable;
import ru.yandex.practicum.sleeptracker.tools.SleepingSession;

import java.util.List;

public class TotalSessionsAnalyzer implements SleepAnalyzable {
    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        return new SleepAnalysisResult("Количество сессий сна", String.valueOf(sessions.size()));
    }
}
