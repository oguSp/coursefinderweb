package com.openforce.coursefinderweb.webcontroller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.openforce.coursefinderweb.domain.Course;
import com.openforce.coursefinderweb.domain.Request;
import com.openforce.coursefinderweb.domaincontroller.CourseFinderController;
import com.openforce.coursefinderweb.repository.course.CSVCourseRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller web che intercetta la chiamata api.
 */
@RestController
public class CourseFinderWeb {
    @PostMapping(value = "/api/coursefinder/findcourse", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Course>> getCourses(@RequestBody(required = true) Map<String,String> request) {
        try{
         CourseFinderController domainController = new CourseFinderController(new CSVCourseRepository("harvardMIT.csv"));
         // Map<String,String> request = request.getrequest();
         List<Course> courses = new ArrayList<>();
         // filtro per tutti i corsi
         if (request.containsKey("all")) {
             courses = domainController.findAllCourses();
             return ResponseEntity.ok(courses);
             // filtro per tutte e tre le opzioni contemporaneamente
         } else if (request.containsKey("i") && request.containsKey("y") && request.containsKey("l")) {
             int yearInput = Integer.parseInt(request.get("y"));
             DateFormat format = new SimpleDateFormat("yyyy", Locale.ENGLISH);
             Date launchDateInput = format.parse(request.get("l"));
             courses = domainController.filterByInstructorYearLaunchDate(request.get("i").split(","), yearInput, launchDateInput);
             return ResponseEntity.ok(courses);
             // filtro per instruttori e anno
         } else if (request.containsKey("i") && request.containsKey("y")) {
             int yearInput = Integer.parseInt(request.get("y"));
             courses = domainController.filterByInstructorYear(request.get("i").split(","), yearInput);
             return ResponseEntity.ok(courses);
             // filtro per instruttori e anno solare
         } else if (request.containsKey("i") && request.containsKey("l")) {
             DateFormat format = new SimpleDateFormat("yyyy", Locale.ENGLISH);
             Date launchDateInput = format.parse(request.get("l"));
             courses = domainController.filterByInstructorLaunchDate(request.get("i").split(","), launchDateInput);
             return ResponseEntity.ok(courses);
             // filtro per tutti i corsi, anno e anno solare
         } else if (request.containsKey("y") && request.containsKey("l")) {
             int yearInput = Integer.parseInt(request.get("y"));
             DateFormat format = new SimpleDateFormat("yyyy", Locale.ENGLISH);
             Date launchDateInput = format.parse(request.get("l"));
             courses = domainController.filterByYearLaunchDate(yearInput, launchDateInput);
             return ResponseEntity.ok(courses);
              // filtro per i soli instruttori       
         } else if (request.containsKey("i")) {
             courses = domainController.filterByInstructor(request.get("i").split(","));
             return ResponseEntity.ok(courses);
             // filtro per tutti i corsi e anno
         } else if (request.containsKey("y")) {
             int yearInput = Integer.parseInt(request.get("y"));
             courses = domainController.filterByYear(yearInput);
             return ResponseEntity.ok(courses);
             // filtro per tutti i corsi e anno solare
         } else if (request.containsKey("l")) {
             DateFormat format = new SimpleDateFormat("yyyy", Locale.ENGLISH);
             Date launchDateInput = format.parse(request.get("l"));
             courses = domainController.filterByLaunchDate(launchDateInput);
             return ResponseEntity.ok(courses);
         }
         // se vuoto, ritorno not found
         if (courses.size() == 0){
            return ResponseEntity.notFound().build();
         }
         return ResponseEntity.noContent().build();
        } catch (java.text.ParseException e) {
            // Stampa errore nel caso di data passata in forma non corretta
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data non corretta", e);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Errore durante la lettura del file .csv", e);
        }     
    }
}