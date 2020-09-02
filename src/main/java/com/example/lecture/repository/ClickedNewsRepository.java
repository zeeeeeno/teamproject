package com.example.lecture.repository;

import com.example.lecture.entity.ClickedNews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClickedNewsRepository extends JpaRepository<ClickedNews, Long> {
    public ClickedNews findByClickedNewsNo(String clickedNewsNo);
}