package com.example.lecture.service;

import com.example.lecture.entity.Member;
import com.example.lecture.entity.MemberAuth;
import com.example.lecture.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    static final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Autowired
    private MemberRepository repository;

    @Override
    public void register(Member member) throws Exception {
        log.info("member: " + member);

        Member memEntity = new Member();

        memEntity.setUserId(member.getUserId());
        memEntity.setUserPw(member.getUserPw());
        memEntity.setUserName(member.getUserName());
        memEntity.setSex(member.getSex());
        memEntity.setRole(member.getRole());
        memEntity.setAddress(member.getAddress());

        MemberAuth memberAuth = new MemberAuth();
        memberAuth.setAuth("ROLE_MEMBER");

        memEntity.addAuth(memberAuth);

        repository.save(memEntity);

        member.setUserNo(memEntity.getUserNo());
    }

    @Override
    public void setupAdmin(Member member) throws Exception {
        Member memEntity = new Member();
        memEntity.setUserId(member.getUserId());
        memEntity.setUserPw(member.getUserPw());
        memEntity.setUserName(member.getUserName());
        memEntity.setSex(member.getSex());
        memEntity.setRole(member.getRole());
        memEntity.setAddress(member.getAddress());

        MemberAuth memberAuth = new MemberAuth();
        memberAuth.setAuth("ROLE_ADMIN");

        memEntity.addAuth(memberAuth);

        repository.save(memEntity);
    }

    @Override
    public List<Member> read(Long userNo) throws Exception {
        log.info("Member Service read(): " + userNo);
        log.info("String.valueOf(userNo): " + String.valueOf(userNo));

        return repository.findByUserNo(userNo);
    }

    @Override
    public void modify(Member member) throws Exception {
        log.info("MemberServiceImpl - modify: " + member.getUserNo());
        List<Member> memEntity = repository.findByUserNo(member.getUserNo());
        log.info("memEntity - " + memEntity);
        log.info("memEntity - " + memEntity.get(0).getSex());
        Member mem = memEntity.get(0);
        mem.setUserName(member.getUserName());
        mem.setAddress(member.getAddress());

        List<MemberAuth> memberAuthList = mem.getAuthList();
        List<MemberAuth> authList = member.getAuthList();

        for(int i = 0; i < authList.size(); i++) {
            MemberAuth auth = authList.get(i);

            if(i < memberAuthList.size()) {
                MemberAuth memberAuth = memberAuthList.get(i);
                memberAuth.setAuth(auth.getAuth());
            }
        }

        repository.save(mem);
    }

    @Override
    public void remove(Long userNo) throws Exception {
        repository.deleteById(userNo);
    }

    @Override
    public List<Member> list() throws Exception {
        List<Object[]> valArrays = repository.listAllMember();
        List<Member> memberList = new ArrayList<Member>();

        for(Object[] valArr : valArrays) {
            Member mem = new Member();

            mem.setUserNo((Long) valArr[0]);
            mem.setUserId((String) valArr[1]);
            mem.setUserPw((String) valArr[2]);
            mem.setUserName((String) valArr[3]);
            mem.setSex((String) valArr[4]);
            mem.setRole((String) valArr[5]);
            mem.setAddress((String) valArr[6]);
            mem.setRegDate((Date) valArr[7]);

            memberList.add(mem);
        }

        return memberList;
    }

    @Override
    public long countAll() throws Exception {
        return repository.count();
    }

    @Override
    public boolean checkId(String userId) throws Exception {
        log.info("checkId() - userId: " + userId);
        List<Member> memEntity = repository.findByUserId(userId);
        log.info("memEntity: " + memEntity);

        if (!memEntity.isEmpty()) {
            log.info("userId가 존재한다.");
            return true;
        }

        return false;
    }


}