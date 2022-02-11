package com.example.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Builder
@AllArgsConstructor
@Data
public class PageRequestDto {

    private int page;
    private int size;
    private String type;
    private String keyword;

    public PageRequestDto(){
        this.page = 1;
        // 한페이지에 보여줄 게시글 갯수 가 10개 한페이지에 10개의 리스트
        this.size = 10;
        // 한페이지에 보여줄 게시글 갯수 가 10개 한페이지에 5개의 리스트
//        this.size = 5;
    }

    public Pageable getPageable(Sort sort){

        return PageRequest.of(page -1 , size, sort);

    }

}
