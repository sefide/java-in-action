[1. 리듀싱과 요약](#리듀싱과-요약) <br>
[2. 그룹화](#그룹화) <br>
[3. 분할](#분할) <br>
[4. Collector 인터페이스](#Collector-인터페이스) <br>

<br>

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



---

- Collectors 클래스 (java.util.stream)<br>=> 내부에 Collector 인터페이스를 구현한 CollectorImpl 구현체를 지니고 있음 

- Collector 인터페이스 (java.util.stream)



### Collector 인터페이스

리듀싱 연산을 어떻게 구현할 지 제공하는 메서드 집합

```
public interface Collector<T, A, R> {
	Supplier<A> supplier();
	BiConsumer<A, T> accumulator();
	Function<A, R> finisher();
	BinaryOperation<A> combiner();
	Set<Charcteristics> characteristics();
}
```

**T** : 수집될 스트림 항목의 제네릭 형식

**A** : 누적자, 수집 과정에서 중간 결과를 누적하는 객체 형식

**R** : 수집 연산 결과 객체의 형식

상위 4개의 메서드들은 collect 메서드에서 실행하는 함수이며, characteristics 메서드는 collect 메서드가 어떤 최적화를 이용해서 리듀싱 연산을 수행할 것인지 결정하도록 돕는 힌트 특성 집합을 제공한다. 

<br>

- **supplier** <br>
  => 빈 결과로 이루어진 Supplier를 반환 <br>
- **accumulator**  <br>=> 리듀싱 연산을 수행하는 함수를 반환 <br>
  => 함수의 반환값은 void로 누적자 내부 상태가 바뀐다. <br>
- **finisher**  <br>
  => 스트림 탐색을 끝내고 누적자 객체를 최종 결과로 변환하면서 누적 과정을 끝낼 때 호출할 함수 반환 <br>
  => 누적자 객체가 이미 최종 결과인 경우, 항등 함수를 반환한다. 
- **combiner** <br>
  => 리듀싱 연산에서 사용할 함수를 반환 <br>
  => 반환되는 함수는 스트림의 병렬 처리로 분할된 서브파트 스트림을, 누적자가 이 결과를 어떻게 처리할지 정의 <br>
- **characteristics** <br>
  => 컬렉터의 연산을 정의하는 Characteristics 형식의 불변 집합을 반환 <br>
  => Characteristics에 따라 병렬로 리듀싱할지, 어떤 최적화를 할지 힌트 제공 <br>

[^Characteristics]: Collector의 속성을 나타내는 열거형

```
UNORDERED : 리듀싱 결과는 스트림 요소의 방문 순서나 누적순서에 영향을 받지 않는다.

CONCURRENT : 다중 스레드에서 accumulator 함수를 동시에 호출할 수 있으며 이 컬렉터는 스트림의 병렬 리듀싱을 수행할 수 있다. 컬렉터의 플래그에 UNORDERED를 함께 설정하지 않았다면, 데이터 소스가 정렬되어 있지 않은 상황에서 병렬 리듀싱 수행이 가능

IDENTITY_FINISH : finisher 메서드가 반환하는 함수는 단순히 identity를 적용하므로 생략할 수 있다.
```



테스트로 생성한 ToListCollector 클래스는 IDENTITY_FINISH다. 더불어 리스트의 순서가 상관이 없으니 UNORDERED이기도 하며 CONCURRENT이기도... 

[ToListCollector 구현 - 코드로 확인하기](ToListCollector.java)

[ToListCollector 사용 - 코드로 확인하기](ListCollector.java)



#### 커스텀 컬렉터 구현해보기

[커스텀 컬렉터로 소수 분할하기 - 코드로 확인하기](PrimeNumbersCollector.java)

새로운 커스텀 컬렉터를 구현하면 collect 메서드에 파라미터를 전달하는 것보다 가독성과 재사용성을 높일 수 있다. 

