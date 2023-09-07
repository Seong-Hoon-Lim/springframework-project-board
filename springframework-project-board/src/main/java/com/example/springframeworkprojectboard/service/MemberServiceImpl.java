package com.example.springframeworkprojectboard.service;

import com.example.springframeworkprojectboard.config.util.PasswordEncryptionUtil;
import com.example.springframeworkprojectboard.domain.Member;
import com.example.springframeworkprojectboard.dto.MemberDto;
import com.example.springframeworkprojectboard.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final ModelMapper mapper;
    private final MemberMapper memberMapper;

    @Override
    public boolean hasDuplicateMember(String account) throws Exception {
        log.info("MemberService: hasDuplicateMember()");
        return memberMapper.isExistByAccount(account);
    }

    @Override
    public void registerMember(MemberDto memberDto) throws Exception {
        log.info("MemberService: registerMember()");
        memberDto.setPassword(PasswordEncryptionUtil.encrypt(memberDto.getPassword()));
        Member member = mapper.map(memberDto, Member.class);
        memberMapper.save(member);
    }

    @Override
    public MemberDto getMember(long memberId) throws Exception {
        log.info("MemberService: getMember(memberId)");
        Member member = memberMapper.findMemberById(memberId);
        return mapper.map(member, MemberDto.class);
    }

    @Override
    public MemberDto authenticateMember(String account, String rawPassword) throws Exception {
        log.info("MemberService: authenticateMember()");
        Member member = memberMapper.findMemberByAccount(account);

        if (member != null && PasswordEncryptionUtil.matches(rawPassword, member.getPassword())) {
            return mapper.map(member, MemberDto.class);
        } else {
            return null;
        }
    }

    @Override
    public void modifyMember(MemberDto memberDto) throws Exception {
        log.info("MemberService: modifyMember()");
        memberDto.setPassword(PasswordEncryptionUtil.encrypt(memberDto.getPassword()));
        Member member = mapper.map(memberDto, Member.class);
        memberMapper.update(member);
    }

    @Override
    public void removeMember(long memberId) throws Exception {
        log.info("MemberService: removeMember()");
        memberMapper.deleteMemberByMemberId(memberId);
    }
}
