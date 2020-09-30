package com.example.lecture.service;


import com.example.lecture.entity.Volunteer;

import java.util.List;

public interface VolunteerService {
    public void register(Volunteer volunteer) throws Exception;
    public List<Volunteer> list() throws Exception;
}
