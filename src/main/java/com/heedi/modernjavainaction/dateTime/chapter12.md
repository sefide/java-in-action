
# 새로운 날짜와 시간 API 

<br>

_java.util.Date (java 1.0)_

1900년을 기준으로 하는 offset, 0에서 시작하는 월 index, 날짜가 아닌 밀리초 단위의 표현

<br>

_java.util.Calendar (java 1.1)_

0에서 시작하는 월 index, dateFormat 메서드 미지원, Date 클래스와의 혼동

<br>

_java.time (java 8)_

Date와 Calendar의 대안으로 추가 <br>
LocalDate, LocalDateTime과 같은 불변 클래스 추가


[Date, Calendar 객체의 문제점 참고](https://madplay.github.io/post/reasons-why-javas-date-and-calendar-was-bad)


## java.time 패키지

**날짜와 시간 관련 API 제공** <br>
LocalDate, LocalTime, LocalDateTime, Instant, Duration, Period 등의 클래스 포함

