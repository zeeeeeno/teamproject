package com.example.lecture.service;

import com.example.lecture.entity.Member;

import java.util.List;

public interface MemberService {
    public void register(Member member) throws Exception;
    public void setupAdmin(Member member) throws Exception;

    public List<Member> read(Long userNo) throws Exception;
    public void modify(Member member) throws Exception;
    public void remove(Long userNo) throws Exception;
    public List<Member> list() throws Exception;
    public long countAll() throws Exception;
}
