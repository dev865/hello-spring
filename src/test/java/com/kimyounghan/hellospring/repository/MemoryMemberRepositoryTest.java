package com.kimyounghan.hellospring.repository;

import com.kimyounghan.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();

    @AfterEach
    public void clear(){
        memoryMemberRepository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("tester1");

        memoryMemberRepository.save(member);
        Optional<Member> result = memoryMemberRepository.findById(member.getId());
        // jupiter.api.Assertions를 사용.
        // Assertions.assertEquals(member,result.get());

        // 강좌에선 assertj.core.api를 사용하길 권고
        Assertions.assertThat(member).isEqualTo(result.get());
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("tester1");
        memoryMemberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("tester2");
        memoryMemberRepository.save(member2);

        Member result = memoryMemberRepository.findByName("tester1").get();

        Assertions.assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("tester1");
        memoryMemberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("tester2");
        memoryMemberRepository.save(member2);

        List<Member> result = memoryMemberRepository.findAll();
        Assertions.assertThat(result.size()).isEqualTo(2);
    }
}
