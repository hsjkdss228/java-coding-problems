package chatper01;

import static org.assertj.core.api.Assertions.assertThat;

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
}
