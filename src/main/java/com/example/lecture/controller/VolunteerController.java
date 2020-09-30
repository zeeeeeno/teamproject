package com.example.lecture.controller;

import com.example.lecture.entity.Volunteer;
import com.example.lecture.service.VolunteerService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping("/volunteer")
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class VolunteerController {
    @Autowired
    VolunteerService service;

    @PostMapping("")
    public ResponseEntity<Volunteer> register(@Validated @RequestBody Volunteer volunteer)
            throws Exception {
        log.info("volunteer: " + volunteer);
        log.info("volunteer.getTitle(): " + volunteer.getTitle());

        service.register(volunteer);

        return new ResponseEntity<>(volunteer, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Volunteer>> list()
            throws Exception {
        log.info("list()");

        return new ResponseEntity<>(service.list(), HttpStatus.OK);
    }
}
