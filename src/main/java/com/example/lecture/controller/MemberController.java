package com.example.lecture.controller;

import com.example.lecture.entity.Member;
import com.example.lecture.entity.MemberAuth;
import com.example.lecture.security.AuthUtil;
import com.example.lecture.service.MemberAuthService;
import com.example.lecture.service.MemberService;
import lombok.extern.java.Log;
import org.h2.util.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@Log
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class MemberController {
    @Autowired
    private MemberService service;

    @Autowired
    private MemberAuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MessageSource messageSource;

    @PostMapping("")
    public ResponseEntity<Member> register(@Validated @RequestBody Member member)
            throws Exception {
        log.info("member.getUserName(): " + member.getUserName());
        log.info("member: " + member);

        String inputPassword = member.getUserPw();
        member.setUserPw(passwordEncoder.encode(inputPassword));

        service.register(member);

        log.info("register member.getUserNo(): " + member.getUserNo());

        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<Member>> list() throws Exception {
        return new ResponseEntity<>(service.list(), HttpStatus.OK);
    }

    @GetMapping("/{userNo}")
    public ResponseEntity<List<Member>> read(@PathVariable("userNo") Long userNo) throws Exception {
        log.info("read() - userNo: " + userNo);
        List<Member> member = service.read(userNo);

        log.info("member: " + member);

        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{userNo}")
    public ResponseEntity<Void> remove(@PathVariable("userNo") String userNo) throws Exception {
        log.info("remove - userNo: " + userNo);
        long userNoLong = Long.parseLong(userNo);
        service.remove(userNoLong);

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{userNo}")
    public ResponseEntity<Member> modify(@PathVariable("userNo") Long userNo,
                                         @Validated @RequestBody Member member)
            throws Exception {
        log.info("modify - member: " + member);

        log.info("modify - userNo: " + userNo);

        member.setUserNo(userNo);
        service.modify(member);

        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @RequestMapping(value = "/setup",
            method = RequestMethod.POST,
            produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> setupAdmin(@Validated @RequestBody Member member)
            throws Exception {
        log.info("setupAdmin: member.getUserName(): " + member.getUserName());
        log.info("setupAdmin: service.countAll(): " + service.countAll());

        if (service.countAll() == 0) {
            String inputPassword = member.getUserPw();
            member.setUserPw(passwordEncoder.encode(inputPassword));
            member.setRole("Admin");
            service.setupAdmin(member);

            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }

        String message = messageSource.getMessage("common.cannotSetupAdmin",
                null, Locale.KOREAN);

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/myinfo")
    public ResponseEntity<MemberAuth> getMyInfo(
            @RequestHeader (name="Authorization") String header) throws Exception {
        Long userNo = AuthUtil.getUserNo(header);
        log.info("register userNo: " + userNo);

        MemberAuth auth = authService.read(userNo);
        log.info("auth: " + auth);

        return new ResponseEntity<>(auth, HttpStatus.OK);
    }

    @PostMapping("checkId")
    public ResponseEntity<String> checkId(@Validated @RequestBody String userId) throws Exception {
        log.info("checkId() - userId: " + userId);
        String[] userIdArr = userId.split(":");
        log.info("userIdArr[1] : " + userIdArr[1]);
        String userIdString1 = userIdArr[1].replace("\"", "");
        log.info("userIdString1 : " + userIdString1);
        String userIdString2 = userIdString1.replace("}", "");
        log.info("userIdString2 : " + userIdString2);


        if (service.checkId(userIdString2) == false) {
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }

        String message = messageSource.getMessage("common.cannotCheckUserId",
                null, Locale.KOREAN);

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}