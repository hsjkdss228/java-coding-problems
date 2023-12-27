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

    /**
     * 041. null 참조 검사와 맞춤형 NullPointerException 던지기
     *
     * @param string 전달되는 문자열
     * @return 전달되는 문자열이 null이 아닐 경우 전달되는 문자열을 그대로 반환, null인 경우 정의된 에러 메시지를 반환
     */
    public String checkNull2(String string) {
        try {
            return Objects.requireNonNull(string, "전달된 문자열이 null입니다.");
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }
}
