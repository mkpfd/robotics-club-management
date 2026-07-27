package com.roboticsclub.service;

import com.roboticsclub.model.Member;
import com.roboticsclub.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + id));
    }

    public Member saveMember(Member member) {
        // Basic validation: student ID and email must stay unique across members.
        memberRepository.findByStudentId(member.getStudentId()).ifPresent(existing -> {
            if (!existing.getId().equals(member.getId())) {
                throw new IllegalArgumentException("Student ID already exists: " + member.getStudentId());
            }
        });

        memberRepository.findByEmail(member.getEmail()).ifPresent(existing -> {
            if (!existing.getId().equals(member.getId())) {
                throw new IllegalArgumentException("Email already exists: " + member.getEmail());
            }
        });

        return memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}
