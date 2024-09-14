package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration
public class RouteControllerUnitTests {

    private MyFileDatabase fileDatabaseMock;

    @BeforeEach
    public void setupCourseForTesting() {
        fileDatabaseMock = mock(MyFileDatabase.class);
        IndividualProjectApplication.overrideDatabase(fileDatabaseMock);

        String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
        String[] locations = {"417 IAB", "309 HAV", "301 URIS"};
        Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
        coms1004.setEnrolledStudentCount(249);
        Course coms3134 = new Course("Brian Borowski", locations[2], times[1], 250);
        coms3134.setEnrolledStudentCount(250);
        HashMap<String, Course> courses = new HashMap<>();
        courses.put("1004", coms1004);
        courses.put("3134", coms3134);
        Department comSci = new Department("COMS", courses, "Luca Carloni", 2700);
        HashMap<String, Department> mapping = new HashMap<>();
        mapping.put("COMS", comSci);

        when(fileDatabaseMock.getDepartmentMapping()).thenReturn(mapping);
        testRouteController = new RouteController();
    }

    @Test
    public void retrieveDepartmentDepartmentNotFoundTest() {
        ResponseEntity<?> responseEntity = testRouteController.retrieveDepartment("COMS1");
        assertEquals("Department Not Found", responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void retrieveDepartmentDepartmentFoundTest() {
        String expectedBody = "COMS 1004: \n" +
                "Instructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55\n" +
                "COMS 3134: \n" +
                "Instructor: Brian Borowski; Location: 301 URIS; Time: 4:10-5:25\n";

        ResponseEntity<?> responseEntity = testRouteController.retrieveDepartment("COMS");
        assertEquals(expectedBody, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void retrieveCourseCourseNotFoundTest() {
        ResponseEntity<?> responseEntity = testRouteController.retrieveCourse("COMS", 1005);
        assertEquals("Course Not Found", responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void retrieveCourseDepartmentNotFoundTest() {
        ResponseEntity<?> responseEntity = testRouteController.retrieveCourse("COMS1", 1004);
        assertEquals("Department Not Found", responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void retrieveCourseCourseFoundTest() {
        ResponseEntity<?> responseEntity = testRouteController.retrieveCourse("COMS", 1004);
        String expectedResponse = "\n" +
                "Instructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55";
        assertEquals(expectedResponse, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void isCourseFullCourseNotFoundTest() {
        ResponseEntity<?> responseEntity = testRouteController.isCourseFull("COMS", 1005);
        assertEquals("Course Not Found", responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void isCourseFullCourseFoundNotFullTest() {
        ResponseEntity<?> responseEntity = testRouteController.isCourseFull("COMS", 1004);
        assertEquals(false, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void isCourseFullCourseFoundFullTest() {
        ResponseEntity<?> responseEntity = testRouteController.isCourseFull("COMS", 3134);
        assertEquals(true, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getMajorCtFromDeptDepartmentNotFoundTest() {
        ResponseEntity<?> responseEntity = testRouteController.getMajorCtFromDept("COMS1");
        assertEquals("Department Not Found", responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void getMajorCtFromDeptSuccessTest() {
        ResponseEntity<?> responseEntity = testRouteController.getMajorCtFromDept("COMS");
        assertEquals("There are: 2700 majors in the department", responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void identifyDeptChairDepartmentNotFoundTest() {
        ResponseEntity<?> responseEntity = testRouteController.identifyDeptChair("COMS1");
        assertEquals("Department Not Found", responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void identifyDeptChairSuccessTest() {
        ResponseEntity<?> responseEntity = testRouteController.identifyDeptChair("COMS");
        assertEquals("Luca Carloni is the department chair.", responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void findCourseLocationCourseNotFoundTest() {
        ResponseEntity<?> responseEntity = testRouteController.findCourseLocation("COMS", 1005);
        assertEquals("Course Not Found", responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void findCourseLocationSuccessTest() {
        ResponseEntity<?> responseEntity = testRouteController.findCourseLocation("COMS", 1004);
        assertEquals("417 IAB is where the course is located.", responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void findCourseInstructorCourseNotFoundTest() {
        ResponseEntity<?> responseEntity = testRouteController.findCourseInstructor("COMS", 1005);
        assertEquals("Course Not Found", responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void findCourseInstructorSuccessTest() {
        ResponseEntity<?> responseEntity = testRouteController.findCourseInstructor("COMS", 1004);
        assertEquals("Adam Cannon is the instructor for the course.", responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void findCourseTimeCourseNotFoundTest() {
        ResponseEntity<?> responseEntity = testRouteController.findCourseTime("COMS", 1005);
        assertEquals("Course Not Found", responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void findCourseTimeSuccessTest() {
        ResponseEntity<?> responseEntity = testRouteController.findCourseTime("COMS", 1004);
        assertEquals("The course meets at: 11:40-12:55", responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void addMajorToDeptDepartmentNotFoundTest() {
        ResponseEntity<?> responseEntity = testRouteController.addMajorToDept("COMS1");
        assertEquals("Department Not Found", responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void addMajorToDeptSuccessTest() {
        ResponseEntity<?> responseEntity = testRouteController.addMajorToDept("COMS");
        assertEquals("Attribute was updated successfully", responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    public static RouteController testRouteController;
}
