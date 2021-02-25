[1. 벼열ㄹ 스트림](#병렬-스트림) <br>


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

#### 스레드 관리

내부적으로 ForkJoinPool을 사용한다. <br>
ForkJoinPool은 기본적으로 프로세서 수가 반환하는 값에 상응하는 스레드를 갖는다.

(Runtime.getRuntime().availableProcessors()로 확인할 수 있음)

