package com.pravinviswa.agecalculator;

import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AgeController {

    @GetMapping("/calculate-age")
    public Map<String, Object> calculateAge(
            @RequestParam String dob,
            @RequestParam(required = false) String timezone) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Parse full date-time
            LocalDateTime birthDateTime = LocalDateTime.parse(dob);
            ZoneId userZone = (timezone != null && !timezone.isEmpty()) ? ZoneId.of(timezone) : ZoneId.systemDefault();

            ZonedDateTime birthZoned = birthDateTime.atZone(userZone);
            ZonedDateTime nowZoned = ZonedDateTime.now(userZone);

            if (birthZoned.isAfter(nowZoned)) {
                Duration duration = Duration.between(nowZoned, birthZoned);
                Period period = Period.between(nowZoned.toLocalDate(), birthZoned.toLocalDate());

                response.put("status", "future");
                response.put("yearsLeft", period.getYears());
                response.put("monthsLeft", period.getMonths());
                response.put("daysLeft", period.getDays());
                response.put("hoursLeft", duration.toHoursPart());
                response.put("minutesLeft", duration.toMinutesPart());
                response.put("secondsLeft", duration.toSecondsPart());
            } else {
                Period period = Period.between(birthZoned.toLocalDate(), nowZoned.toLocalDate());
                Duration duration = Duration.between(birthZoned, nowZoned);

                response.put("status", "past");
                response.put("years", period.getYears());
                response.put("months", period.getMonths());
                response.put("days", period.getDays());
                response.put("hours", duration.toHoursPart());
                response.put("minutes", duration.toMinutesPart());
                response.put("seconds", duration.toSecondsPart());
            }

            response.put("timezone", userZone.toString());

        } catch (DateTimeParseException e) {
            response.put("error", "Invalid date-time format. Use yyyy-MM-ddTHH:mm");
        }

        return response;
    }
}
