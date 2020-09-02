package com.example.lecture.repository;

import com.example.lecture.entity.HomeNews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeNewsRepository extends JpaRepository<HomeNews, Long> {
}
