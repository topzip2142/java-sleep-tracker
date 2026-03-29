package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.analyzers.*;
import ru.yandex.practicum.sleeptracker.enums.*;
import ru.yandex.practicum.sleeptracker.tools.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;


public class SleepTrackerApp {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    private static final String LOG_FILE_PATH = "src/main/resources/sleep_log.txt";
    private final List<SleepAnalyzable> analyzers;

    public  SleepTrackerApp() {
        this.analyzers = List.of(
                new TotalSessionsAnalyzer(),
                new MinDurationAnalyzer(),
                new AverageDurationAnalyzer(),
                new BadQualitySessionsAnalyzer(),
                new QualityAnalyzer(),
                new DaytimeSessionsAnalyzer(),
                new SleeplessSessionsAnalyzer(),
                new ChronotypeUserAnalyzer(),
                new InsufficientSessionsAnalyzer()
        );
    }

    public static void main(String[] args) {
        try {
            List<SleepingSession> sessions = loadSessionsFromFile();

            if (sessions.isEmpty()) {
                System.out.println("Файл не содержит данных о сессиях сна");
                return;
            }

            SleepTrackerApp sleepTrackerApp = new SleepTrackerApp();
            List<SleepAnalysisResult> results = sleepTrackerApp.createAnalysis(sessions);

            System.out.println("==== Анализатор сна ====");

            results.forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка формата данных: " + e.getMessage());
            System.exit(1);
        }
    }

    public List<SleepAnalysisResult> createAnalysis(List<SleepingSession> sessions) {
        return analyzers.stream()
                .map(analyzer -> analyzer.analyze(sessions))
                .collect(Collectors.toList());
    }

    private static List<SleepingSession> loadSessionsFromFile() throws IOException {
        return Files.readAllLines(Path.of(LOG_FILE_PATH)).stream()
                .filter(line -> !line.isBlank())
                .map(SleepTrackerApp::parseSession)
                .collect(Collectors.toList());
    }

    private static SleepingSession parseSession(String line) {
        String[] lineParts = line.split(";");
        if (lineParts.length != 3) {
            throw new IllegalArgumentException(
                    "Некорректный формат строки: " + line +
                            ". Ожидается: начало_засыпания;конец_пробуждения;качество_сна"
            );
        }
        try {
            LocalDateTime start = LocalDateTime.parse(lineParts[0].trim(), DATE_FORMATTER);
            LocalDateTime end = LocalDateTime.parse(lineParts[1].trim(), DATE_FORMATTER);
            SleepQuality quality = SleepQuality.valueOf(lineParts[2].trim());

            return new SleepingSession(start, end, quality);

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Ошибка даты в строке: " + line, e
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Неизвестное значение качества сна в строке: " + line +
                            ". Допустимые значения: GOOD, NORMAL, BAD", e
            );
        }
    }
}