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
