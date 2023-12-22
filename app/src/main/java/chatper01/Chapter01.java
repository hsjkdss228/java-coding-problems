package chatper01;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Chapter01 {

    /**
     * 001. 주어진 문자열에서 문자 개수를 확인
     *
     * @param string 입력되는 문자열
     * @return 특정 문자 별 문자 개수
     */
    public Map<Character, Integer> countNumberOfCharacters(String string) {
        Map<Character, Integer> charactersAndCounts = new HashMap<>();

        for (char character : string.toCharArray()) {
            charactersAndCounts.compute(character, (key, value) -> value == null ? 1 : value + 1);
        }

        return charactersAndCounts;
    }

    public Map<Character, Long> countNumberOfCharactersFunctional(String string) {
        return string.chars()
                .mapToObj(character -> (char) character)
                .collect(Collectors.groupingBy(
                        character -> character,
                        Collectors.counting()
                ));
    }

    private static final int EXTENDED_ASCII_CODES = 256;

    private static final int NOT_APPEARED = -1;
    private static final int REPEATED = -2;

    /**
     * 002. 반복되지 않는 첫 번째 문자 찾기
     *
     * @param string 입력되는 문자열
     * @return 문자열 내에서 유일한 문자 중 가장 앞에 위치한 문자
     */
    public char findFirstNonRepeatedCharacter(String string) {
        int[] flags = new int[EXTENDED_ASCII_CODES];

        Arrays.fill(flags, -1);

        for (int i = 0; i < string.length(); i += 1) {
            char character = string.charAt(i);

            if (flags[character] == NOT_APPEARED) {
                flags[character] = i;
                continue;
            }

            flags[character] = REPEATED;
        }

        int position = Integer.MAX_VALUE;

        for (int index : flags) {
            if (index >= 0) {
                position = Math.min(position, index);
            }
        }

        return position == Integer.MAX_VALUE ? Character.MIN_VALUE : string.charAt(position);
    }

    public String findFirstNonRepeatedCharacterWithLinkedHashMap(String string) {
        Map<String, Integer> charactersAndRepeatCounts = new LinkedHashMap<>();

        for (String character : string.split("")) {
            charactersAndRepeatCounts.compute(
                    character,
                    (key, value) -> value == null ? 1 : value + 1
            );
        }

        for (Map.Entry<String, Integer> characterAndRepeatCount
                : charactersAndRepeatCounts.entrySet()) {
            if (characterAndRepeatCount.getValue() == 1) {
                return characterAndRepeatCount.getKey();
            }
        }

        return Character.toString(Character.MIN_VALUE);
    }

    public String findFirstNonRepeatedCharacterFunctional(String string) {
        Map<String, Long> charactersAndRepeatCounts = Arrays.stream(string.split(""))
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        LinkedHashMap::new,
                        Collectors.counting()
                ));

        return charactersAndRepeatCounts.entrySet()
                .stream()
                .filter(characterAndRepeatCount -> characterAndRepeatCount.getValue() == 1)
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(Character.toString(Character.MIN_VALUE));
    }

    private static final String WHITESPACE = " ";

    /**
     * 003. 글자와 단어 뒤집기
     *
     * @param string 입력되는 문자열
     * @return 각 단어의 알파벳 순서가 모두 뒤집힌 문자열
     */
    public String reverseString(String string) {
        String[] words = string.split(WHITESPACE);

        StringBuilder reversedStringBuilder = new StringBuilder();

        for (String word : words) {
            StringBuilder reversedWordBuilder = new StringBuilder();

            for (int i = word.length() - 1; i >= 0; i -= 1) {
                reversedWordBuilder.append(word.charAt(i));
            }

            reversedStringBuilder.append(reversedWordBuilder)
                    .append(WHITESPACE);
        }

        deleteLastWhitespace(reversedStringBuilder);

        return reversedStringBuilder.toString();
    }

    private void deleteLastWhitespace(StringBuilder reversedStringBuilder) {
        int length = reversedStringBuilder.length();
        reversedStringBuilder.delete(length - 1, length);
    }

    private static final Pattern PATTERN = Pattern.compile(" +");

    public String reverseStringFunctional(String string) {
        return PATTERN.splitAsStream(string)
                .map(word -> new StringBuilder(word)
                        .reverse())
                .collect(Collectors.joining(" "));
    }

    public String reverseStringAndWord(String string) {
        return new StringBuilder(string).reverse()
                .toString();
    }
}
