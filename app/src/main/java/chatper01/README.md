# 1장. 문자열과 수, 수학

## 001. 문자 개수 세기

### `Map.compute()`

- 특정 Key와, 대상 Map 인스턴스에 대해 수행할 동작이 정의된 콜백 함수를 인자로 전달.

```java
Map<Character, Integer> charactersAndCounts = new HashMap<>();
```

- 위와 같은 Map 인스턴스가 존재할 때, 다음과 같이 특정 Key의 존재 유무에 따라 수행할 동작을 결정하는 연산은 다음과 같이 대체될 수 있음. 

```java
if (charactersAndCounts.containsKey(character)) {
    charactersAndCounts.put(character, charactersAndCounts.get(character) + 1);
    continue;
}
charactersAndCounts.put(character, 1);
```

```java
charactersAndCounts.compute(character, (key, value) -> value == null ? 1 : value + 1);
```

### `Collectors.groupingBy()`

- Stream 인스턴스의 각 요소들을 그룹화
  - `collect()`의 결과로 `Map<T, List<T>>` 타입의 결과 반환
  - Map 구현체를 반환하는 함수형 인터페이스를 전달할 경우, 그룹핑 결과를 저장하는 데 해당 Map 구현체를 사용
  - Collector 타입의 [downstream](https://chat.openai.com/share/9b0c2cef-db90-48f2-8d35-50c9300b3270)(하위 연산) 파라미터를 추가로 전달하는 경우, 그룹핑 결과의 Value로 해당 하위 연산의 결과를 저장

```java
Stream<T> stream = Stream.of(...);

Map<T, Long> keysAndCounts = stream.collect(
        Collectors.groupingBy(
            character -> character,
            HashMap::new,
            Collectors.counting()
        ));
```

### Java에서 유니코드 문자 표현하기

- Java에서 단일 `char` 타입은 65536(16비트, 0xFFFF)보다 작은 숫자로 나타내는 문자를 표현할 수 있음
- 이보다 큰 숫자로 나타내는 문자들은 32비트로 나타내지며, 일반적으로 UTF-32 인코딩 스키마를 통해 표현될 수 있음
- **Java는 UTF-32를 지원하지 않으므로**, 해당 문자들을 16비트 상위 대리(High Surrogate), 16비트 하위 대리(Low Surrogate)의 쌍인 _대리 쌍(Surrogate Pair)_ 으로 나타냄

```java
String string = "😂🥲🥹🤔";
  
string.length();      // 8
  
System.out.print("😂🥲🥹🤔".charAt(0));     // 해당 문자를 나타내는 대리 쌍 중 상위 대리만 추출되므로 😂 이모지가 출력되지 않음
  
for (int i = 0; i < string.length(); i += 1) {
    int codePoint = string.codePointAt(i);
    if (Character.charCount(codePoint) == 2) {      // 대리 쌍인 문자일 경우 다음 문자 조회를 위해 index를 2씩 증가
        i += 1;
    }
    char[] chars = Character.toChars(codePoint);
    String character = String.valueOf(chars);
      
    System.out.print(chars);            // 대리 쌍이 모두 포함되었으므로 각 이모지 출력
    System.out.print(character);        // 대리 쌍이 모두 포함되었으므로 각 이모지 출력
}
```

## 002. 반복되지 않는 첫 번째 문자 찾기

### `Function.identity()`

- 항등 함수.
- 즉 `value -> value` 람다식과 같음

```java
// 바로 위의 keysAndCounts와 같음
Map<T, Long> keysAndCounts = stream.collect(
        Collectors.groupingBy(
            Function.identity(),
            HashMap::new,
            Collectors.counting()
        ));
```

## 003. 글자와 단어 뒤집기

### `Pattern.splitAsStream()`

- 문자열을 정규표현식이 컴파일된 Pattern 객체를 이용해 배열의 각 요소들로 나눈 뒤, 해당 배열을 이용해 Stream 인스턴스를 생성

### `StringBuilder.reverse()`

- StringBuilder 인스턴스에 내포된 문자열을 역순으로 배치

## 004. 숫자만 포함하는 문자열인지 검사

- Java에서 특정 정규표현식 검증을 위한 `Pattern` 객체는 [생성 비용이 높은 인스턴스](https://github.com/hsjkdss228/dev-book-studies/tree/main/effective-java-3e/notes/chapter2/item6#%EC%83%9D%EC%84%B1-%EB%B9%84%EC%9A%A9%EC%9D%B4-%EB%B9%84%EC%8B%BC-%EA%B0%9D%EC%B2%B4%EC%9D%98-%EC%9E%AC%EC%82%AC%EC%9A%A9)이기 때문에, 자주 사용되는 정규표현식은 상수로 정의한 뒤 사용하면 효과적.

## 005. 모음과 자음 세기

### `Collectors.partitioningBy()`

- Stream의 요소를 `predicate`으로 주어지는 판별 연산에 따라 `Map<Boolean, List<T>>`의 value의 `List<T>`에 각각 파티셔닝
  - `downstream`이 주어지는 경우, `List<T>`에 대해 추가적인 연산을 수행

```java
Map<Boolean, Long> predicatedAndCounts = stream.collect(
        Collectors.partitioningBy(
            value -> value.equals("abcde"),
            Collectors.counting()
        ));
```
