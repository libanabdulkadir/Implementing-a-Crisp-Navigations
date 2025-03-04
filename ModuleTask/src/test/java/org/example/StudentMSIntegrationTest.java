
 package org.example;
 import org.junit.jupiter.api.Test;
 import javax.swing.*;
 import java.util.Arrays;
 import java.util.List;
 import static org.junit.jupiter.api.Assertions.*;

     class StudentMSIntegrationTest {

      @Test
      void testStudentMSIntegration() {
          StudentMS studentMS = new StudentMS();

          assertAll("Frame properties",
                  () -> assertNotNull(studentMS.frame),
                  () -> assertEquals("STUDENT MS", studentMS.frame.getTitle()),
                  () -> assertEquals(450, studentMS.frame.getWidth()),
                  () -> assertEquals(600, studentMS.frame.getHeight())
          );

          studentMS.createUI();
          assertNotNull(studentMS.frame);

          JPanel mainMenu = studentMS.createMainMenu();
          assertNotNull(mainMenu);
          assertEquals(3, mainMenu.getComponentCount());

          JPanel inputPanel = studentMS.createInputPanel();
          assertNotNull(inputPanel);

          JPanel inputFormControl = (JPanel) inputPanel.getComponent(0);
          assertEquals(2, inputFormControl.getComponentCount());

          JPanel formInput = (JPanel) inputFormControl.getComponent(0);
          assertEquals(10, formInput.getComponentCount());

          JPanel buttonPanel = (JPanel) inputFormControl.getComponent(1);
          assertTrue(buttonPanel.getComponentCount() >= 3);

          JPanel viewPanel = studentMS.createViewPanel();
          assertNotNull(viewPanel);
          assertTrue(viewPanel instanceof JPanel);
          assertNotNull(studentMS.displayArea);

          studentMS.studentRecords.add("Name: ALi\nCourse: Computer Science\nGender: Male\nBirth Date: 2000-01-01\nAddress: Kampala");

          assertDoesNotThrow(studentMS::updateStudentList);
          String updatedText = studentMS.displayArea.getText().trim();

          List<String> expectedLines = List.of(
                  "Student Records:",
                  "",
                  "Name: ALi",
                  "Course: Computer Science",
                  "Gender: Male",
                  "Birth Date: 2000-01-01",
                  "Address: Kampala",
                  "-".repeat(50)
          );

          assertLinesMatch(expectedLines, Arrays.asList(updatedText.split("\n")));

          studentMS.studentRecords = null;
          assertThrowsExactly(NullPointerException.class, studentMS::updateStudentList,
                  "Expected NullPointerException when studentRecords is null");


          studentMS.toggleDarkMode();
          assertDoesNotThrow(studentMS::toggleDarkMode);
          assertNotNull(studentMS.frame.getContentPane());


      }
  }
