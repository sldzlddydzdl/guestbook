package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDto;
import com.example.guestbook.dto.PageResultDto;
import com.example.guestbook.entity.Guestbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    private GuestbookService service;

    @DisplayName("1. 등록 테스트")
    @Test
    public void testRegister(){
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample Title....")
                .content("Sample Content")
                .writer("user0")
                .build();

        System.out.println(service.register(guestbookDTO));
    }

    @DisplayName("2. 페이지글 list 로 받아보기")
    @Test
    public void testList(){
        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .page(1)
                .size(10)
                .build();

        PageResultDto<GuestbookDTO, Guestbook> resultDto = service.getList(pageRequestDto);

        for(GuestbookDTO guestbookDTO : resultDto.getDtoList()){
            System.out.println(guestbookDTO);
        }
    }

    @DisplayName("3. 페이지 글 이전 다음 리스트 테스트")
    @Test
    public void nextPrevTest(){
        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .page(1)
                .size(10)
                .build();

        PageResultDto<GuestbookDTO, Guestbook> resultDto = service.getList(pageRequestDto);

        System.out.println("PREV : " + resultDto.isPrev()); // 1페이지므로 이전으로 가는 링크 필요 없음
        System.out.println("NEXT : " + resultDto.isNext()); // 다음 페이지로 가는 링크 필요
        System.out.println("TOTAL : " + resultDto.getTotalPage()); // 전체 페이지 갯수
        System.out.println("--------------------------------------");
        for(GuestbookDTO guestbookDTO : resultDto.getDtoList()){
            System.out.println(guestbookDTO);
        }

        System.out.println("-------------------------------------");
        resultDto.getPageList().forEach(i-> System.out.println(i));

    }

    @DisplayName("4. 검색 조건 테스트")
    @Test
    public void searchTest(){
        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .page(1)
                .size(10)
                .type("tc") // 검색 조건 t, c, w, tc, tcw ...
                .keyword("mod") // 검색 키워드
                .build();

        PageResultDto<GuestbookDTO, Guestbook> resultDto = service.getList(pageRequestDto);

        System.out.println("PREV : " + resultDto.isPrev());
        System.out.println("NEXT : " + resultDto.isNext());
        System.out.println("TOTAL : " + resultDto.getTotalPage());

        System.out.println("-------------------------------------------");
        for(GuestbookDTO guestbookDTO : resultDto.getDtoList()){
            System.out.println(guestbookDTO);
        }

        System.out.println("===========================================");
        resultDto.getPageList().forEach(i-> System.out.println(i));
    }
}
