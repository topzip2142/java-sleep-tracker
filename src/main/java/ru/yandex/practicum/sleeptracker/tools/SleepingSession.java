package ru.yandex.practicum.sleeptracker.tools;

import ru.yandex.practicum.sleeptracker.enums.SleepQuality;

import java.time.Duration;
import java.time.LocalDateTime;

public class SleepingSession {
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final SleepQuality sleepQuality;

    public SleepingSession(LocalDateTime startDateTime, LocalDateTime endDateTime, SleepQuality sleepQuality) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.sleepQuality = sleepQuality;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public SleepQuality getQuality() {
        return sleepQuality;
    }

    public long getDurationMinutes() {
        return Duration.between(startDateTime, endDateTime).toMinutes();
    }

    @Override
    public String toString() {
        return "SleepingSession{" +
                "startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", quality=" + sleepQuality +
                '}';
    }
}
