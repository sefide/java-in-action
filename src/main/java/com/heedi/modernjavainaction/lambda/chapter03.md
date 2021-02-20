[1.람다의 특징](#특징)

[2.람다 표현 방법](#표현-방법)

[3.람다의 사용](#어디에서-사용할까?)

[4.람다 표현식 검사](#람다-표현식을-어떻게-검사할까?)

[5.람다 캡처링](#람다-캡처링-&-지역-변수-사용-제약)

[6.클로저](#클로저-(closure))

[7.메서드 참조](#메서드-참조)

[8.유용한 유틸리티 메서드](#유용한-함수형-인터페이스의-유틸리티-메서드)

---
## 람다 표현식

메서드로 전달할 수 있는 익명 함수를 단순화한 것 

파라미터 리스트, 바디, 반환 형식, 발생할 수 있는 예외 리스트



### 특징

- **익명** : 이름이 없는 메서드
- **함수** : 특정 클래스에 종속되지 않으며, 메서드와 같이 파라미터 리스트, 바디, 반환형식, 가능한 예외 리스트를 포함한다. 
- **전달** : 메서드 인수로 전달하거나 변수로 저장할 수 있다. 
- **간결성**



=> java8 이전에 불가능했던 일을 제공하는 것은 아니고, 익명 클래스와 같이 판에 박힌 코드를 구현할 필요가 없어졌다. 



### 표현 방법

(parameter) -> {statements; }

```
(Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
⎿ 람다 파라미터         ⎿ 화살표   ⎿ 람다 바디
```



### 어디에서 사용할까? 

✓ 람다 표현식은 변수에 할당하거나 함수형 인터페이스를 인수로 받는 메서드로 전달할 수 있으며, 함수형 인터페이스의 추상 메서드와 같은 시그니처를 갖는다. 



#### 함수형 인터페이스

: 하나의 추상 메서드를 지정하는 인터페이스. 디폴트 메서드가 많더라도 추상 메서드를 하나만 가지고 있다면 함수형 인터페이스다. (@FunctionalInterface)
ex) Comparator, Runnable, Callable, PrivilegedAction 등 

=> 함수형 인터페이스의 추상 메서드 구현을 직접 전달할 수 있으므로 전체 표현식을 함수형 인터페이스의 인스턴스로 취급... 

```java
Runnable anonymousRunnable = new Runnable() {
  public void run() {
    System.out.println("run");
  }
};

Runnable lambdaRunnable = () -> System.out.println("run");
```



- Predicate\<T>
  : 제네릭 형식의 T의 객체를 인수로 받아 불리언을 반환하는 추상 메서드 test를 정의한다. 
- Consumer\<T>
  : 제네릭 형식 T의 객체를 인수로 받아 void를 반환하는 추상 메서드 accept를 정의한다.  forEach와 같이 인수를 받아 어떤 동작을 수행하고 싶을 때 적절하다. 
- Function<T, R>
  : 제네릭 형식 T를 인수로 받아서 제네릭 형식의 R 객체를 반환하는 추상 메서드 apply를 정의한다. 

비용 소모가 있는 오토박싱 동작을 피할 수 있도록 특별한 버전의 함수형 인터페이스도 제공한다. 



#### 함수 디스크립터

:  '함수형 인터페이스의 추상 메서드 시그니처'는 '람다 표현식의 시그니처'를 가리킨다.
람다 표현식의 시그니처를 서술하는 메서드를 함수 디스크립터라 한다. 

ex) Predicate\<T>의 경우 함수 시트크립터는 "**T -> boolean**" 이다. 



#### 실행 어라운드 패턴(execute around pattern) 

: 자원 처리에 사용하는 순환 패턴으로 자원을 열고 처리하고 닫는 설정과 정리 과정으로 이루어진다. 



---

### 람다 표현식을 어떻게 검사할까? 

람다가 사용되는 콘텍스트를 이용해서 람다의 형식을 추론한다. 

```
람다가 사용된 콘텍스트를 확인한다. 
-> 대상 형식을 확인한다. 
-> 함수 디스크립터를 확인한다. 
-> 람다 시그니처를 추론하여 *람다 파라미터 형식*을 추론한다. 
-> 함수형 인터페이스를 추론하여 추상 메서드를 확인한다. 
-> 람다가 대상 형식에 맞는지 확인한다.
```

**대상 형식** : 람다가 전달되는 메서드 파라미터나 람다가 할당되는 변수 등, 기대되는 람다 표현식의 형식



### 람다 캡처링 & 지역 변수 사용 제약

람다 표현식은 파라미터가 넘겨진 변수가 아닌 외부에서 정의된 변수(자유 변수)를 사용할 수도 있다. 다만 제약이 있다. 한번만 할당할 수 있는 (final로 선언되어 있거나 그와 같이 사용하는) 지역 변수만을 캡쳐할 수 있다. 인스턴스 변수 캡처는 가능하다. (final 지역 변수 this를 캡처하는 것과 같음)

그 이유는 ? 
=> *인스턴스 변수는 힙에 저장, 지역변수는 스택에 저장. 스택에 저장된 지역변수를 접근하게 되면 스레드 종료 후, 변수 할당이 해제되었는데도 람다 스레드에서는 접근하려 할 수 있다. 따라서 자바에서는 원래 변수 복사본을 제공한다. 복사본의 값이 바뀌지 않아야 하므로 한번만 값을 할당할 수 있도록 제약을 둔다.* 


[코드로 확인하기](src/main/java/com/heedi/modernjavainaction/lambda/Lambda.java)


### 클로저 (Closure)

함수의 비지역 변수를 자유롭게 참조할 수 있는 함수의 인스턴스.
외부에 정의된 변수 값에 접근할 수 있으며 참조하는 값을 복사해두기 때문에 외부 함수가 종료되더라도 내부 함수에서 참조하는 외부 함수의 값은 유지된다. 

람다와의 차이점 : 클로저는 외부 변수를 참조, 람다는 자신이 받는 매개변수만을 참조

[코드로 확인하기](src/main/java/com/heedi/modernjavainaction/lambda/Closure.java)

참고) 
- https://futurecreator.github.io/2018/08/09/java-lambda-and-closure/
- https://coding-start.tistory.com/370
- https://ktko.tistory.com/entry/%EC%9E%90%EB%B0%94%EC%8A%A4%ED%81%AC%EB%A6%BD%ED%8A%B8-%ED%81%B4%EB%A1%9C%EC%A0%80Clouser-%EA%B0%9C%EB%85%90-%EC%9E%A1%EA%B8%B0 (js)



### 메서드 참조

특정 메서드만을 호출하는 람다의 축약형, 가독성을 높이는데 유용


1. 정적 메서드 참조
2. 다양한 형식의 인스턴스 메서드 참조
3. 기존 객체의 인스턴스 메서드 참조 

[코드로 확인하기](src/main/java/com/heedi/modernjavainaction/lambda/MethodReference.java)


---

### 유용한 함수형 인터페이스의 유틸리티 메서드


**Comparator**

- 역정렬 : reverse()

- 2차 정렬 기준 : thenComparing

  ```java
  default <U extends Comparable<? super U>> Comparator<T> thenComparing(Function<? super T, ? extends U> keyExtractor) {
    return thenComparing(comparing(keyExtractor));
  }
  ```

**Predicate**

- 결과 반전 : negate
- 결과에 and 조건 적용 : and
- 결과에 or 조건 적용 : or

**Function**

- andThen : 주어진 함수를 먼저 적용한 결과를 다른 함수의 입력으로 전달하는 함수 반환

  ```java
  default <V> Function<T, V> andThen(Function<? super R, ? extends V> after) {
    Objects.requireNonNull(after);
    return (T t) -> after.apply(apply(t));
  }
  ```

- compose : 인수로 주어진 함수를 먼저 실행한 다음에 그 결과를 외부 함수의 인수로 제공

  ```java
  default <V> Function<V, R> compose(Function<? super V, ? extends T> before) {
    Objects.requireNonNull(before);
    return (V v) -> apply(before.apply(v));
  }
  ```
  
[코드로 확인하기](src/main/java/com/heedi/modernjavainaction/lambda/UtilityDefaultMethod.java)

