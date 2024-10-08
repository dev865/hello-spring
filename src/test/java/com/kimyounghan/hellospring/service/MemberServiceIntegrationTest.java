package com.kimyounghan.hellospring.service;

import com.kimyounghan.hellospring.domain.Member;
import com.kimyounghan.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest     // 스프링 컨테이너와 테스트를 함께 실행.
@Transactional      // Test 케이스에 @Transactional을 쓰면 테스트 시작전 트랜젝션을 시작 -> 테스트 완료후 롤백
class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {
        // given
        Member member1 = new Member();
        member1.setName("hello");

        // when
        Long id = memberService.join(member1);

        // then
        Member findMember = memberService.findOne(id).get();
        assertThat(member1.getId()).isEqualTo(findMember.getId());
    }

    @Test
    void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));   // 콜백함수를 실행시 첫번째 예외를 기대한다.

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}