[1. 필터링](#필터링) <br>
[2. 스트림 슬라이싱](#스트림-슬라이싱) <br>
[3. 매핑](#매핑) <br>
[4. 검색과 매칭](#검색과-매칭) <br>
[5. 리듀싱](#리듀싱) <br>
[6. 스트림 연산의 상태](#스트림-연산의-상태) <br>
[7. 필터링](#실전-연습) <br>
[8. 숫자형 스트림](#숫자형-스트림) <br>
[9. 그 외](#그-외) <br>

<br>


## 스트림 활용 

### 필터링

- **filter** : 조건에 맞는 스트림 반환

```java
Stream<T> filter(Predicate<? super T> predicate);
```

- **distinct**: 고유 요소 스트림 반환

[코드로 확인하기](Filtering.java)

<br>

---

### 스트림 슬라이싱

- **takeWhile** : Predicate에 적용할 요소가 정렬이 되어 있는 경우, Predicate를 적용한 요소를 슬라이스 반환한다. filter는 모든 요소를 살펴보지만 takeWhile은 이는 조건에 적합한 요소까지만 확인 후 종료한다. 이는 요소가 정렬이 되어 있어 가능하다.

```java
default Stream<T> takeWhile(Predicate<? super T> predicate) {
  Objects.requireNonNull(predicate);
  // Reuses the unordered spliterator, which, when encounter is present,
  // is safe to use as long as it configured not to split
  return StreamSupport.stream(
    new WhileOps.UnorderedWhileSpliterator.OfRef.Taking<>(spliterator(), true, predicate)
    ,isParallel()).onClose(this::close);    
}
```
<br>

- **dropWhile** : 조건에 부합하지 않는 스트림을 반환한다. takeWhile과 정반대로 Predicate가 거짓이 되는 지점까지 발견된 요소를 버리는 방식이다.

```java
default Stream<T> dropWhile(Predicate<? super T> predicate) {
  Objects.requireNonNull(predicate);
  // Reuses the unordered spliterator, which, when encounter is present,
  // is safe to use as long as it configured not to split
	return StreamSupport.stream(
    new WhileOps.UnorderedWhileSpliterator.OfRef.Dropping<>(spliterator(), true, predicate)
    ,isParallel()).onClose(this::close);   
}
```
<br>

- **limit** : 최대 n개의 요소를 반환한다. (쇼트서킷 적용)
<br>

- **skip** : 처음 n개의 요소를 제외한 스트림을 반환한다. 요소의 개수가 n개 이하라면 빈 스트림을 반환.


<br>

---

### 매핑

특정 객체에서 특정 데이터를 선택 (변환에 가까운 매핑)

- **map** : 인수로 받은 함수가 요소에 적용되어 새로운 요소를 매핑하여 스트림으로 반환.

#### 스트림 평면화

- **flatMap** : 스트림의 각 값을 다른 스트림으로 만든 다음에 모든 스트림을 하나의 스트림으로 연결한다. (**스트림 평면화**) 

```java
<R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);
```

[코드로 확인하기](Mapping.java)

<br>

---

### 검색과 매칭

특정 속성이 데이터 집합에 있는지 여부 검사

- **anyMatch** : 스트림에서 Predicate를 만족하는 요소가 적어도 하나 이상 있는지 여부 반환한다.
- **allMatch** : 스트림의 모든 요소가 Predicate에 만족하는지 여부 반환한다.
- **noneMatch** : 주어진 Predicate와 일치하는 요소가 없는지 여부 반환한다.
- **findAny** : 스트림에서 임의의 요소를 Optional로 감싸서 반환한다.
- **findFirst** : 스트림에서 첫번째 요소를 Optional로 감싸서 반환한다.

=> 위 연산들은 *쇼트서킷*이 적용된다. 스트림의 모든 요소들을 처리하지 않고도 결과를 반환할 수 있는 것이다. 

<br>

[^Optional<T>]: 값의 존재나 부재 여부를 표현하는 컨테이너 클래스 (chapter10 참고)

<br>

---

### 리듀싱

모든 스트림 요소를 처리해서 값으로 도출하는 연산 (fold;폴드)

- **reduce**

```java
T reduce(T identity, BinaryOperator<T> accumulator);

// 스트림에 아무 요소도 없는 경우를 대비해 Optional 반환
Optional<T> reduce(BinaryOperator<T> accumulator);

<U> U reduce(U identity,
             BiFunction<U, ? super T, U> accumulator,
             BinaryOperator<U> combiner);
```

identity : 초기값

accumulator : 추상화된 반복 패턴 (연산)

combiner : 병렬처리된 결과를 합치는데 사용할 연산

[코드로 확인하기](Reducing.java)

<br>

---

### 스트림 연산의 상태

**상태 없음**
(map, filter) : 요소를 받아 결과를 출력 스트림으로 내보내며 내부 상태를 갖지 않는다. (사용자가 제공한 람다나 메서드 참조가 내부적인 가변 상태를 갖지 않는다는 가정)

**상태 있음** 
(reduce, sum, max) : 연산 결과를 누적할 내부 상태가 필요한 경우, 한정된 크기의 내부 상태를 보유
(sorted, distinct) : 모든 요소가 버퍼에 추가되어 있음, 이 때는 저장소 크기가 정해져있지 않지만 무한으로 커지면 문제가 발생할 수 있다.

<br>

---

### 실전 연습
[코드로 확인하기](ActualPractice.java)

<br>

---

### 숫자형 스트림

스트림 API의 숫자 스트림을 효율적으로 처리할 수 있도록 제공해주는 기본형 특화 스트림 (primitive stream specialization)

#### 기본형 특화 스트림

박싱, 언박싱 과정에서 일어나는 효율성을 높이기 위해 ... IntStream, DoubleStream, LongStream

- 숫자 스트림으로 매핑 : mapToInt, mapToDouble, mapToLong
- 객체 스트림으로 복원 : boxed()

```java
Stream<Integer> boxed();
Stream<Double> boxed();
Stream<Long> boxed();
```

- OptionalInt, OptionalDouble, OptionalLong

<br>


#### 숫자 범위

- **range**

  ```java
  public static IntStream range(int startInclusive, int endExclusive) {
    if (startInclusive >= endExclusive) {
      return empty();
    } else {
      return StreamSupport.intStream(
        new Streams.RangeIntSpliterator(startInclusive, endExclusive, false), false);
    }
  }
  ```

  

- **rangeClosed**

  ```java
  public static IntStream rangeClosed(int startInclusive, int endInclusive) {
    if (startInclusive > endInclusive) {
      return empty();
    } else {
      return StreamSupport.intStream(
        new Streams.RangeIntSpliterator(startInclusive, endInclusive, true), false);
    }
  }
  ```

[코드로 확인하기](NumberRange.java)

<br>

---

### 그 외


**피보나치 수열**

[코드로 확인하기](Fibonacci.java)


**피타고라스의 정리**

[코드로 확인하기](Pythagoras.java)


**무한 스트림**

[코드로 확인하기](InfiniteStream.java)


**파일 스트림** 

[코드로 확인하기](FileStream.java)
