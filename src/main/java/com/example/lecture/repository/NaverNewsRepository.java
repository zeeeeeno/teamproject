package com.example.lecture.repository;

import com.example.lecture.entity.NaverNews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NaverNewsRepository extends JpaRepository<NaverNews, Long> {
    public NaverNews findByNewsNo(String newsNo);
}
