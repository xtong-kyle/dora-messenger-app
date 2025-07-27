package com.dora.app.util;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;

public class DateTimeUtil {

    private static final ChronoUnit[] unitsOrdered = {
            ChronoUnit.YEARS,
            ChronoUnit.MONTHS,
            ChronoUnit.WEEKS,
            ChronoUnit.DAYS,
            ChronoUnit.HOURS,
            ChronoUnit.MINUTES,
            ChronoUnit.SECONDS
    };

    public static Optional<Map.Entry<ChronoUnit, Long>> findLargestUnitDiff(long start, long end) {
        Instant i1 = Instant.ofEpochMilli(start);
        Instant i2 = Instant.ofEpochMilli(end);

        LocalDateTime dt1 = LocalDateTime.ofInstant(i1, ZoneOffset.UTC);
        LocalDateTime dt2 = LocalDateTime.ofInstant(i2, ZoneOffset.UTC);

        if (dt1.isAfter(dt2)) {
            LocalDateTime temp = dt1;
            dt1 = dt2;
            dt2 = temp;
        }

        for (ChronoUnit unit : unitsOrdered) {
            long diff = unit.between(dt1, dt2);
            if (diff != 0) {
                return Optional.of(new AbstractMap.SimpleEntry<>(unit, diff));
            }
        }

        return Optional.empty(); // identical timestamps
    }

    public static void main(String[] args) {
        System.out.println(findLargestUnitDiff(1751328000000L, 1754006399000L));
    }
}
