package com.openforce.coursefinderweb.repository.course;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.openforce.coursefinderweb.domain.Course;
import com.openforce.coursefinderweb.domain.CourseYear;
import com.openforce.coursefinderweb.domain.Institution;
import com.openforce.coursefinderweb.domain.Instructor;

/**
 * Repository per operazioni sui corsi su file csv.
 */
public class CSVCourseRepository implements ICourseRepository {

    private Reader input;

    public CSVCourseRepository(String filePath) throws IOException {
        Resource resource = new ClassPathResource(filePath);
        this.input = new FileReader(resource.getFile());
    }

    @Override
    public List<Course> findByInstructorNames(String[] instructorNames) {
        try {
            List<Course> courses = new ArrayList<>();
            List<String> instructorsNamesAsList = Arrays.asList(instructorNames);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(input);
            for (CSVRecord record : records) {
                String[] csvInstructorNames = record.get("Instructors").split(", ");
                List<String> csvInstructorsNamesAsList = Arrays.asList(csvInstructorNames);
                for (String csvInstructorName : csvInstructorsNamesAsList) {
                    if (instructorsNamesAsList.contains(csvInstructorName)){
                        Course course = mapCourse(record.get("Course Number"), record.get("Course Title"), record.get("Year"), record.get("Launch Date"), csvInstructorsNamesAsList, record.get("Institution"));
                        courses.add(course);
                        break; //Se ho trovato anche solo uno degli insegnanti creo il corso senza controllare l'altro.
                    }
                }
            }
            return courses;
        }
        catch (Exception e) {
            System.err.println("Parsing error");
            return null;
        }
    }

    @Override
    public List<Course> getAllCourses() {
        try {
            List<Course> courses = new ArrayList<>();
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(input);
            for (CSVRecord record : records) {
                String[] csvInstructorNames = record.get("Instructors").split(", ");
                List<String> csvInstructorsNamesAsList = Arrays.asList(csvInstructorNames);
                Course course = mapCourse(record.get("Course Number"), record.get("Course Title"), record.get("Year"), record.get("Launch Date"), csvInstructorsNamesAsList, record.get("Institution"));
                courses.add(course);
            }
            return courses;
        } catch (Exception e) {
            System.err.println("Parsing error");
            return null;
        }
    }

    private Course mapCourse(String courseNumber, String courseTitle, String year, String date, List<String> instructorsNames, String csvInstitution )
            throws ParseException {
        DateFormat format = new SimpleDateFormat("MM/d/yyyy", Locale.ENGLISH);
        Date launchDate = format.parse(date);
        CourseYear courseYear = new CourseYear(Integer.parseInt(year), launchDate);
        Institution institution =new Institution(csvInstitution);
        List<Instructor> instructors = new ArrayList<>();
        for (String instructorName : instructorsNames) {
            instructors.add(new Instructor(instructorName));
        }
        return new Course(courseNumber, courseTitle, courseYear, instructors, institution);
    }
}