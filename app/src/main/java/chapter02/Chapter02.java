package chapter02;

import java.util.Collection;
import java.util.Objects;

public class Chapter02 {

    /**
     * 040. 함수형 스타일과 절차적 코드에서 null 참조 검사
     *
     * @param collection 전달되는 컬렉션 (그 자체가 null이거나, 요소로 null을 포함하고 있을 수 있음)
     */
    public void checkNull(Collection<?> collection) {
        if (Objects.isNull(collection)) {
            throw new NullPointerException();
        }

        for (Object element : collection) {
            if (Objects.isNull(element)) {
                throw new NullPointerException();
            }
        }
    }

    public void checkNullFunctional(Collection<?> collection) {
        if (Objects.isNull(collection)) {
            throw new NullPointerException();
        }

        if (collection.stream()
                .anyMatch(Objects::isNull)) {
            throw new NullPointerException();
        }
    }
}
