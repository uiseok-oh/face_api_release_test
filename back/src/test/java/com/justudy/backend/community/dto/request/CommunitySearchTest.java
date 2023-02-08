package com.justudy.backend.community.dto.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CommunitySearchTest {

    @Test
    @DisplayName("생성자 확인")
    void testConstructor() {
        //given
        CommunitySearch condition = new CommunitySearch(5L, 2L, "Go",
                "name", "hi", "like");
        //expected
        assertThat(condition.getPage()).isEqualTo(5L);
        assertThat(condition.getSize()).isEqualTo(2L);
        assertThat(condition.getCategory()).isEqualTo("Go");
        assertThat(condition.getType()).isEqualTo("name");
        assertThat(condition.getSearch()).isEqualTo("hi");
        assertThat(condition.getOrder()).isEqualTo("like");
    }

    @Test
    @DisplayName("@Builder.Default 확인")
    void testBuilderDefault() {
        //given
        CommunitySearch condition = CommunitySearch.builder().build();

        //expected
        assertThat(condition.getPage()).isEqualTo(1L);
        assertThat(condition.getSize()).isEqualTo(20);
        assertThat(condition.getCategory()).isNull();
        assertThat(condition.getType()).isNull();
        assertThat(condition.getSearch()).isNull();
        assertThat(condition.getOrder()).isNull();
    }

    @DisplayName("getOffset")
    @ParameterizedTest
    @CsvSource(value = {"1, 20, 0", "2, 10, 10,", "5, 5, 20", "0, 30, 0", "-1, 5, 0", "5, 5000, 8000"})
    void getOffset(Long page, Long size, Long offset) {
        //given
        CommunitySearch condition = CommunitySearch.builder()
                .page(page)
                .size(size)
                .build();

        //expected
        assertThat(condition.getOffset()).isEqualTo(offset);
    }

    @DisplayName("offset(noticeSize) 테스트")
    @ParameterizedTest
    @CsvSource({"1, 20, 0", "2, 20, 17", "3, 20, 34"})
    void getOffsetWithNoticeSize(Long page, Long size, Long offset) {
        //given
        int noticeSize = 3;
        CommunitySearch condition = CommunitySearch.builder()
                .page(page)
                .size(size)
                .build();

        //expected
        assertThat(condition.getOffsetWithNotice(noticeSize)).isEqualTo(offset);
    }
}