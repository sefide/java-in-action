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



- 스레드를 직접 사용하는 것보다 스레드 풀을 이용하는 것이 더 낫다.

- k개의 스레드를 가진 스레드 풀은 오직 k 만큼의 스레드를 동시에 실행할 수 있다. 

- task가 k개를 초과하여 제출된다면 스레드를 할당하고 남은 나머지 task는 큐에 저장이 되어 먼저 할당된 task가 종료될때까지 기다린다. 

- 락이 걸릴 수 있는 task는 스레드 풀에 제출하지 않는게 좋다. 데드락에 걸릴 수 있기 때문이다. 



