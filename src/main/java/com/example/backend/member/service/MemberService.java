package com.example.backend.member.service;

import com.example.backend.member.dto.MemberRequestDto;
import com.example.backend.member.dto.MemberResponseDto;
import com.example.backend.member.entity.Member;
import com.example.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public List<MemberResponseDto> findAll() {
        return memberRepository.findAll().stream()
                .map(MemberResponseDto::from)
                .toList();
    }

    public MemberResponseDto findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Member not found: id=" + id));
        return MemberResponseDto.from(member);
    }

    @Transactional
    public MemberResponseDto create(MemberRequestDto request) {
        Member member = Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();
        return MemberResponseDto.from(memberRepository.save(member));
    }

    @Transactional
    public MemberResponseDto update(Long id, MemberRequestDto request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Member not found: id=" + id));
        member.update(request.getName(), request.getEmail());
        return MemberResponseDto.from(member);
    }

    @Transactional
    public void delete(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new NoSuchElementException("Member not found: id=" + id);
        }
        memberRepository.deleteById(id);
    }
}
