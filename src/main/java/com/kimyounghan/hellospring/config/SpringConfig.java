package com.kimyounghan.hellospring.config;

import com.kimyounghan.hellospring.repository.MemberRepository;
import com.kimyounghan.hellospring.repository.MemoryMemberRepository;
import com.kimyounghan.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
