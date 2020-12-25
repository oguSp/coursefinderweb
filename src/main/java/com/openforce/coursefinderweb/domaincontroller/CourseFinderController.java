package com.openforce.coursefinderweb.domaincontroller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.openforce.coursefinderweb.domain.Course;
import com.openforce.coursefinderweb.repository.course.ICourseRepository;

public class CourseFinderController {
    
    ICourseRepository courseRepository;

    public CourseFinderController(ICourseRepository repository){
        this. courseRepository = repository;
    }

    public List<Course> findAllCourses(){
        List<Course> courses = courseRepository.getAllCourses().stream().distinct().collect(Collectors.toList());
        return courses;
    }

    public List<Course> filterByInstructorYearLaunchDate(String[] instructorNames, int year, Date launchDate){
        List<Course> courses = courseRepository.findByInstructorNames(instructorNames).stream()
            .filter(course -> course.getCourseYear().getYear() == year
                && course.getCourseYear().getLaunchDate().getYear() == launchDate.getYear())
            .collect(Collectors.toList());
        return courses;
    }

    public List<Course> filterByInstructorYear(String[] instructorNames, int year){
        List<Course> courses = courseRepository.findByInstructorNames(instructorNames).stream()
            .filter(course -> course.getCourseYear().getYear() == year)
            .collect(Collectors.toList());
        return courses;
    }

    public List<Course> filterByInstructorLaunchDate(String[] instructorNames, Date launchDate){
        List<Course> courses = courseRepository.findByInstructorNames(instructorNames).stream()
            .filter(course -> course.getCourseYear().getLaunchDate().getYear() == launchDate.getYear())
            .collect(Collectors.toList());
        return courses;
    }

    public List<Course> filterByYearLaunchDate(int year, Date launchDate){
        List<Course> courses = courseRepository.getAllCourses().stream()
            .filter(course -> course.getCourseYear().getYear() == year
                && course.getCourseYear().getLaunchDate().getYear() == launchDate.getYear())
            .collect(Collectors.toList());
        return courses;
    }

    public List<Course> filterByInstructor(String[] instructorNames){
        List<Course> courses = courseRepository.findByInstructorNames(instructorNames).stream()
            .distinct()
            .collect(Collectors.toList());
        return courses;
    }

    public List<Course> filterByYear(int year){
        List<Course> courses = courseRepository.getAllCourses().stream()
            .filter(course -> course.getCourseYear().getYear() == year)
            .collect(Collectors.toList());
        return courses;
    }

    public List<Course> filterByLaunchDate(Date launchDate){
        List<Course> courses = courseRepository.getAllCourses().stream()
            .filter(course -> course.getCourseYear().getLaunchDate().getYear() == launchDate.getYear())
            .collect(Collectors.toList());
        return courses;
    }
}