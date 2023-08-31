package com.example.springframeworkprojectboard.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Arrays;

/*
 * 페이징 처리와 검색 처리를 위한 클래스
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDto {

    @Builder.Default //build 할 때 아무 값도 입력이 없으면 기본값 1 지정
    @Min(value = 1)
    @Positive //음수 값 미허용 명시
    private int page = 1;

    @Builder.Default
    @Min(value = 10)
    @Max(value = 100)
    @Positive
    private int size = 10;
    private int totalPage; //전체 페이지 수

    private String link;
    private String[] types; //검색 타입(필터)
    private String keyword; //검색어
    private LocalDateTime from;
    private LocalDateTime to;

    /*
     * limit 에서 사용하는 건너뛰기
     * skip 의 수를 가져오는 메소드
     */
    public int getSkip() {
        return (page -1) * size;
    }

    /*
     * 조회 페이지로 이동할 때 페이지 번호가 붙을 경우
     * 기존에 페이지로 이동하기 위해 링크 내부에 검색 조건에서 세팅된
     * 파라미터 값을 가져오는 메소드
     */
    public String getLink() {
        StringBuilder builder = new StringBuilder();
        builder.append("page=" + this.page);
        builder.append("&size=" + this.size);

        if (this.types != null && this.types.length > 0) {
            for (int i = 0; i < this.types.length; i++) {
                builder.append("&types=" + types[i]);
            }
        }
        if (this.types != null && this.types.length > 0) {
            for (int i = 0; i < this.types.length; i++) {
                builder.append("&types=" + types[i]);
            }
        }
        if (this.keyword != null) {
            try {
                builder.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8"));
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (this.from != null) {
            builder.append("&from=" + from.toString());
        }
        if (this.to != null) {
            builder.append("&to=" + to.toString());
        }
        return builder.toString();
    }

    /*
     * 화면에 검색 조건 표시하는 메소드
     * 다른 페이지로 이동 시 검색 값의 파라미터가 유지되지 않으므로
     * 아래 메소드를 EL 태그로 활용
     */
    public boolean checkType(String type) {
        if (this.types == null || this.types.length == 0) {
            return false;
        }
        return Arrays.asList(this.types).contains(type);
    }
}
