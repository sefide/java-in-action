

# 리팩터링, 테스팅, 디버깅

<br>



## 리팩터링

#### 익명 클래스 -> 람다 표현식

간결하고 가독성 좋은 코드를 구현할 수 있다. 



**주의할 점)** 

1. 익명 클래스의 this는 익명 클래스 자신을 가리키지만 람다에서 this는 람다를 감싸는 클래스를 가리킨다. 
2. 익명 클래스는 감싸고 있는 클래스의 변수를 가릴 수 있지만 (섀도 변수), 람다는 불가능하다. 
3. 익명 클래스는 인스턴스화할 때 명시적으로 형식이 정해지는 반면, 람다의 형식은 콘텍스트에 따라 달라지기 때문에 모호함이 초래될 수 있다. => 명시적 형변환이 필요할 때가 있다. 



#### 람다 표현식 -> 메서드 참조

람다 표현식을 메서드로 생성하여 메서드 참조로 표현, 정적 헬퍼 메서드 활용

#### 명령형 데이터 처리 -> 스트림

[코드로 확인하기](Refactoring.java)



### 람다로 디자인 패턴 적용

1. **전략**

한 유형의 알고리즘을 보유한 상태에서 런타임에 적절한 알고리즘(전략)을 선택하는 기법

[코드로 확인하기](StrategyPattern.java)



2. **템플릿 메서드**

알고리즘의 개요가 존재하고, 알고리즘의 일부를 고칠 수 있는 유연함을 제공



3. **옵저버**

어떤 이벤트가 발생했을 때, 한 객체(주제)가 다른 객체 리스트(옵저버)에 자동으로 알림을 보내야 하는 상황에서 사용



4. **의무체인**





5. **팩토리**




