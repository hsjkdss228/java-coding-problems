package chatper01;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Chapter01Test {

    private Chapter01 test;

    @BeforeEach
    void setUp() {
        test = new Chapter01();
    }

    @Test
    void countNumberOfCharacters() {
        String string = "aaabcdd";
        assertThat(test.countNumberOfCharacters(string)).isEqualTo(Map.of(
                'a', 3,
                'b', 1,
                'c', 1,
                'd', 2
        ));
        assertThat(test.countNumberOfCharactersFunctional(string)).isEqualTo(Map.of(
                'a', 3L,
                'b', 1L,
                'c', 1L,
                'd', 2L
        ));

        String stringWithEmoji = "aaabcdd😂";
        assertThat(test.countNumberOfCharactersWithUnicodeFunctional(stringWithEmoji))
                .isEqualTo(Map.of(
                        "a", 3L,
                        "b", 1L,
                        "c", 1L,
                        "d", 2L,
                        "😂", 1L
                ));
    }

    @Test
    void findFirstNonRepeatedCharacter() {
        String string = "aaabcdd";
        assertThat(test.findFirstNonRepeatedCharacter(string)).isEqualTo('b');
        assertThat(test.findFirstNonRepeatedCharacterWithLinkedHashMap(string)).isEqualTo("b");
        assertThat(test.findFirstNonRepeatedCharacterFunctional(string)).isEqualTo("b");
    }

    @Test
    void reverse() {
        String string = "cat banana dog rabbit";
        assertThat(test.reverseString(string)).isEqualTo("tac ananab god tibbar");
        assertThat(test.reverseStringFunctional(string)).isEqualTo("tac ananab god tibbar");
        assertThat(test.reverseStringAndWord(string)).isEqualTo("tibbar god ananab tac");
    }

    @Test
    void isNumericString() {
        String numericString = "121235000112583";
        String nonNumericString = "121235ba12=35n";

        assertThat(test.isNumericString(numericString)).isTrue();
        assertThat(test.isNumericStringFunctional(numericString)).isTrue();
        assertThat(test.isNumericStringRegex1(numericString)).isTrue();
        assertThat(test.isNumericStringRegex2(numericString)).isTrue();

        assertThat(test.isNumericString(nonNumericString)).isFalse();
        assertThat(test.isNumericStringFunctional(nonNumericString)).isFalse();
        assertThat(test.isNumericStringRegex1(nonNumericString)).isFalse();
        assertThat(test.isNumericStringRegex2(nonNumericString)).isFalse();
    }

    @Test
    void countVowelAndConsonant() {
        String string = "I have a pen. 이게 무슨 소린지 모르겠다고? 난감하네요 😅";

        Map<String, Long> typesAndCounts = Map.of(
                "vowel", 5L,
                "consonant", 4L
        );

        assertThat(test.countVowelAndConsonant(string)).isEqualTo(typesAndCounts);
        assertThat(test.countVowelAndConsonantWithUnicodeFunctional(string))
                .isEqualTo(typesAndCounts);
    }

    @Test
    void countTargetCharacter() {
        String string = "I have a pan. 이게 무슨 소린지 모르겠다고? 난감하네요";
        String stringWithUnicode = "I have a pan. 이게 무슨 소린지 모르겠다고? 난감하네요 😅😅";

        assertThat(test.countTargetCharacter(string, "a")).isEqualTo(3);
        assertThat(test.countTargetCharacterWithUnicode(stringWithUnicode, "a")).isEqualTo(3);
        assertThat(test.countTargetCharacterWithUnicode(stringWithUnicode, "😅")).isEqualTo(2);
    }

    @Test
    void toNumeric() {
        assertThat(test.toInt("133224")).isEqualTo(133224);
        assertThat(test.toLong("13322499944217")).isEqualTo(13322499944217L);
        assertThat(test.toFloat("133224.112231")).isEqualTo(133224.112231F);
        assertThat(test.toDouble("133224.112")).isEqualTo(133224.112D);
        assertThrows(NumberFormatException.class,
                () -> test.toInt("1313unyo'o;jp89;ll"));

        assertThat(test.toNumeric("133224", Integer.class)).isEqualTo(133224);
        assertThat(test.toNumeric("13322499944217", Long.class)).isEqualTo(13322499944217L);
        assertThat(test.toNumeric("133224.112231", Float.class)).isEqualTo(133224.112231F);
        assertThat(test.toNumeric("133224.112", Double.class)).isEqualTo(133224.112D);
        assertThrows(NumberFormatException.class,
                () -> test.toNumeric("dklylbfalwieuhxrlq", Long.class));
    }
}
