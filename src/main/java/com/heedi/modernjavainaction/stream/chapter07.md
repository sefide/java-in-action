[1. 병렬 스트림](#병렬-스트림) <br>
[2. 스레드 관리](#스레드-관리) <br>
[3. 성능 측정](#성능-측정) <br>
[4. 병렬 스트림 효과적으로 사용하기](#병렬-스트림-효과적으로-사용하기) <br>

## 병렬 데이터 처리와 성능

포크/조인 프레임워크가 추가되기 전, 자바 7 전까지는 데이터 컬렉션을 병렬로 처리하기 어려웠다. 
데이터 분할부터 스레드 할당, 경쟁 상태를 고려한 동기화와 합병까지.. 고려해야 할 부분이 많았는데,...

자바 7에서 포크/조인 프레임워크 기능이 제공되고 이후 스트림의 내부 병렬 스트림까지 제공되면서 컬렉션 데이터에 대해 얼마나 쉽게 병렬 처리가 가능해졌는지 확인할 수 있다. 

<br>



### 병렬 스트림

모든 멀티코어 프로세서가 각각의 스레드에서 처리할 수 있도록 스트림 요소를 여러 청크로 분할한 스트림



#### 병렬 스트림 생성

- **Collection의 parallelStream** : 컬렉션에서 병렬 스트림 생성시, 사용

- **Stream의 parallel** : 순차 스트림을 병렬 스트림으로 바꿔줌 (실제로는 병렬 스트림 여부 플래그를 변환)

```java
// AbstractPipeline 
@Override
@SuppressWarnings("unchecked")
public final S parallel() {
   sourceStage.parallel = true;
   return (S) this;
}
```

- **Stream의 sequential** : 병렬 스트림을 순차 스트림으로 바꿔줌 

```java
 @Override
 @SuppressWarnings("unchecked")
 public final S sequential() {
    sourceStage.parallel = false;
    return (S) this;
 }
```

parallel과 sequential 두 메서드를 같이 사용할 경우, 최종적으로 호출된 메서드가 전체 파이프라인에 영향을 미친다. 

<br>

### 스레드 관리

내부적으로 ForkJoinPool을 사용한다. <br>
ForkJoinPool은 기본적으로 프로세서 수가 반환하는 값에 상응하는 스레드를 갖는다.

(Runtime.getRuntime().availableProcessors()로 확인할 수 있음)



### 성능 측정

JMH를 이용한 성능 측정

코드로 확인하기 



@BenchmarkMode(Mode.AverageTime) <br>
=> 벤치마크 대상 메서드를 실행하는데 걸린 평균 시간 측정

@OutputTimeUnit(TimeUnit.MILLISECONS) <br>
=> 벤치마크 결과 밀리초 단위로 표시

@Fork(2, jvmArgs={"-Xms4G", "-Xmx4G"}) <br>
=> 4GB인 힙 공간을 제공한 환경에서 두 번 벤치마크를 수행해 결과 신뢰성 확보

```
$gradle jmh
```



**성능 측정 관련 참고 글**

- https://hyesun03.github.io/2019/08/27/how-to-benchmark-java/
- https://javabom.tistory.com/75



***적절한 자료구조를 선택할 것***

=> 특화되지 않은 스트림을 처리할 때는 오토박싱, 언박싱과 같은 작업으로 인해 오버헤드가 발생할 수 있다. 

=> 병렬 처리라도 반복 형식과 순차 처리보다 성능이 떨어질 수 있다.



***병렬화은 공짜가 아니다***

=> 병렬 이용 

 	1. 스트림을 재귀적으로 분할
 	2. 서브스트림을 서로 다른 스레드의 리듀싱 연산으로 할당
 	3. 결과를 합산

=> 코어간 데이터 전송은 비싸므로, 데이터 전송보다 오래 걸리는 작업을 병렬로 수행하자



***공유된 가변 상태는 병렬 계산에서 사용하지 않는다.***

코드로 확인하기



### 병렬 스트림 효과적으로 사용하기

1. 확신이 없다면 적절한 벤치마크를 이용해 직접 성능을 측정하자

2. 박싱은 오버헤드가 클 수 있으니 되도록 기본형 특화 스트림(IntStream, LongStream, DoubleStream)을 사용하자

3. 요소의 순서에 의존하는 연산(limit, findFirst)은 병렬 스트림에서 비싼 비용을 치러야 할 수도 있다. (findAny가 findFirtst보다 성능이 좋다.)

4. 스트림에서 수행하는 전체 파이프라인 연산 비용을 고려하라 (처리해야 할 요소 수 * 하나의 요소를 처리하는데 드는 비용) 하나의 요소를 처리하는데 드는 비용이 커질수록 병렬이 유리할 수 있다.

5. 소량의 데이터에서는 사용하지 말 것..

6. 스트림의 분해에 적절한 자료구조가 있다. 참고해서 확인하라

   | 소스            | 분해성 |
   | --------------- | ------ |
   | ArrayList       | 훌륭함 |
   | LinkedList      | 나쁨   |
   | IntStream.range | 훌륭함 |
   | Stream.iterate  | 나쁨   |
   | HashSet         | 좋음   |
   | TreeSet         | 좋음   |

7. 스트림의 길이를 측정할 수 없다면 효과적인 병렬 처리가 어려울 수 있다. 따라서 중간연산이 스트림의 특성을 어떻게 바꾸는지 확인해라 (SIZED 스트림은 효과적, 필터 연산은 길이 측정이 어려우니 비효과적)

8. 최종 연산의 병합 과정이 비싸면 병렬이 효과적으로 상쇄해줄 수 있다. 