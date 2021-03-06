# CompletableFuture와 리액티브 프로그래밍 컨셉의 기초

## 동시성과 병렬성

**동시성** : 단일 코어 머신에서 발생할 수 있는 프로그래밍 속성으로 실행이 서로 겹칠 수 있다.

**병렬성** : 병렬 실행을 하드웨어 수준에서 지원

![동시성과 병렬성](../../../../../resources/modernjavainaction/chapter15/concurrent_parallelism.png)


**병렬성 < 동시성이 더 필요한 상황** <br>
=> 스레드 블록이 아닌 코어를 바쁘게 만들어야 하는 상황

<br>

*병렬성 + 동시성 프로그래밍을 위해 자바는..*

*first* .. Runnable, Thread

*java5* .. ExecutorService interface => Callable\<T>, Future\<T>, 제네릭

*java7* .. java.util.concurrent.RecursiveTask (포크/조인 구현 지원)

*java8* .. 스트림 및 람다 지원에 기반한 병렬 프로세싱, CompletableFuture와 같은 Future 조합 기능

*java9* .. 분산형 비동기 프로그래밍, java.util.concurrent.Flow (발행-구독 프로토콜)



<br>

---

### Executor와 스레드 풀

*since java5*



**자바 스레드의 단점** 

=> 운영체제 스레드에 직접 접근  <br>
=> 비싼 비용과 스레드 수의 한계

운영체제 스레드, 자바 스레드 > 하드웨어 스레드



**ExecutorService** 

=> task 제출, 나중에 결과 수집하는 인터페이스 제공 <br>
=> 하드웨어에 맞는 수의 task를 유지하면서 동시에 수천개의 task를 스레드풀에 아무 오버헤드 없이 제출 


#### 스레드 풀

- 스레드를 직접 사용하는 것보다 스레드 풀을 이용하는 것이 더 낫다.

- k개의 스레드를 가진 스레드 풀은 오직 k 만큼의 스레드를 동시에 실행할 수 있다. 

- task가 k개를 초과하여 제출된다면 스레드를 할당하고 남은 나머지 task는 큐에 저장이 되어 먼저 할당된 task가 종료될때까지 기다린다. 

- 락이 걸릴 수 있는 task는 스레드 풀에 제출하지 않는게 좋다. 데드락에 걸릴 수 있기 때문이다. 

- 프로그램 종료 전, 스레드 풀을 종료시키는 습관이 중요하다. 

#### 스레드 - 중첩되지 않은 메서드 호출 
- 엄격한 포크/조인 : 스레드 생성과 join()이 한 쌍처럼 중첩된 메서드 호출내에 추가 되는 방식 (병렬 스트림 및 포크/조인 프레임워크)
- 비동기 메서드 : 메서드 호출자에 기능을 제공하도록 메서드가 반환된 후에도 만들어진 태스크 실행이 계속되는 메서드 

#### setDaemon() : 데몬(daemon) / 비데몬 구분
- 데몬 스레드 = 애플리케이션이 종료될 때 강제 종료 시킨다. 
    - 데이터 일관성 파괴 위험이 있다.
    - 따라서 데이터 일관성을 파괴하지 않는 동작을 수행할 때 유용하다.
    
- 비데몬 스레드 = 애플리케션을 종료하지 못하고 모든 스레드가 끝날때까지 기다린다. 
    - main() 메서드는 비데몬 스레드가 종료될 때까지 프로그램을 종료하지 않고 기다린다. 
    - 무한 대기 상태를 발생시킬 수 있다. 

<br>

### 동기 API / 비동기 API

- 동기 API
: 메서드를 호출한 다음에 메서드가 계산을 완료할 때까지 기다렸다가 메서드가 반환되면 호출자는 반환된 값으로 계속 다른 동작을 수행한다. 
호출자가 피호출자의 동작 완료를 기다리는 상황 (블록 호출)

- 비동기 API 
: 메서드가 즉시 반환되며 끝내지 못한 나머지 작업을 호출자 스레드와 동기적으로 실행될 수 있도록 다른 스레드에 할당한다. (비블록 호출) <br>
다른 스레드에서 수행된 비동기 호출의 결과 값은 호출자가 기다린다는 메서드를 전달하거나, 콜백 메서드를 이용해 전달된다. 

<br> 

작업 수행 시간이 오래 걸리는 작업 f, g 메서드가 반환하는 결과값 합계를 구하고자 할 때 <br> 
1. Future 조합, CompletableFuture 이용 
2. 발행-구독 프로그램에 기반한 java.util.concurrent.Flow 인터페이스 이용 

### 비동기 API에서 예외 처리 

비동기 API에서 호출된 메서드는 별도의 스레드에서 수행되기 때문에 예외가 발생한다면 호출자의 실행 범위와는 관계가 없어진다. <br>
CompletableFuture나 리액리브에서는 예외를 처리할 수 있도록 별도의 메소드를 제공한다. <br>

### 박스와 채널 다이어그램

동시성 모델을 설계하고 구조화했을 때 유용. <br>
박스 (프로그램 콤비네이터)
 
