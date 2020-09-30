package com.example.lecture.service;

import com.example.lecture.entity.Volunteer;
import com.example.lecture.repository.VolunteerRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class VolunteerServiceImpl implements VolunteerService {

    @Autowired
    VolunteerRepository repository;

    @Override
    public void register(Volunteer volunteer) throws Exception {
        log.info("register() - volunteer: " + volunteer);

        Volunteer volunteerEntity = new Volunteer();

        volunteerEntity.setTitle(volunteer.getTitle());
        volunteerEntity.setCategory(volunteer.getCategory());
        volunteerEntity.setPersonNum(volunteer.getPersonNum());
        volunteerEntity.setContents(volunteer.getContents());
        volunteerEntity.setDate(volunteer.getDate());
        volunteerEntity.setTime(volunteer.getTime());

        repository.save(volunteerEntity);
    }

    @Override
    public List<Volunteer> list() throws Exception {
        return repository.findAll();
    }


}
