package ru.yandex.practicum.sleeptracker.analyzers;

import ru.yandex.practicum.sleeptracker.tools.*;
import ru.yandex.practicum.sleeptracker.enums.SleepQuality;

import java.util.List;

public class QualityAnalyzer implements SleepAnalyzable {
    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        long goodCount = sessions.stream()
                .filter(session -> session.getQuality() == SleepQuality.GOOD)
                .count();

        long normalCount = sessions.stream()
                .filter(session -> session.getQuality() == SleepQuality.NORMAL)
                .count();

        long badCount = sessions.stream()
                .filter(session -> session.getQuality() == SleepQuality.BAD)
                .count();

        String combinedResult = "хорошее - " + goodCount + ", нормальное - " + normalCount + ", плохое - " + badCount;

        return new SleepAnalysisResult("Качество сна", combinedResult);
    }
}
