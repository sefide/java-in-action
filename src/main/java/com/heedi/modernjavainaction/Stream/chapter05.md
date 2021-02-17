## 스트림 활용 

### 필터링

- filter : 조건에 맞는 스트림 반환

```java
Stream<T> filter(Predicate<? super T> predicate);
```

- distinct: 고유 요소 스트림 반환

  

### 스트림 슬라이싱

- takeWhile : Predicate에 적용할 요소가 정렬이 되어 있는 경우, Predicate를 적용한 요소를 슬라이스 반환한다. filter는 모든 요소를 살펴보지만 takeWhile은 이는 조건에 적합한 요소까지만 확인 후 종료한다. 이는 요소가 정렬이 되어 있어 가능하다.

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

- dropWhile : 조건에 부합하지 않는 스트림을 반환한다. takeWhile과 정반대로 Predicate가 거짓이 되는 지점까지 발견된 요소를 버리는 방식이다.

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

- limit : 최대 n개의 요소를 반환한다. 
- skip : 처음 n개의 요소를 제외한 스트림을 반환한다. 요소의 개수가 n개 이하라면 빈 스트림을 반환.