# CompletableFuture와 리액티브 프로그래밍 컨셉의 기초

## 동시성과 병렬성

**동시성** : 단일 코어 머신에서 발생할 수 있는 프로그래밍 속성으로 실행이 서로 겹칠 수 있다.

**병렬성** : 병렬 실행을 하드웨어 수준에서 지원

![동시성과 병렬성](../../../../../resources/modernjavainaction/chapter15/concurrent_parallelism.png)


병렬성 < 동시성이 더 필요한 상황

=> 스레드 블록이 아닌 코어를 바쁘게 만들어야 하는 상황



병렬성 + 동시성 프로그래밍을 위해 자바는..

*first* .. Runnable, Thread

*java5* .. ExecutorService interface => Callable\<T>, Future\<T>, 제네릭

*java7* .. java.util.concurrent.RecursiveTask (포크/조인 구현 지원)

*java8* .. 스트림 및 람다 지원에 기반한 병렬 프로세싱, CompletableFuture와 같은 Future 조합 기능

*java9* .. 분산형 비동기 프로그래밍, java.util.concurrent.Flow (발행-구독 프로토콜)

