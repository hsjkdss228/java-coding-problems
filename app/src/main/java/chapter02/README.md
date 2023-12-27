# 2장. 객체와 불변성, switch 문

## 040. 함수형 스타일과 절차적 코드에서 null 참조 검사

- Stream 인스턴스의 요소의 null 검사에는 `Objects.isNull()`, `Objects.nonNull()` 메서드를 Predicate으로 활용할 수 있음

```java
boolean containsNull = Stream.of("a", "bbb", null, "d")
        .anyMatch(Objects::isNull);

// true
```
