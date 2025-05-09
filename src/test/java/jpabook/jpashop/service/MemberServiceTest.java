package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
//    @Rollback(false)
    public void 회원가입() {
        // given
        Member member = new Member();
        member.setName("taeyi");

        // when
        Long memberId = memberService.join(member);

        // then
        assertThat(member).isEqualTo(memberRepository.findOne(memberId));
    }

    @Test
    public void 중복회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("taeyi");
        memberService.join(member1);

        // when
        Member member2 = new Member();
        member2.setName("taeyi");


        // then
//        assertThatThrownBy(() -> memberService.join(member2));
    }
}