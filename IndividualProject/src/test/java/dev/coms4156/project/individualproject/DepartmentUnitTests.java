package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

    @BeforeAll
    public static void setupCourseForTesting() {
        HashMap<String, Course> courses = new HashMap<String, Course>();
        courses.put("Software Engineering", new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250));
        courses.put("Adv Software Engineering", new Course("Peter Lose", "418 IAB", "12:30-13:55", 250));
        testDepartment = new Department("COMS", courses, "Samhit Bhogavalli", 1);
    }

    @Test
    public void toStringTest() {
        String expectedResult =
            "COMS Adv Software Engineering: \n"
                + "Instructor: Peter Lose; Location: 418 IAB; Time: 12:30-13:55\n"
                + "COMS Software Engineering: \n"
                + "Instructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n";
        assertEquals(expectedResult, testDepartment.toString());
    }

    @Test
    public void dropPersonFromMajorTest() {
        testDepartment.dropPersonFromMajor();
        assertEquals(0, testDepartment.getNumberOfMajors());
        testDepartment.dropPersonFromMajor();
        assertEquals(0, testDepartment.getNumberOfMajors());
    }

    public static Department testDepartment;
}
