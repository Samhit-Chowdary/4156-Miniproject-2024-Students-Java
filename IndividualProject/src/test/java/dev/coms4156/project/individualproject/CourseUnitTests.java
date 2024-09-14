package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Represents a test suite for the Course class which will test all functionalities
 * and will include all possible edge cases.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }


  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void isCourseFullClassNotFullTest() {
    testCourse.setEnrolledStudentCount(200);
    assertFalse(testCourse.isCourseFull());
  }

  @Test
  public void isCourseFullClassFullTest() {
    testCourse.setEnrolledStudentCount(250);
    assertTrue(testCourse.isCourseFull());
  }

  @Test
  public void enrollStudentSuccessTest() {
    assertTrue(testCourse.enrollStudent());
  }

  @Test
  public void enrollStudentClassFullTest() {
    testCourse.setEnrolledStudentCount(250);
    assertFalse(testCourse.enrollStudent());
  }

  @Test
  public void dropStudentSuccessTest() {
    testCourse.setEnrolledStudentCount(250);
    assertTrue(testCourse.dropStudent());
    assertEquals(249, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void dropStudentClassEmptyTest() {
    testCourse.setEnrolledStudentCount(0);
    assertFalse(testCourse.dropStudent());
    assertEquals(0, testCourse.getEnrolledStudentCount());
  }

  @AfterEach
  public void restoreDefaultValues() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}

