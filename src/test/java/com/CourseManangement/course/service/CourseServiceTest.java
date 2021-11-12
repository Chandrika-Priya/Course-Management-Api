package com.CourseManangement.course.service;

import com.CourseManangement.course.repository.Course;
import com.CourseManangement.course.repository.CourseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(CourseService.class)
public class CourseServiceTest {
    @Autowired
    private CourseService courseService;

    @MockBean
    private CourseRepository courseRepository;

    Course courseOne = new Course("API", "course description");
    Course courseTwo = new Course("Spring", "Spring description");

    @Test
    void shouldGetAllCourses() {
        List<Course> courses = new ArrayList<>(Arrays.asList(
                courseOne, courseTwo
        ));
        when(courseRepository.findAll()).thenReturn(courses);

        Assertions.assertEquals(courses, courseService.getAllCourses());
    }

    @Test
    void shouldGetCourseById() {
        when(courseRepository.findById(Mockito.anyInt())).thenReturn(courseOne);

        Assertions.assertEquals(courseOne, courseService.getCourseById(1));
    }

    @Test
    void shouldAddCourse() {
        when(courseRepository.save(any())).thenReturn(courseOne);

        Assertions.assertEquals(courseOne.getName(), courseService.addCourse(courseOne).getName());
    }

    @Test
    void shouldUpdateCourse() {
        when(courseRepository.findById(Mockito.anyInt())).thenReturn(courseOne);

        Assertions.assertEquals(LocalDateTime.now().withNano(0).toString(), courseService.updateCourse(courseOne, 1).getUploadedAt());
    }

    @Test
    void shouldDeleteCourse() {
        courseOne.setId(1);
        courseService.deleteCourse(1);
        when(courseRepository.findById(anyInt())).thenReturn(courseOne);

        verify(courseRepository).delete(courseRepository.findById(1));
    }

    @Test
    void shouldGetCourseByTitle() {
        when(courseRepository.findByName(anyString())).thenReturn(courseOne);

        Assertions.assertEquals(true, courseService.getCourseByTitle("API"));
    }
}
