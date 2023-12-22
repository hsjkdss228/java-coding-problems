package chatper01;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Chapter01 {

    /**
     * 001. 주어진 문자열에서 문자 개수를 확인
     *
     * @param string 입력되는 문자열
     * @return 특정 문자 별 문자 개수
     */
    public Map<Character, Integer> countNumberOfCharacters1(String string) {
        Map<Character, Integer> charactersAndCounts = new HashMap<>();

        for (char character : string.toCharArray()) {
            charactersAndCounts.compute(character, (key, value) -> value == null ? 1 : value + 1);
        }

        return charactersAndCounts;
    }

    public Map<Character, Long> countNumberOfCharacters2(String string) {
        return string.chars()
                .mapToObj(character -> (char) character)
                .collect(Collectors.groupingBy(
                        character -> character,
                        Collectors.counting()
                ));
    }
}
