package chapter02;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Chapter02Test {

    private Chapter02 test;

    @BeforeEach
    void setUp() {
        test = new Chapter02();
    }

    @Test
    void isNull() {
        assertThrows(NullPointerException.class, () -> test.checkNull(null));
        assertThrows(NullPointerException.class, () -> test.checkNull(List.of(1, 3, null, 5)));
        assertDoesNotThrow(() -> test.checkNull(Set.of(1, 3, 5, 7)));

        assertThrows(NullPointerException.class, () -> test.checkNullFunctional(null));
        assertThrows(NullPointerException.class, () -> test.checkNullFunctional(List.of(1, null)));
        assertDoesNotThrow(() -> test.checkNullFunctional(Set.of(1, 3, 5, 7)));
    }
}
