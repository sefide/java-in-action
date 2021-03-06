

# null 대신 Optional 클래스

<br>

### null 때문에 발생하는 문제

- NullPointerException의 근원
- null 체크가 필요해지면서 복잡함 한스푼
- 무의미한 null
- 자바에서 미처 숨기지 못한 포인터 개념
- null의 의미 (모든 참조 형식에 null을 할당할 수 있어 null이 전파되었을 경우, 초기 null의 의미를 잃을 수 있다.)



***다른 언어들은 ?*** 

그루비 - 안전 내비게이션 연산자 (safe navigation operator) (?.)

하스켈 - Maybe

스칼라 - Option[T]



### Optional 클래스

*since java8* 

*java.util.Optiona\<T>*

=> 선택형 값을 캡슐화하는 클래스

=> 값이 null 일 수 있다는 것을 알려주는 역할을 하여 값이 없는 경우에 대한 대응을 필요화하는 효과가 있다.

=> 모든 null 참조를 Optional로 대치하는건 옳지 않다. 

=> 필드 형식으로 사용할 것을 가정하지 않아 Serializable 인터페이스를 구현하지 않는다. 



=> 값이 있는 경우, Optional 클래스로 **값을 감싼** **Optional** 클래스 

=> 값이 없는 경우, Optinal.empty로 **빈** **Optional** 반환



- Optional.empty() : 빈 Optional
- Optional.of : null이 아닌 값을 담은 Optional
- Optional.ofNullable : null 일 수 있는 값을 담은 Optional



- map : 값 변환, 값이 없는 경우 nothing

- flatMap : 중첩 Optional 구조에서 내부 Optional의 값을 꺼내어 일차원 Optional로 변환

  ```java
  public <U> Optional<U> flatMap(Function<? super T, ? extends Optional<? extends U>> mapper) {
    Objects.requireNonNull(mapper);
    if (!isPresent()) {
      return empty();
    } else {
      @SuppressWarnings("unchecked")
      Optional<U> r = (Optional<U>) mapper.apply(value);
      return Objects.requireNonNull(r);
    }
  }
  ```



- stream (*since java9*) : Optional이 비어있는지 아닌지에 따라 0개 이상의 항목을 포함하는 스트림으로 변환
- get : 값을 읽는다. 값이 없으면 NoSuchElementException 발생 
- orElse(T other) : 값이 없는 경우 기본값 제공
- orElseGet(Supplier<? extends T> other) : 값이 없는 경우에 Supplier 실행 
- orElseThrow(Supplier<? extends X> exceptionSupplier) : 값이 없는 경우에 Supplier에서 생성한 예외 발생
- ifPresent(Consumer<? super T> consumer) : 값이 존재하면 consumer 실행하고 없으면 아무 일도 일어나지 않음  
- ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) : 값이 존재하면 action 실행하고, 없으면 아무 일도 일어나지 않음



#### 기본형 특화 Optional 

=> OptionalInt, OptionalLong, OptionalDouble

=> 요소가 최대 한 개이므로 스트림처럼 성능 향상을 기대하기는 어렵다. 

=> map, filter, flatMap과 같은 메서드를 지원하지 않는다. 