package ru.yandex.practicum.sleeptracker.tools;

import java.util.List;

@FunctionalInterface
public interface SleepAnalyzable {
    SleepAnalysisResult analyze(List<SleepingSession> sessions);
}
