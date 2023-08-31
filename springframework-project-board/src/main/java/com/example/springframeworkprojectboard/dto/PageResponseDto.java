package com.example.springframeworkprojectboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;


/*
 * 페이징 처리를 위한 클래스
 * 페이지 계산 등 정보 담고 있음
 * 여러 정보를 생성자를 이용해 받아서 처리하는 것이 안전
 */
@Getter
@ToString
public class PageResponseDto<E> {

    private int page; //현재 페이지 번호
    private int size; //현재 페이지당 게시물 수
    private int total; //전체 게시물 수
    private int totalPage; //전체 페이지

    private int start; //시작 페이지 번호
    private int end; //끝 페이지 번호

    private boolean prev; //이전 페이지의 존재 여부
    private boolean next; //다음 페이지의 존재 여부

    private List<E> dtoList; //게시물 데이터가 담길 리스트(제네릭 설계)
    private Long startNo; //게시물 일련 번호

    @Builder(builderMethodName = "withAll")
    public PageResponseDto(PageRequestDto requestDto, int total, List<E> dtoList) {
        this.page = requestDto.getPage();
        this.size = requestDto.getSize();

        this.total = total;
        this.dtoList = dtoList;

        this.end = (int) (Math.ceil(this.page / 10.0)) * 10;
        this.start = this.end - 9;  //시작 페이지 계산

        int last = (int) (Math.ceil(total / (double)size));
        this.end = end > last ? last : end; //마지막 페이지 계산

        this.prev = this.start > 1; //이전 페이지 계산
        this.next = total > this.end * this.size;   //다음 페이지 계산

        this.startNo = Long.valueOf(total - (this.page -1) * size); //일련번호 계산
    }
}
