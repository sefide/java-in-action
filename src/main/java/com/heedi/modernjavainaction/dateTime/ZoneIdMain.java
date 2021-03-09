package com.heedi.modernjavainaction.dateTime;

import java.time.*;
import java.util.TimeZone;

public class ZoneIdMain {

    public static void main(String[] args) {
        // 특정 지역 ZoneId 찾기
        ZoneId zoneId = ZoneId.of("Europe/London");

        // TimeZone -> ZoneId 변환
        ZoneId toZoneId = TimeZone.getDefault().toZoneId();

        // 현 서버의 ZoneId 정보
        ZoneId currentZoneId = ZoneId.systemDefault();

        applyZoneId();


        // 존재하지 않는 ZoneId를 생성하려는 경우
        // java.time.zone.ZoneRulesException: Unknown time-zone ID: Europe/France
        ZoneId franceZoneId = ZoneId.of("Europe/France");
    }

    private static void applyZoneId() {
        ZoneId zoneId = ZoneId.of("Europe/Paris");

        LocalDate localDate = LocalDate.of(2021, 1, 12);
        ZonedDateTime zonedDateTime1 = localDate.atStartOfDay(zoneId);
        System.out.println(zonedDateTime1);

        LocalDateTime localDateTime = LocalDateTime.of(2222, Month.APRIL, 12, 13, 44);
        ZonedDateTime zonedDateTime2 = localDateTime.atZone(zoneId);
        System.out.println(zonedDateTime2);

        Instant instant = Instant.now();
        ZonedDateTime zonedDateTime3 = instant.atZone(zoneId);
        System.out.println(zonedDateTime3);
    }

}
