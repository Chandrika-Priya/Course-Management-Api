package com.CourseManangement.course.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CourseRepositoryTest {
    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void shouldReturnCourseWhenFindByNameIsCalled() {
        Course course = new Course("API", "API Description");
        courseRepository.save(course);

        Course resultCourse = courseRepository.findByName("API");

        assertThat(resultCourse.getName()).isEqualTo("API");
    }

    @Test
    void shouldReturnCourseWhenFindByIdIsCalled() {
        Course course = new Course("Spring", "API Description");
        courseRepository.save(course);

        Course resultCourse = courseRepository.findById(2);

        assertThat(resultCourse.getName()).isEqualTo("Spring");
    }
}
