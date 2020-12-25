package com.openforce.coursefinderweb.webcontroller;

import java.util.List;

import com.openforce.coursefinderweb.domain.Course;
import com.openforce.coursefinderweb.domain.Request;
import com.openforce.coursefinderweb.domaincontroller.CourseFinderController;
import com.openforce.coursefinderweb.repository.course.CSVCourseRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controlle web che intercetta la chiamata api.
 */
public class CourseFinderWeb {
    @PostMapping("/findcourse")
    public ResponseEntity<List<Course>> getCourses(@RequestBody Request request) {
        if (line.getOptions().length == 0) {
    
            controller.findAllCourses().forEach(course -> basePrinterFactory.createCoursePrinter().print(course));;
            // filtro per tutte e tre le opzioni contemporaneamente
        } else if (line.hasOption('i') && line.hasOption('y') && line.hasOption('l')) {
            int yearInput = Integer.parseInt(line.getOptionValue('y'));
            DateFormat format = new SimpleDateFormat("yyyy", Locale.ENGLISH);
            Date launchDateInput = format.parse(line.getOptionValue('l'));
    
            controller.filterByInstructorYearLaunchDate(line.getOptionValues('i'), yearInput, launchDateInput)
                    .forEach(course -> basePrinterFactory.createCoursePrinter().print(course));
            // filtro per instruttori e anno
        } else if (line.hasOption('i') && line.hasOption('y')) {
            int yearInput = Integer.parseInt(line.getOptionValue('y'));
    
            controller.filterByInstructorYear(line.getOptionValues('i'), yearInput)
                    .forEach(course -> basePrinterFactory.createCoursePrinter().print(course));
            // filtro per instruttori e anno solare
        } else if (line.hasOption('i') && line.hasOption('l')) {
            DateFormat format = new SimpleDateFormat("yyyy", Locale.ENGLISH);
            Date launchDateInput = format.parse(line.getOptionValue('l'));
    
            controller.filterByInstructorLaunchDate(line.getOptionValues('i'), launchDateInput)
                    .forEach(course -> basePrinterFactory.createCoursePrinter().print(course));
            // filtro per tutti i corsi, anno e anno solare
        } else if (line.hasOption('y') && line.hasOption('l')) {
    
            int yearInput = Integer.parseInt(line.getOptionValue('y'));
            DateFormat format = new SimpleDateFormat("yyyy", Locale.ENGLISH);
            Date launchDateInput = format.parse(line.getOptionValue('l'));
            controller.filterByYearLaunchDate(yearInput, launchDateInput)
                    .forEach(course -> basePrinterFactory.createCoursePrinter().print(course));
             // filtro per i soli instruttori       
        } else if (line.hasOption('i')) {
    
            controller.filterByInstructor(line.getOptionValues('i'))
                    .forEach(course -> basePrinterFactory.createCoursePrinter().print(course));
            // filtro per tutti i corsi e anno
        } else if (line.hasOption('y')) {
    
            int yearInput = Integer.parseInt(line.getOptionValue('y'));
            controller.filterByYear(yearInput)
                    .forEach(course -> basePrinterFactory.createCoursePrinter().print(course));
            // filtro per tutti i corsi e anno solare
        } else if (line.hasOption('l')) {
    
            DateFormat format = new SimpleDateFormat("yyyy", Locale.ENGLISH);
            Date launchDateInput = format.parse(line.getOptionValue('l'));
            controller.filterByLaunchDate(launchDateInput)
                    .forEach(course -> basePrinterFactory.createCoursePrinter().print(course));
        }        
    }

}
