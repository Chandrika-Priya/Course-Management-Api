package com.CourseManangement.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findById(Integer id);

    Course findByName(String name);
}
