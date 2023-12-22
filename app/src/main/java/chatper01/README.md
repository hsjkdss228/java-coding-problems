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
  - Collector 타입의 [downstream](https://chat.openai.com/share/9b0c2cef-db90-48f2-8d35-50c9300b3270)(하위 연산) 파라미터를 추가로 전달하는 경우, 그룹핑 결과의 Value로 해당 하위 연산의 결과를 저장

```java
Stream<T> stream = Stream.of(...);

Map<T, Long> keysAndCounts = stream.collect(
        Collectors.groupingBy(
            character -> character,
            Collectors.counting()
        ));
```
