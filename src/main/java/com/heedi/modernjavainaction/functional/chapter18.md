# 함수형 관점으로 생각하기 

\#부작용, #불변성, #선언형 프로그래밍, #참조 투명성

"프로그램 내에 synchronized 키워드가 발견된다면 프로그램 관리 요청을 거절해라"라는 말이 있을 정도로 동시성 버그를 고치는건 결코 쉽지 않다. 

함수형 프로그래밍은 시스템 구현과 유지보수를 위해 불변성이라는 개념을 활용해 부작용이 없는 설계를 하는게 목적이다. 
자바8에서 스트림을 이용하다보면 잠금 문제를 신경쓸 필요가 없다. 
이는 스트림 처리 파이프라인에서는 값을 바꿀 수 없는 변수를 사용하지 않는다는 조건을 만족하기 떄문에 가능하다. 

> 함수형 프로그래밍은 시스템 구현과 유지보수를 위해 선언형 프로그래밍을 따르는 방식이며 부작용이 없는 계산을 지향한다.
 
<br>

### 공유된 가변 데이터 

공유된 가변 데이터를 사용하는 구조는 프로그램 전체에서 데이터 갱신 사실을 추적하기 어려워진다.
따라서 어떠한 자료구조도 바꾸지 않는 순수 메서드를 사용하거나 불변 객체를 이용해야 한다.  

#### 순수 메서드, 부작용 없는 메서드
객체의 상태를 바꾸지 않고 return문을 통해서만 자신의 결과를 반환하는 메서드
아래와 같이 메서드 내부에 포함되지 않는 부작용이 없다. 

- 자료구조를 구치거나 필드에 값을 할당
- 예외 발생
- 파일에 쓰기 등의 I/O 동작 수행 

<br>

### 선언형 프로그래밍 vs 명령형 프로그래밍 

#### 선언형 프로그래밍
- '무엇을'에 집중
- 원하는 동작이 무엇이고 시스템이 어떻게 그 목표를 달성할지 규칙이 정해진다. 
- 명확하게 코드에 문제를 해결하는 방법이 드러난다. 

#### 명령형 프로그래밍
- '어떻게'에 집중
- 고전 객체지향 프로그래밍 
- (할당, 조건문, 분기문, 루프) 

<br>

### 함수형 프로그래밍 
함수 : 0개 이상의 인수를 가지며, 한 개 이상의 결과를 반환하지만 부작용이 없는 것을 의미한다. 
함수형 : 수학의 함수처럼 부작용이 없는 메서드.
시스템의 다른 부분, 그리고 호출자에 아무 영향을 미치지 않게 설계되어 호출자가 내부적인 부작용을 파악하거나 신경쓸 필요가 없다. 
내부적으로 함수형이 아닌 기능을 사용할 수 있다. 

*in java*
- 실제 부작용이 있지만 아무도 보지 못하게 하는 참조 투명성 보장
    > 참조 투명성 : 같은 인수로 호출했을 떄 항상 같은 결과를 반환한다. 
- 메서드 레벨 
    - 지역 변수만을 변경
    - 객체의 모든 필드가 final이어야 하고 모든 참조 필드는 불변 객체를 직접 참조해야 한다.
    - 새로 생성한 객체는 갱신 가능 
    - 예외 발생 X (Optional으로 대체)
- 비함수형 동작을 감출 수 있는 상황에섬나 부작용을 포함하는 라이브러리 함수를 사용해야 한다. 
