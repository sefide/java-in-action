## 스트림 데이터 수집

최종 연산 중 collect에 대해 좀 더 자세히 살펴본다. 

collect 메서드에 Collector 파라미터를 전달하여 스트림의 요소를 어떤 식으로 도출할지 지정한다. 

함수형 프로그래밍의 장점은 원하는 것을 선언하여 사용하는 방식이었다. 
collect 메서드로 Collector 인터페이스를 구현할 때도 이와 같은 장점을 누릴 수 있다. 

예를 들면 각 요소를 리스트로 만들고 싶을 땐 'toList'로, 
각 키 버킷과 그에 대응하는 요소 리스트를 값으로 하는 맵을 만들고 싶을 때는 'groupingBy'라는 메서드를 사용하면 된다. 꽤 직관적이다 !

collect는 내부적으로 스트림의 요소에 리듀싱 연산을 수행한다. Collector 인터페이스의 메서드를 어떻게 구현하느냐에 따라 어떤 리듀싱 연산이 수행되는지 결정된다. 



### 리듀싱과 요약

- counting

```
long count = menu.stream().collect(Collectors.counting());

아래와 같다
long count = menu.stream().count();
```



- 최댓값과 최솟값

[코드로 확인하기](CollectReducing.java)



- 요약 연산



