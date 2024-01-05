package chatper01;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

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

    public Map<String, Long> countNumberOfCharactersWithUnicodeFunctional(String string) {
        return string.codePoints()
                .mapToObj(codePoint -> String.valueOf(Character.toChars(codePoint)))
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

    private static final Pattern WHITESPACE_PATTERN = Pattern.compile(" +");

    public String reverseStringFunctional(String string) {
        return WHITESPACE_PATTERN.splitAsStream(string)
                .map(word -> new StringBuilder(word)
                        .reverse())
                .collect(Collectors.joining(" "));
    }

    public String reverseStringAndWord(String string) {
        return new StringBuilder(string).reverse()
                .toString();
    }

    /**
     * 004. 숫자만 포함하는 문자열인지 검사
     *
     * @param string 입력되는 문자열
     * @return 숫자만 포함된 문자열인지에 대한 여부
     */
    public boolean isNumericString(String string) {
        for (char character : string.toCharArray()) {
            if (!Character.isDigit(character)) {
                return false;
            }
        }
        return true;
    }

    public boolean isNumericStringFunctional(String string) {
        return string.chars()
                .allMatch(Character::isDigit);
    }

    private static final Pattern NUMERIC_PATTERN = Pattern.compile("\\d+");
    private static final Pattern CONTAINS_NON_NUMERIC_PATTERN = Pattern.compile("(?!\\d+$).+");

    public boolean isNumericStringRegex1(String string) {
        return NUMERIC_PATTERN.matcher(string)
                .matches();
    }

    public boolean isNumericStringRegex2(String string) {
        return !CONTAINS_NON_NUMERIC_PATTERN.matcher(string)
                .matches();
    }

    private static final Set<Character> VOWELS_CHARACTER = Set.of('a', 'e', 'i', 'o', 'u');
    private static final Set<String> VOWELS_STRING = Set.of("a", "e", "i", "o", "u");

    /**
     * 005. 모음과 자음 세기
     *
     * @param string 입력되는 문자열
     * @return Key: 자음/모음, Value: 개수
     */
    public Map<String, Long> countVowelAndConsonant(String string) {
        long vowelCount = 0;
        long consonantCount = 0;

        for (char character : string.toLowerCase().toCharArray()) {
            if (VOWELS_CHARACTER.contains(character)) {
                vowelCount += 1;
                continue;
            }

            if (character >= 'a' && character <= 'z') {
                consonantCount += 1;
            }
        }

        return Map.of(
                "vowel", vowelCount,
                "consonant", consonantCount
        );
    }

    public Map<String, Long> countVowelAndConsonantWithUnicodeFunctional(String string) {
        Map<Boolean, Long> partitionedAndCounts = string.toLowerCase()
                .codePoints()
                .mapToObj(Character::toChars)
                .filter(chars -> chars.length == 1 && chars[0] >= 'a' && chars[0] <= 'z')
                .map(String::valueOf)
                .collect(Collectors.partitioningBy(
                        VOWELS_STRING::contains,
                        Collectors.counting()
                ));

        return Map.of(
                "vowel", partitionedAndCounts.get(true),
                "consonant", partitionedAndCounts.get(false)
        );
    }

    /**
     * 006. 문자 빈도수 세기
     *
     * @param string 입력되는 문자열
     * @param target 대상 문자
     * @return 문자열 내 대상 문자의 빈도수
     */
    public long countTargetCharacter(String string, String target) {
        return string.length() - string.replaceAll(target, "").length();
    }

    public long countTargetCharacterWithUnicode(String string, String target) {
        return string.codePoints()
                .mapToObj(codePoint -> String.valueOf(Character.toChars(codePoint)))
                .filter(character -> character.equals(target))
                .count();
    }

    /**
     * 007. 문자열을 int, long, float, double로 변환
     *
     * @param string 입력되는 숫자 형식의 문자열
     * @return 대상 타입으로 변환된 숫자
     */
    public int toInt(String string) {
        return Integer.parseInt(string);
    }

    public long toLong(String string) {
        return Long.parseLong(string);
    }

    public float toFloat(String string) {
        return Float.parseFloat(string);
    }

    public double toDouble(String string) {
        return Double.parseDouble(string);
    }

    public Number toNumeric(String string, Class<? extends Number> classType) {
        if (classType.equals(Integer.class)) {
            return Integer.valueOf(string);
        }

        if (classType.equals(Long.class)) {
            return Long.valueOf(string);
        }

        if (classType.equals(Float.class)) {
            return Float.valueOf(string);
        }

        if (classType.equals(Double.class)) {
            return Double.valueOf(string);
        }

        throw new NumberFormatException();
    }

    /**
     * 008. 문자열에서 여백 제거
     *
     * @param string 입력되는 문자열
     * @return 여백이 제거된 문자열
     */
    public String removeWhitespaces(String string) {
        return string.replaceAll("\\s", "");
    }

    /**
     * 009. 구분자로 여러 문자열 합치기
     *
     * @param delimiter 구분자
     * @param strings   주어지는 문자열들
     * @return 구분자를 이용해 합쳐진 문자들
     */
    public String joinByDelimiterWithJoin(
            String delimiter,
            String... strings
    ) {
        return String.join(delimiter, strings);
    }

    public String joinByDelimiterWithStringBuilder(
            char delimiter,
            String... strings
    ) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String string : strings) {
            stringBuilder.append(string).append(delimiter);
        }

        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());

        return stringBuilder.toString();
    }

    public String joinByDelimiterWithStringJoiner(
            String delimiter,
            Iterable<? extends CharSequence> strings
    ) {
        StringJoiner stringJoiner = new StringJoiner(delimiter);

        strings.forEach(stringJoiner::add);

        return stringJoiner.toString();
    }

    public String joinByDelimiterWithJoining(
            String delimiter,
            Iterable<? extends CharSequence> strings
    ) {
        return StreamSupport.stream(strings.spliterator(), false)
                .collect(Collectors.joining(delimiter));
    }

    /**
     * 010. 모든 순열 생성
     *
     * @param string 주어지는 문자열
     * @return 주어지는 문자열의 각 단어를 서로 다르게 조합한 순열의 모든 경우의 수
     */
    public Set<String> permute(String string) {
        Set<String> permutations = new HashSet<>();

        permute(permutations, "", string);

        return permutations;
    }

    private void permute(Set<String> permutations, String prefix, String string) {
        int length = string.length();

        if (length == 0) {
            permutations.add(prefix);
            return;
        }

        for (int i = 0; i < length; i += 1) {
            String nextPrefix = prefix + string.charAt(i);
            String remainingString
                    = string.substring(0, i) + string.substring(i + 1, length);
            permute(permutations, nextPrefix, remainingString);
        }
    }

    public Set<String> permuteFunctional(String string) {
        Set<String> permutations = new HashSet<>();

        permuteFunctional(permutations, "", string);

        return permutations;
    }

    private void permuteFunctional(Set<String> permutations, String prefix, String string) {
        int length = string.length();

        if (length == 0) {
            permutations.add(prefix);
            return;
        }

        IntStream.range(0, length)
                .forEach(index -> {
                    String nextPrefix = prefix + string.charAt(index);
                    String remainingString
                            = string.substring(0, index) + string.substring(index + 1, length);
                    permuteFunctional(permutations, nextPrefix, remainingString);
                });
    }
}
