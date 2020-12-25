package com.openforce.coursefinderweb.repository.course;

import java.util.List;

import com.openforce.coursefinderweb.domain.Course;

public interface ICourseRepository {
    
    List<Course> findByInstructorNames(String[] instructorNames);
    List<Course> getAllCourses();
}