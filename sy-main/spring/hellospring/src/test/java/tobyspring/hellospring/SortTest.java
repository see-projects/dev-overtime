package tobyspring.hellospring;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class SortTest {

    @Test
    void sort() {
        // 테스트 실행 준비
        Sort sort = new Sort();

        // 실행
        List<String> list = sort.sortByLength(Arrays.asList("aa", "b"));

        // 검증
        Assertions.assertThat(list).isEqualTo(List.of("b", "aa"));
    }

    @Test
    void sort3Items() {
        // 테스트 실행 준비
        Sort sort = new Sort();

        // 실행
        List<String> list = sort.sortByLength(Arrays.asList("aa", "ccc", "b"));

        // 검증
        Assertions.assertThat(list).isEqualTo(List.of("b", "aa", "ccc"));
    }

    @Test
    void sortAlreadySorted() {
        // 테스트 실행 준비
        Sort sort = new Sort();

        // 실행
        List<String> list = sort.sortByLength(Arrays.asList("b", "aa", "ccc"));

        // 검증
        Assertions.assertThat(list).isEqualTo(List.of("b", "aa", "ccc"));
    }
}
