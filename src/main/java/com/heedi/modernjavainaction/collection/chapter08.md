[1. 컬렉션 API 개선](#컬렉션-API-개선) <br>	[1. 컬렉션 팩토리](#컬렉션-팩토리) <br>	[2. 리스트와 집합처리](#리스트와-집합-처리) <br>



# 컬렉션 API 개선

자바 8,9에 추가된 내용들을 다룬다. 

<br>

## 컬렉션 팩토리


- **Arrays.asList **<br>

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

```java
// AbstractList.java
public void add(int index, E element) {
  throw new UnsupportedOperationException();
}
```



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

  중복 요소가 있는 경우, IllegalArgumentException 발생 

  

- **Map.of(K k1, V v1, K k2, V v2 ..)**

  10개 이하의 키를 가진 맵 생성 시 유용

  

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



[코드로 확인하기](CollectionFactory.java)

<br>



## 리스트와 집합 처리

*in List, Set Interface .. since java8*

- removeIf
- replaceAll
- sort

