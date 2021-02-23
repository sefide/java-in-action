## 스트림 데이터 수집

최종 연산 중 collect에 대해 좀 더 자세히 살펴본다. 

collect 메서드에 Collector 파라미터를 전달하여 스트림의 요소를 어떤 식으로 도출할지 지정한다. 

함수형 프로그래밍의 장점은 원하는 것을 선언하여 사용하는 방식이었다. 
collect 메서드로 Collector 인터페이스를 구현할 때도 이와 같은 장점을 누릴 수 있다. 

예를 들면 각 요소를 리스트로 만들고 싶을 땐 'toList'로, 
각 키 버킷과 그에 대응하는 요소 리스트를 값으로 하는 맵을 만들고 싶을 때는 'groupingBy'라는 메서드를 사용하면 된다. 꽤 직관적이다 !

collect는 내부적으로 스트림의 요소에 리듀싱 연산을 수행한다. Collector 인터페이스의 메서드를 어떻게 구현하느냐에 따라 어떤 리듀싱 연산이 수행되는지 결정된다. 

<br>

### 리듀싱과 요약

- **counting**

```java
long count = menu.stream().collect(Collectors.counting());

아래와 같다
long count = menu.stream().count();
```

<br>

- **최댓값과 최솟값**

Collectors.maxBy, Collectors.minBy

maxBy나 minBy 모두 내부적으로 reducing 연산으로 이뤄진다는 것을 알 수 있다.

```java
 public static <T> Collector<T, ?, Optional<T>> maxBy(Comparator<? super T> comparator) {
 return reducing(BinaryOperator.maxBy(comparator));
}
```

[코드로 확인하기](CollectMinMax.java)

<br>

- **요약 연산**

합계 : Collectors.summingInt, summingLong, summingDouble

평균 : Collectors.averagingInt, averagingLong, averagingDouble

통계 : Collectors.summarizingInt, summarizingLong, summarizingDouble


[코드로 확인하기](CollectSumming.java)


<br>

- **문자열 연결**

Collectors.joining

[코드로 확인하기](CollectJoining.java)

<br>

- **범용 리듀싱 요약 연산**

Collectors.reducing

```java
public static <T> Collector<T, ?, T> reducing(T identity, BinaryOperator<T> op)

public static <T> Collector<T, ?, Optional<T>> reducing(BinaryOperator<T> op)
  
public static <T, U> Collector<T, ?, U> reducing(U identity, Function<? super T, ? extends U> mapper, BinaryOperator<U> op) 
```

[코드로 확인하기](CollectReducing.java)

<br>

#### collect vs reduce

**collect**  

=> 도출하려는 결과를 누적하는 컨테이너를 바꾸도록 설계됨 <br>
=> 병렬성 확보 가능



**reduce** 

=> 두 값을 하나로 도출하는 불변형 연산 <br>
=> 병렬 연산에서 사용 X <br>

<br>
---

### 그룹화

데이터 집합을 하나 이상의 특성으로 분류하여 그룹핑하는 연산

**분류 함수** : 스트림이 그룹화되는 기준을 제시하는 함수로 그룹화 함수가 반환하는 키가 맵의 키로, 그에 대응하는 스트림 요소가 값으로 반환된다. 

[코드로 확인하기](Grouping.java)



#### groupingBy 할 때 알아두면 좋은 Collectors 메서드

- **filtering** : 그루핑될 요소 중 predicate에 만족하는 요소를 downstream으로 변환하도록 Collector 반환

```java
public static <T, A, R> 
  Collector<T, ?, R> filtering(Predicate<? super T> predicate,
                               Collector<? super T, A, R> downstream) {
  BiConsumer<A, ? super T> downstreamAccumulator = downstream.accumulator();
  return new CollectorImpl<>(downstream.supplier(),
                             (r, t) -> {
                               if (predicate.test(t)) {
                                 downstreamAccumulator.accept(r, t);
                               }
                             },
                             downstream.combiner(), downstream.finisher(),
                             downstream.characteristics()
                            );
}
```

<br>

- **mapping** : 그루핑될 요소를 mapper에 적용하여 downstream으로 변환하도록 Collector 반환

```java
public static <T, U, A, R>
    Collector<T, ?, R> mapping(Function<? super T, ? extends U> mapper,
                               Collector<? super U, A, R> downstream) {
  BiConsumer<A, ? super U> downstreamAccumulator = downstream.accumulator();
  return new CollectorImpl<>(downstream.supplier(),
                             (r, t) -> downstreamAccumulator.accept(r, mapper.apply(t)),
                             downstream.combiner(), downstream.finisher(),
                             downstream.characteristics());
}
```

<br>

- **flatMapping**: 두 수준의 리스트를 한 수준으로 평면화하는 것으로 

```java
public static <T, U, A, R>
    Collector<T, ?, R> flatMapping(Function<? super T, 
                                   ? extends Stream<? extends U>> mapper,
                                   Collector<? super U, A, R> downstream) {
  BiConsumer<A, ? super U> downstreamAccumulator = downstream.accumulator();
  return new CollectorImpl<>(downstream.supplier(),
                            (r, t) -> {
                              try (Stream<? extends U> result = mapper.apply(t)) {
                                if (result != null) result.sequential().forEach(u -> downstreamAccumulator.accept(r, u));
                              }
                            },
                            downstream.combiner(), downstream.finisher(),
                            downstream.characteristics());
    }
```
<br>

---

### 분할

분할 함수를 분류 함수로 사용하는 특수한 그룹화 기능

분할 함수 : Boolean을 반환하는 Predicate .. 

[코드로 확인하기](Partitioning.java)

<br>