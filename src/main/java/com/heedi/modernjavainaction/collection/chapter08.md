[1. 컬렉션 팩토리](#컬렉션-팩토리) <br>	[2. 리스트와 집합처리](#리스트와-집합-처리) <br>	[3. 맵 처리](#맵-처리) <br>



# 컬렉션 API 개선

자바 8,9에 추가된 내용들을 다룬다. 

<br>

## 컬렉션 팩토리

[코드로 확인하기](CollectionFactory.java)
<br>

- **Arrays.asList**<br>

=> 고정된 크기의 리스트를 생성하기 때문에 요소를 추가하려 하면 UnsupportedOperationException 발생 <br>
=> 값 갱신은 가능 <br>

```java
// Arrays에서 AbstractList를 상속하는 ArrayList를 구현하고 있다. 
// 이때 set 메서드는 오버라이딩하고 있지만 add는 따로 하고 있지 않다. 
@SafeVarargs
@SuppressWarnings("varargs")
public static <T> List<T> asList(T... a) {
  return new ArrayList<>(a);
}
```

AbstractList.java 발췌
```java
public void add(int index, E element) {
  throw new UnsupportedOperationException();
}
```


<br> 

*since java9*

- **List.of(E e1, E e2 ..)** <br>
  => Arrays.asList처럼 고정된 크기의 리스트 생성, Arrays.asList와 달리 갱신 불가능 <br>

  - 가변 인수가 10개 미만인 경우

  ```java
  static <E> List<E> of(E e1) {
    return new ImmutableCollections.List12<>(e1);
  }
  ```

  

  - 가변 인수가 10개 이상인 경우 - 추가 배열을 할당해서 리스트로 감싸는 과정에서 배열을 할당/초기화하며 카비지 컬렉션을 하는 비용이 추가된다.

  ```java
  @SafeVarargs
  @SuppressWarnings("varargs")
  static <E> List<E> of(E... elements) {
    switch (elements.length) { // implicit null check of elements
      case 0:
        return ImmutableCollections.emptyList();
      case 1:
        return new ImmutableCollections.List12<>(elements[0]);
      case 2:
        return new ImmutableCollections.List12<>(elements[0], elements[1]);
      default:
        return new ImmutableCollections.ListN<>(elements);
    }
  }
  ```

  

- **Set.of(E e1, E e2 ..)**

  => 중복 요소가 있는 경우, IllegalArgumentException 발생 

  

- **Map.of(K k1, V v1, K k2, V v2 ..)**

  => 10개 이하의 키를 가진 맵 생성 시 유용

  

- **Map.ofEntries(Entry<> ... entries)**

  => Map.Entry<K, V> 객체를 인수로 받음 <br>
  => 10개 이상의 키를 가진 맵 생성 시 유용 <br>

  ```java
  @SafeVarargs
  @SuppressWarnings("varargs")
  static <K, V> Map<K, V> ofEntries(Entry<? extends K, ? extends V>... entries) {
    if (entries.length == 0) { // implicit null check of entries array
      return ImmutableCollections.emptyMap();
    } else if (entries.length == 1) {
      // implicit null check of the array slot
      return new ImmutableCollections.Map1<>(entries[0].getKey(),
                                             entries[0].getValue());
    } else {
      Object[] kva = new Object[entries.length << 1];
      int a = 0;
      for (Entry<? extends K, ? extends V> entry : entries) {
        // implicit null checks of each array slot
        kva[a++] = entry.getKey();
        kva[a++] = entry.getValue();
      }
      return new ImmutableCollections.MapN<>(kva);
    }
  }
  ```




<br>



## 리스트와 집합 처리

새로운 컬렉션을 생성하는 것이 아니라 기존의 컬렉션을 변경하는 것은 매우 복잡한 코드를 유발한다. 이를 간단히 해결해주기 위해 아래 메서드들이 생겨났다. 


*in List, Set Interface .. since java8*

- **removeIf** <br>
  => 프레디케이트를 만족하는 요소를 제거한다.

  ```java
  default boolean removeIf(Predicate<? super E> filter) {
    Objects.requireNonNull(filter);
    boolean removed = false;
    final Iterator<E> each = iterator();
    while (each.hasNext()) {
      if (filter.test(each.next())) {
        each.remove();
        removed = true;
      }
    }
    return removed;
  }
  ```

  [코드로 확인하기](RemoveIfMethod.java)


- **replaceAll** <br>
  => 리스트의 각 요소를 새로운 요소로 바꾼다.

  ```java
  default void replaceAll(UnaryOperator<E> operator) {
    Objects.requireNonNull(operator);
    final ListIterator<E> li = this.listIterator();
    while (li.hasNext()) {
      li.set(operator.apply(li.next()));
    }
  }
  ```

  [코드로 확인하기](ReplaceAllMethod.java)


- **sort** 
  => List 인터페이스에서 제공하는 기능으로 리스트 정렬을 수행한다.  




<br>



## 맵 처리

[코드로 확인하기](MapFactory.java)


*in Map Interface .. since java8*

- **forEach** <br>
  => 맵의 키와 값을 반복

  ```java
  default void forEach(BiConsumer<? super K, ? super V> action) { ... }
  K : key 
  V : value
  ```

  

- **Entry.comparingByValue, Entry.comparingByKey** <br>
  => 맵의 항목을 값 또는 키로 정렬   <br>
  => java8 이전의 맵에서는 항목을 키로 생성한 해시코드로 접근할 수 있는 버켓에 저장했다. 하지만 이 방법은 키가 많아질수록 동일한 해시코드를 반환하는 상황이 발생하여 LinkedList(O(N))로 버킷을 반환하며 성능이 저하되었다. java8 이후로는 버킷이 너무 커질 경우, 이를 정렬된 트리를 이용하여 동적으로 치환해 성능을 O(logN)으로 개선하였다. 하지만 정렬된 트리를 생성하려면 키가 String이나 Number와 같이 정렬이 가능한 형태여야만 한다. 



- **getOrDefault** <br>
  =>  Map에서 존재하지 않은 키로 값을 조회하면 NullPointerException이 발생할 수 있다. 이를 방지하며 기본값을 반환하는 방식

  ```java
  default V getOrDefault(Object key, V defaultValue) {...}
  ```

  

- **computeIfAbsent** <br>
  => 제공된 키에 해당하는 값이 없거나 null인 경우, 키를 이용해 새 값을 계산하고 맵에 추가한다.
- **computeIfPresent** <br>
  => 제공된 키가 존재하면 새 값을 계산하고 맵에 추가한다.
- **compute** <br>
  => 제공된 키로 새 값을 계산하고 맵에 저장한다. 



- **remove** <br>
  => key와 value를 이용하여 항목 제거



- **replaceAll** <br>
  => BiFunction을 적용한 결과로 각 항목의 값을 교체한다. 
- **replace**  <br>
  => 키가 존재하면 맵의 값을 바꾼다. 



- **merge**  <br>
  => 두 개의 맵을 합칠 때 사용 <br>
  => 지정된 키와 연관된 값이 없거나 널인 경우, 키를 널이 아닌 값으로 연결한다. 혹은 주어진 매핑 함수를 이용해 대치하거나 널인 경우 항목을 제거한다. <br>
  => 중복된 키가 있는 경우 BiFunction을 이용하여 어떻게 처리할 지 정의해준다.  <br>

  ```java
  default V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) { ... }
  ```


  

