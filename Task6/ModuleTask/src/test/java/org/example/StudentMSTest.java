package org.example;

import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

  class StudentMSTest {
      @Test
      void MainFrame() {
          StudentMS studentMS = new StudentMS();
          studentMS.mainFrame();
          JFrame frame = studentMS.frame;
          assertAll("Main Frame",
                  () -> assertNotNull(frame),
                  () -> assertEquals("STUDENT MS", frame.getTitle()),
                  () -> assertEquals(450, frame.getWidth()),
                  () -> assertEquals(600, frame.getHeight())
          );

      }

      @Test
      void CreateUI() {
          StudentMS studentMS = new StudentMS();
          studentMS.createUI();
          assertAll("UI components",
                  () -> assertNotNull(studentMS.cardPanel),
                  () -> assertEquals(3, studentMS.cardPanel.getComponentCount())
          );
      }


      @Test
      void CreateMainMenu() {
          StudentMS studentMS = new StudentMS();
          JPanel mainMenu = studentMS.createMainMenu();

          assertAll("Main menu checks",
                  () -> assertNotNull(mainMenu),
                  () -> assertEquals(3, mainMenu.getComponentCount())
          );
      }


      @Test
      void CreateInputPanel() {
          StudentMS studentMS = new StudentMS();
          JPanel inputPanel = studentMS.createInputPanel();

          assertNotNull(inputPanel);

          JPanel inputFormControl = (JPanel) inputPanel.getComponent(0);
          assertEquals(2, inputFormControl.getComponentCount());

          JPanel formInput = (JPanel) inputFormControl.getComponent(0);
          assertEquals(10, formInput.getComponentCount());

          JPanel buttonPanel = (JPanel) inputFormControl.getComponent(1);
          assertTrue(buttonPanel.getComponentCount() >= 3);
      }


      @Test
      void CreateViewPanel() {
          StudentMS studentMS = new StudentMS();
          JPanel viewPanel = studentMS.createViewPanel();
          assertAll("View panel checks",
                  () -> assertNotNull(viewPanel),
                  () -> assertTrue(viewPanel instanceof JPanel),
                  () -> assertNotNull(studentMS.displayArea)
          );
      }
      @Test
      void UpdateStudentList() {
          StudentMS studentMS = new StudentMS();
          studentMS.createUI();
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
          assertThrowsExactly(NullPointerException.class, studentMS::updateStudentList, "Expected NullPointerException when studentRecords is null");
      }


      @Test
      void ToggleDarkMode() {
          StudentMS studentMS = new StudentMS();
          studentMS.toggleDarkMode();
          assertDoesNotThrow(studentMS::toggleDarkMode);
          assertNotNull(studentMS.frame.getContentPane());
      }



  }
