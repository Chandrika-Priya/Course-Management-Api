package com.CourseManangement.course;

import com.CourseManangement.course.repository.Course;
import com.CourseManangement.course.view.CourseController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CourseController.class)
public class CourseControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    CourseService courseService;

    Course courseOne = new Course("API", "course description");
    Course courseTwo = new Course("Spring", "Spring description");

    @Test
    void shouldGetAllCourses() throws Exception {
        List<Course> courses = new ArrayList<>(Arrays.asList(
                courseOne, courseTwo
        ));

        when(courseService.getAllCourses()).thenReturn(courses);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldGetCourseById() throws Exception {
        when(courseService.getCourseById(1)).thenReturn(courseOne);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses/1")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("API")));
    }

    @Test
    void shouldCreateCourse() throws Exception {
        Course course = new Course("React", "React description");
        when(courseService.addCourse(Mockito.any())).thenReturn(course);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/courses")
                .content(this.mapper.writeValueAsString(course))
                .contentType("application/json")
                .accept("application/json");
        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt", is(LocalDateTime.now().withNano(0).toString())));
    }

    @Test
    void shouldUpdateCourse() throws Exception {
        Course course = new Course("React", "React description");
        when(courseService.getCourseById(anyInt())).thenReturn(course);
        course.setUploadedAt(LocalDateTime.now().withNano(0).toString());
        when(courseService.updateCourse(Mockito.any(), anyInt())).thenReturn(course);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/courses/1")
                .content(this.mapper.writeValueAsString(course))
                .contentType("application/json")
                .accept("application/json");
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uploadedAt", is(LocalDateTime.now().withNano(0).toString())));
    }

    @Test
    void shouldDeleteCourse() throws Exception {
        Course course = new Course("xyz", "this is test");
        when(courseService.getCourseById(anyInt())).thenReturn(course);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/api/courses/1");
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }
}

