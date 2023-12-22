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

        assertThat(test.countNumberOfCharacters1(string)).isEqualTo(Map.of(
                'a', 3,
                'b', 1,
                'c', 1,
                'd', 2
        ));
        assertThat(test.countNumberOfCharacters2(string)).isEqualTo(Map.of(
                'a', 3L,
                'b', 1L,
                'c', 1L,
                'd', 2L
        ));
    }
}