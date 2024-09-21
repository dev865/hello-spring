package com.kimyounghan.hellospring.controller;

import com.kimyounghan.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    // final로 불변성 부여
    private final MemberService memberService;

    // 생성자 주입 방식을 사용하면 더이상 실행중 호출이 불가능하다.
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
        System.out.println("memberService = " + memberService.getClass());
    }
    
}