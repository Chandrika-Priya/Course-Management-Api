package com.CourseManangement.course.controller;

import com.CourseManangement.course.service.CourseService;
import com.CourseManangement.course.repository.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @RequestMapping("/api/courses")
    public List<Course> getCourses() {
        return courseService.getAllCourses();
    }

    @RequestMapping(value = "/api/courses/{id}", produces = "application/json")
    public ResponseEntity<Object> getCourseById(@PathVariable Integer id) {
        Course course = courseService.getCourseById(id);
        if (course == null) {
            return new ResponseEntity<>("{\"error\":\"Course id = " + id + " not found\"}", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PostMapping(value = "/api/courses", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addCourse(@RequestBody Course course) {
        if (course.getName() == null) {
            return new ResponseEntity<>("{\"error\":\"Course title is required\"}", HttpStatus.BAD_REQUEST);
        }
        if (courseService.getCourseByTitle(course.getName())) {
            return new ResponseEntity<>("{\"error\":\"Course with title '" + course.getName() + "' already exists\"}", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(courseService.addCourse(course), HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/courses/{id}", produces = "application/json")
    public ResponseEntity<Object> updateCourse(@RequestBody Course course, @PathVariable Integer id) {
        if (courseService.getCourseById(id) == null) {
            return new ResponseEntity<>("{\"error\":\"Course with id = " + id + " not found\"}", HttpStatus.NOT_FOUND);
        }
        if (course.getName() == null) {
            return new ResponseEntity<>("{\"error\":\"Course title is required\"}", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(courseService.updateCourse(course, id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/courses/{id}", produces = "application/json")
    public ResponseEntity<Object> deleteCourse(@PathVariable Integer id) {
        if (courseService.getCourseById(id) == null) {
            return new ResponseEntity<>("{\"error\":\"Course with id = " + id + " not found\"}", HttpStatus.NOT_FOUND);
        }
        courseService.deleteCourse(id);
        return null;
    }
}
