# 함수형 프로그래밍 기법

\#고차원 함수, #커링, #영구 자료구조, #게으른 리스트, #패턴 매칭, #참조 투명성(캐싱, 콤비네이터) 

## 일급 함수 (first-class function)
함수를 일반값처럼 사용하는 것을 의미
인수로 전달하거나 결과로 반환받거나 혹은 자료구조에 저장할 수 있다.
ex) 자바8의 메서드참조(::), 람다 표현식 
 
### 고차원 함수 
- 하나 이상의 함수를 인수로 받음
- 함수를 결과로 받음
위 두 동작 중 하나 이상을 수행하는 함수 

ex) Comparator.comparing

#### 부작용과 고차원 함수
> " 고차원 함수나 메서드를 구현할 때 어떤인수가 전달될 지 알 수 없으므로 인수가 부작용을 포함할 가능성을 염두해둬야 한다. 
함수를 인수로 받아 사용하면서 코드가 정확히 어떤 작ㅇ버을 수행하고 프로그램의 상태를 어떻게 바꿀지 예측하기 어려워진다. 
디버깅도 어려워질 것이다. 따라서 인수로 전달된 함수가 어떤 부작용을 포함하게 될지 정확하게 문서화하는게 좋다. "

### 커링 (currying)
x와 y라는 두 인수를 받는 함수 f를 한 개의 인수를 받는 g라는 함수로 대체하는 기법. <br>
g라는 함수 역시 하나의 인수를 받는 함수를 반환한다. 
> f(x, y) = (g(x))(y)

- 함수 모듈화
- 재사용

ex) [단위 변환 함수]()
한 개의 인수를 갖는 변환 함수를 생성하는 팩토리를 정의한다. 