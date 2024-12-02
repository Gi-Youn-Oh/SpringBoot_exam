package org.zerock.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.board.entity.Member;
import org.zerock.board.entity.QMember;
import org.zerock.board.repository.MemberRepository;

import com.querydsl.core.BooleanBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/member/")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private final MemberRepository memberRepository;

    @GetMapping("/mid/{mid}")
    public String getMemberById(@PathVariable String mid, Model model) {
        // 동적 쿼리 생성
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(QMember.member.email.contains(mid));

        // 검색된 데이터 가져오기
        Iterable<Member> members = memberRepository.findAll(predicate);

        // 데이터를 Model에 추가
        model.addAttribute("members", members);

        // `member/mid/containMember` 지정 경로로 반환
        return "member/mid/containMember";
    }
}
