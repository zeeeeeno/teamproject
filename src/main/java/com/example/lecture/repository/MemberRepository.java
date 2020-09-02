package com.example.lecture.repository;

import com.example.lecture.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public List<Member> findByUserId(String userId);
    public List<Member> findByUserNo(Long userNo);

    @Query("select m.userNo, m.userPw, m.userName, m.regDate from Member m")
    public List<Object[]> listAllMember();

    @Query("select m.userNo, m.userPw, m.userName, m.regDate from Member m where m.userNo = :user_no")
    public Member listMember(@Param("user_no") Long userNo);
}