package com.example.lecture.service;

import com.example.lecture.entity.MemberAuth;
import com.example.lecture.repository.MemberAuthRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log
@Service
public class MemberAuthService {
    @Autowired
    MemberAuthRepository authRepository;

    public MemberAuth read(Long userNo) throws Exception {
        log.info("MemberAuth Service read()");

        return authRepository.checkAuth(userNo);
    }
}

