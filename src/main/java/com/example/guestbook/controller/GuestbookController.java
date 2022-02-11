package com.example.guestbook.controller;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDto;
import com.example.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor // 자동 주입을 위한 Annotation
public class GuestbookController {

    private final GuestbookService service;

    @GetMapping("/")
    public String index(){
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDto pageRequestDto , Model model){

        log.info("list............" + pageRequestDto);

        model.addAttribute("result" , service.getList(pageRequestDto));

    }

    @GetMapping("/register")
    public void register(){
        log.info("register get...");
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes){

        log.info("dto..." + dto);

        Long gno = service.register(dto);

        redirectAttributes.addFlashAttribute("message" ,gno);

        return "redirect:/guestbook/list";
    }

    @GetMapping({"/read" ,"/modify"})
    public void read(long gno , @ModelAttribute("requestDTO") PageRequestDto requestDto, Model model){
        log.info("gno: " + gno);

        GuestbookDTO dto = service.read(gno);

        model.addAttribute("dto" ,dto);
    }

    // localhost:8080/guestbook/read?gno=311&page=1
    // @ModelAttribute("requestDTO") PageRequestDto requestDto 에서 PageRequestDto 객체를 뷰 단에 requestDTO 로 넘겨준다

//    <a th:href="@{/guestbook/modify(gno = ${dto.gno} , page={requestDTO.page})}">
//        <button type="button" class="btn btn-primary">Modify</button>
//      </a>

    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes){
        log.info("gno : " + gno);

        service.remove(gno);

        redirectAttributes.addFlashAttribute("message" , gno);

        return "redirect:/guestbook/list";
    }

    @PostMapping("/modify")
    public String modify(
            GuestbookDTO dto ,
            @ModelAttribute("requestDTO") PageRequestDto requestDto ,
            RedirectAttributes redirectAttributes)
    {
        log.info("post modify.......................................");
        log.info("dto : " + dto);

        service.modify(dto);

        redirectAttributes.addAttribute("page" , requestDto.getPage());
        redirectAttributes.addAttribute("type" , requestDto.getType());
        redirectAttributes.addAttribute("keyword" , requestDto.getKeyword());
        redirectAttributes.addAttribute("gno" , dto.getGno());

        return "redirect:/guestbook/read" ;
    }
}
