   package org.example;

   import com.formdev.flatlaf.FlatDarkLaf;
   import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
   import com.toedter.calendar.JDateChooser;

   import javax.swing.*;
   import java.awt.*;
   import java.text.SimpleDateFormat;
   import java.util.ArrayList;

   public class StudentMS {

       JFrame frame;
       JPanel cardPanel;
       JLabel titleLabel;
       CardLayout cardLayout;
       JTextField nameField;
       JTextArea displayArea;
       ArrayList<String> studentRecords;
       JToggleButton darkModeToggle;

       StudentMS() {

           try {
               UIManager.setLookAndFeel(new AcrylLookAndFeel());
           } catch (Exception e) {
               e.printStackTrace();
           }
           studentRecords = new ArrayList<>();
           mainFrame();
           createUI();
       }

       public void mainFrame() {
           frame = new JFrame();
           frame.setTitle("STUDENT MS");
           frame.setSize(450, 600);
           frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
           frame.setLocationRelativeTo(null);
       }

        void createUI() {
           cardLayout = new CardLayout();
           cardPanel = new JPanel(cardLayout);
           cardPanel.add(createMainMenu(), "main");
           cardPanel.add(createInputPanel(), "input");
           cardPanel.add(createViewPanel(), "view");

           frame.add(cardPanel);
           frame.setVisible(true);
       }

       public JPanel createMainMenu() {
           JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, 10));

           titleLabel = new JLabel("Student Management System",SwingConstants.CENTER);
           titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

           JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));

           JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 5, 5));

           JButton addBtn = new JButton("Add New Student");
           JButton  viewBtn = new JButton("View Student Records");

           Dimension buttonSize = new Dimension(200, 50);
           addBtn.setPreferredSize(buttonSize);
           viewBtn.setPreferredSize(buttonSize);

           JPanel darkModePanel = new JPanel();
           darkModeToggle = new JToggleButton(" Dark Mode");
           darkModeToggle.setPreferredSize(new Dimension(65,40));
           darkModeToggle.addActionListener(e -> toggleDarkMode());

           darkModePanel.add(darkModeToggle);
           buttonPanel.add(darkModePanel);

           buttonPanel.add(addBtn);
           buttonPanel.add(viewBtn);
           buttonWrapper.add(buttonPanel);



           mainPanel.add(titleLabel);
           mainPanel.add(buttonWrapper);

           addBtn.addActionListener(e -> cardLayout.show(cardPanel, "input"));

           viewBtn.addActionListener(e -> {
               updateStudentList();
               cardLayout.show(cardPanel, "view");
           });

           return mainPanel;
       }

       public JPanel createInputPanel() {
           JPanel   inputFormControl = new JPanel(new BorderLayout(10, 50));
           inputFormControl.setPreferredSize(new Dimension(400, 500));

           JPanel formInput = new JPanel(new GridLayout(6, 2, 5, 5));

           nameField = new JTextField();
           JComboBox<String> courseBox = new JComboBox<>(new String[]{
                   "Computer Science", "Engineering", "Business","IT Deparment"
           });
           JRadioButton maleBtn = new JRadioButton("Male");
           JRadioButton femaleBtn = new JRadioButton("Female");
           ButtonGroup genderGroup = new ButtonGroup();
           genderGroup.add(maleBtn);
           genderGroup.add(femaleBtn);
           JDateChooser dateChooser = new JDateChooser();
           JTextArea addressArea = new JTextArea(4, 20);

           formInput.add(new JLabel("Name:"));
           formInput.add(nameField);
           formInput.add(new JLabel("Course:"));
           formInput.add(courseBox);
           formInput.add(new JLabel("Gender:"));
           JPanel genderPanel = new JPanel();
           genderPanel.add(maleBtn);
           genderPanel.add(femaleBtn);
           formInput.add(genderPanel);
           formInput.add(new JLabel("Birth Date:"));
           formInput.add(dateChooser);
           formInput.add(new JLabel("Address:"));
           formInput.add(new JScrollPane(addressArea));

           JButton saveButton = new JButton("Save");
           JButton backButton = new JButton("Back");
           JButton clearButton = new JButton("Clear");

           JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
           buttonPanel.add(saveButton);
           buttonPanel.add(backButton);
           buttonPanel.add(clearButton);

           backButton.addActionListener(e -> cardLayout.show(cardPanel, "main"));

           clearButton.addActionListener(e -> {
               nameField.setText("");
               addressArea.setText("");
               courseBox.setSelectedIndex(0);
               genderGroup.clearSelection();
               dateChooser.setDate(null);
           });

           saveButton.addActionListener(e -> {
               // Validate that the name is not empty
               if (nameField.getText().isEmpty()) {
                   JOptionPane.showMessageDialog(frame, "Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                   return;
               }

               // Validate that a gender is selected
               if (!maleBtn.isSelected() && !femaleBtn.isSelected()) {
                   JOptionPane.showMessageDialog(frame, "Please select a gender!", "Error", JOptionPane.ERROR_MESSAGE);
                   return;
               }

               // Retrieve form field values
               String name = nameField.getText();
               String course = (String) courseBox.getSelectedItem();
               String gender = maleBtn.isSelected() ? "Male" : "Female";
               String birthDate = new SimpleDateFormat("yyyy-MM-dd").format(dateChooser.getDate());
               String address = addressArea.getText();




               // Store student data in the list
               studentRecords.add("Name: " + name + "\nCourse: " + course + "\nGender: " + gender +
                       "\nBirth Date: " + birthDate + "\nAddress: " + address);
               JOptionPane.showMessageDialog(frame, "Student Added Successfully!");

               // Update the student list in the view panel
               updateStudentList();


               // Clear fields after saving
               updateStudentList();
               nameField.setText("");
               addressArea.setText("");
               courseBox.setSelectedIndex(0);
               genderGroup.clearSelection();
               dateChooser.setDate(null);

               // Return to the main menu
               cardLayout.show(cardPanel, "main");
           });


           inputFormControl.add(formInput, BorderLayout.CENTER);
           inputFormControl.add(buttonPanel, BorderLayout.SOUTH);

           JPanel centeredPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
           centeredPanel.add(inputFormControl);

           return centeredPanel;
       }

       public JPanel createViewPanel() {
           JPanel viewPanel = new JPanel(new BorderLayout());

           displayArea = new JTextArea();
           displayArea.setEditable(false);
           displayArea.setLineWrap(true);
           displayArea.setWrapStyleWord(true);

           JScrollPane scrollPane = new JScrollPane(displayArea);
           scrollPane.setPreferredSize(new Dimension(400, 500));
           viewPanel.add(scrollPane, BorderLayout.CENTER);

           JButton backButton = new JButton("Back to Menu");
           backButton.addActionListener(e -> cardLayout.show(cardPanel, "main"));
           viewPanel.add(backButton, BorderLayout.SOUTH);

           return viewPanel;
       }

       public void updateStudentList() {

           if (studentRecords.isEmpty()) {
               displayArea.setText("No student records available.");
           }else{
               StringBuilder sb = new StringBuilder();
               sb.append("Student Records:\n\n");
               for (int i = 0; i < studentRecords.size(); i++) {
                   sb.append(studentRecords.get(i)).append("\n");
                   sb.append("-".repeat(50)).append("\n");

                   displayArea.setText(sb.toString());
                   displayArea.setCaretPosition(0);
               }
           }



       }
       public void toggleDarkMode() {
           try {
               if (darkModeToggle.isSelected()) {
                   UIManager.setLookAndFeel(new FlatDarkLaf());
               } else {
                   UIManager.setLookAndFeel(new AcrylLookAndFeel());
               }
               SwingUtilities.updateComponentTreeUI(frame); // Refresh the UI with new look and feel
           } catch (UnsupportedLookAndFeelException e) {
               e.printStackTrace();
           }
       }


   }
