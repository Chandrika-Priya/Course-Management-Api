package com.CourseManangement.course;

import com.CourseManangement.course.repository.Course;
import com.CourseManangement.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Integer id) {
            Course course = courseRepository.findById(id);
            return course;
    }

    public Course addCourse(Course course) {
        Course newCourse = new Course(course.getName(), course.getDescription());
        courseRepository.save(newCourse);
        return newCourse;
    }

    public Course updateCourse(Course course, Integer id) {
        Course updatedCourse = courseRepository.findById(id);
        updatedCourse.setName(course.getName());
        updatedCourse.setDescription(course.getDescription());
        updatedCourse.setUploadedAt(LocalDateTime.now().withNano(0).toString());

        courseRepository.save(updatedCourse);

        return updatedCourse;
    }

    public void deleteCourse(Integer id) {
        Course deletedCourse = courseRepository.findById(id);
        courseRepository.delete(deletedCourse);
    }

    public boolean getCourseByTitle(String name) {
        Course course= courseRepository.findByName(name);
        if(course == null) {
            return false;
        }
        return true;
    }
}
