package com.kimyounghan.hellospring.config;

import com.kimyounghan.hellospring.aop.TimeTraceAop;
import com.kimyounghan.hellospring.repository.JDBCTemplateMemberRepository;
import com.kimyounghan.hellospring.repository.JdbcMemberRepository;
import com.kimyounghan.hellospring.repository.MemberRepository;
import com.kimyounghan.hellospring.repository.MemoryMemberRepository;
import com.kimyounghan.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private DataSource dataSource;
    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }
//
//    @Bean
//    public TimeTraceAop timeTraceAop() {
//        return new TimeTraceAop();
//    }

    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
        return new JDBCTemplateMemberRepository(dataSource);
    }



}
