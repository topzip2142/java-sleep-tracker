package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import ru.yandex.practicum.sleeptracker.analyzers.*;
import ru.yandex.practicum.sleeptracker.enums.*;
import ru.yandex.practicum.sleeptracker.tools.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;


class SleepTrackerAppTest {
    private List<SleepingSession> sleepingSessions;

    @BeforeEach
    void createSessions() {
        sleepingSessions = new ArrayList<>();
    }

    @Test
    void testTotalSessionsAnalyzer() {
        LocalDateTime now = LocalDateTime.now();

        SleepingSession session1 = new SleepingSession(
                now.minusDays(3),
                now.minusDays(3).plusHours(8),
                SleepQuality.GOOD
        );

        SleepingSession session2 = new SleepingSession(
                now.minusDays(2),
                now.minusDays(2).plusHours(6),
                SleepQuality.NORMAL
        );

        SleepingSession session3 = new SleepingSession(
                now.minusDays(1),
                now.minusDays(1).plusHours(7),
                SleepQuality.BAD
        );

        sleepingSessions.add(session1);
        sleepingSessions.add(session2);
        sleepingSessions.add(session3);

        TotalSessionsAnalyzer analyzer = new TotalSessionsAnalyzer();
        SleepAnalysisResult result = analyzer.analyze(sleepingSessions);

        assertEquals("Количество сессий сна", result.getDescription());
        assertEquals("3", result.getValue());
        assertNotNull(result.toString());
        assertEquals("Количество сессий сна: 3", result.toString());
    }

    @Test
    void testChronotypeAnalyzer() {

        LocalDateTime owlSleepTime = LocalDateTime.of(2024, 1, 15, 23, 30);
        LocalDateTime owlWakeTime = LocalDateTime.of(2024, 1, 16, 10, 0);

        SleepingSession owlSession = new SleepingSession(
                owlSleepTime,
                owlWakeTime,
                SleepQuality.GOOD
        );

        sleepingSessions.add(owlSession);

        // Добавляем еще одну ночную сессию для подтверждения хронотипа
        LocalDateTime owlSleepTime2 = LocalDateTime.of(2026, 1, 16, 23, 45);
        LocalDateTime owlWakeTime2 = LocalDateTime.of(2026, 1, 17, 9, 30);

        SleepingSession owlSession2 = new SleepingSession(
                owlSleepTime2,
                owlWakeTime2,
                SleepQuality.GOOD
        );

        sleepingSessions.add(owlSession2);

        ChronotypeUserAnalyzer analyzer = new ChronotypeUserAnalyzer();
        SleepAnalysisResult result = analyzer.analyze(sleepingSessions);

        assertEquals("Хронотип пользователя", result.getDescription());
        assertEquals("сова", result.getValue());

        List<SleepingSession> larkSessions = new ArrayList<>();

        LocalDateTime larkSleepTime = LocalDateTime.of(2026, 1, 15, 21, 0);
        LocalDateTime larkWakeTime = LocalDateTime.of(2026, 1, 16, 6, 30);

        SleepingSession larkSession = new SleepingSession(
                larkSleepTime,
                larkWakeTime,
                SleepQuality.GOOD
        );

        larkSessions.add(larkSession);

        LocalDateTime larkSleepTime2 = LocalDateTime.of(2026, 1, 16, 21, 30);
        LocalDateTime larkWakeTime2 = LocalDateTime.of(2026, 1, 17, 6, 45);

        SleepingSession larkSession2 = new SleepingSession(
                larkSleepTime2,
                larkWakeTime2,
                SleepQuality.GOOD
        );

        larkSessions.add(larkSession2);

        SleepAnalysisResult larkResult = analyzer.analyze(larkSessions);

        assertEquals("Хронотип пользователя", larkResult.getDescription());
        assertEquals("жаворонок", larkResult.getValue());

        List<SleepingSession> emptySessions = new ArrayList<>();
        SleepAnalysisResult emptyResult = analyzer.analyze(emptySessions);

        assertEquals("Хронотип пользователя", emptyResult.getDescription());
        assertEquals("голубь", emptyResult.getValue());
    }


    @Test
    void testDaytimeSessions() {
        LocalDateTime sleepTime1 = LocalDateTime.of(2026, 3, 10, 6, 30);
        LocalDateTime wakeTime1 = LocalDateTime.of(2026, 3, 11, 8, 0);

        LocalDateTime sleepTime2 = LocalDateTime.of(2026, 3, 10, 5, 30);
        LocalDateTime wakeTime2 = LocalDateTime.of(2026, 3, 11, 5, 59);

        LocalDateTime sleepTime3 = LocalDateTime.of(2026, 3, 10, 23, 0);
        LocalDateTime wakeTime3 = LocalDateTime.of(2026, 3, 11, 5, 59);

        SleepingSession session1 = new SleepingSession(
                sleepTime1,
                wakeTime1,
                SleepQuality.GOOD
        );

        SleepingSession session2 = new SleepingSession(
                sleepTime2,
                wakeTime2,
                SleepQuality.NORMAL
        );

        SleepingSession session3 = new SleepingSession(
                sleepTime3,
                wakeTime3,
                SleepQuality.BAD
        );

        sleepingSessions.add(session1);
        sleepingSessions.add(session2);
        sleepingSessions.add(session3);

        DaytimeSessionsAnalyzer analyzer = new DaytimeSessionsAnalyzer();
        SleepAnalysisResult result = analyzer.analyze(sleepingSessions);

        assertEquals("Количество дневных сессий сна", result.getDescription());
        assertEquals("1", result.getValue());
        assertNotNull(result.toString());
        assertEquals("Количество дневных сессий сна: 1", result.toString());

    }

    @Test
    void testInsufficientSessionsAnalyzer() {
        LocalDateTime sleepTime1 = LocalDateTime.of(2026, 3, 10, 23, 0);
        LocalDateTime wakeTime1 = LocalDateTime.of(2026, 3, 11, 5, 59);

        LocalDateTime sleepTime2 = LocalDateTime.of(2026, 3, 10, 23, 0);
        LocalDateTime wakeTime2 = LocalDateTime.of(2026, 3, 11, 6, 0);

        LocalDateTime sleepTime3 = LocalDateTime.of(2026, 3, 10, 23, 0);
        LocalDateTime wakeTime3 = LocalDateTime.of(2026, 3, 11, 6, 1);

        SleepingSession session1 = new SleepingSession(
                sleepTime1,
                wakeTime1,
                SleepQuality.GOOD
        );

        SleepingSession session2 = new SleepingSession(
                sleepTime2,
                wakeTime2,
                SleepQuality.NORMAL
        );

        SleepingSession session3 = new SleepingSession(
                sleepTime3,
                wakeTime3,
                SleepQuality.BAD
        );

        sleepingSessions.add(session1);
        sleepingSessions.add(session2);
        sleepingSessions.add(session3);

        InsufficientSessionsAnalyzer analyzer = new InsufficientSessionsAnalyzer();
        SleepAnalysisResult result = analyzer.analyze(sleepingSessions);

        assertEquals("Количество сессий с недостаточным сном (<7 ч)", result.getDescription());
        assertEquals("1", result.getValue());
        assertNotNull(result.toString());
        assertEquals("Количество сессий с недостаточным сном (<7 ч): 1", result.toString());
    }
}