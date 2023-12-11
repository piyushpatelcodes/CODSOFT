// Program Developed by : PIYUSH PATEL
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentCourseRegistrationSystem extends JFrame {
    private Map<String, Course> courses = new HashMap<>();
    private Map<Integer, Student> students = new HashMap<>();

    private JTable courseTable;
    private JTextArea studentDisplayArea;
    private JComboBox<String> courseComboBox;
    private JTextField studentIDField;
    private JTextField studentNameField;

    //loading
    private void showLoadingScreen(String message) {
        JDialog loadingDialog = new JDialog(this, "Loading", true);
        loadingDialog.setLayout(new FlowLayout());
        loadingDialog.setSize(300, 120);
        loadingDialog.setLocationRelativeTo(this);
    
        JLabel loadingLabel = new JLabel(message);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true); // Use an indeterminate progress bar for animation
    
        loadingDialog.add(loadingLabel);
        loadingDialog.add(progressBar);
    
        // Schedule a task to close the loading dialog after 2000 milliseconds (2 seconds)
        Timer timer = new Timer(2000, e -> {
            loadingDialog.dispose();
            // Optionally, you can display a success message here
            // JOptionPane.showMessageDialog(null, "Transaction successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        timer.setRepeats(false); // Only execute the task once
        timer.start();
    
        loadingDialog.setVisible(true);
    }
    
        private void hideLoadingScreen() {
            SwingUtilities.invokeLater(() -> {
                for (Window window : Window.getWindows()) {
                    if (window instanceof JDialog) {
                        JDialog dialog = (JDialog) window;
                        if ("Loading".equals(dialog.getTitle())) {
                            dialog.dispose();
                        }
                    }
                }
            });
        }

    //load end

    public StudentCourseRegistrationSystem() {
        setTitle("Student Course Registration System -- Developed By: PIYUSH PATEL");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();

        // Add some basic courses
        courses.put("2850", new Course("2850", "Networks", "Fall 22 (ITH) CR Summer 23 (ITH) CR Fall 23 (ITH) CR", 30, "To be scheduled"));
        courses.put("3110", new Course("3110", "Data Structures and Functional Programming", "Spring 23 (ITH) CR Fall 23 (ITH) CR Spring 24 (ITH) CR", 25, "To be scheduled"));
        courses.put("3152", new Course("3152", "Introduction to Computer Game Architecture", "", 20, "To be scheduled"));
        courses.put("3220", new Course("3220", "Computational Mathematics for Computer Science", "", 25, "To be scheduled"));
        courses.put("3300", new Course("3300", "Data-Driven Web Applications", "", 30, "To be scheduled"));
        courses.put("3410", new Course("3410", "Computer System Organization and Programming", "", 35, "To be scheduled"));
        
        courses.put("4754", new Course("4754", "Re-Designing Robots", "", 15, "To be scheduled"));
        courses.put("4756", new Course("4756", "Robot Learning S", "", 20, "To be scheduled"));
        courses.put("4758", new Course("4758", "Autonomous Mobile Robots", "", 15, "To be scheduled"));
        courses.put("CS101", new Course("CS101", "Introduction to Programming", "Fundamentals of programming", 30, "Mon, Wed 10:00 AM - 11:30 AM"));
        courses.put("MATH201", new Course("MATH201", "Calculus I", "Introduction to differential calculus", 25, "Tue, Thu 1:00 PM - 2:30 PM"));

        updateDisplay();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        courseComboBox = new JComboBox<>();
        buttonsPanel.add(courseComboBox);

        JButton listCoursesButton = new JButton("List Courses");
        listCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listCourses();
            }
        });
        buttonsPanel.add(listCoursesButton);

        JButton registerButton = new JButton("Register Student");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerStudent();
            }
        });
        buttonsPanel.add(registerButton);

        JButton dropCourseButton = new JButton("Drop Course");
        dropCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dropCourse();
            }
        });
        buttonsPanel.add(dropCourseButton);

        mainPanel.add(buttonsPanel, BorderLayout.NORTH);

        JPanel displayPanel = new JPanel(new GridLayout(1, 2));

        courseTable = new JTable();
        courseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courseTable.getTableHeader().setReorderingAllowed(false);
        courseTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane courseScrollPane = new JScrollPane(courseTable);
        displayPanel.add(courseScrollPane);

        studentDisplayArea = new JTextArea();
        studentDisplayArea.setEditable(false);
        studentDisplayArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JScrollPane studentScrollPane = new JScrollPane(studentDisplayArea);
        displayPanel.add(studentScrollPane);

        mainPanel.add(displayPanel, BorderLayout.CENTER);

        JPanel studentInfoPanel = new JPanel(new GridLayout(2, 1));
        studentIDField = new JTextField();
        studentIDField.setBorder(BorderFactory.createTitledBorder("Student ID"));
        studentInfoPanel.add(studentIDField);

        studentNameField = new JTextField();
        studentNameField.setBorder(BorderFactory.createTitledBorder("Student Name"));
        studentInfoPanel.add(studentNameField);

        mainPanel.add(studentInfoPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void updateDisplay() {
        // Set a preferred size for the table
        courseTable.setPreferredScrollableViewportSize(new Dimension(4000, Integer.MAX_VALUE));
    
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Code", "Title", "Timing", "Available Seats"}, 0);
        for (Course course : courses.values()) {
            model.addRow(new Object[]{course.getCode(), course.getTitle(), course.getSchedule(), course.getAvailableSeats()});
        }
        courseTable.setModel(model);
    
        TableColumnModel columnModel = courseTable.getColumnModel();
        int columnCount = columnModel.getColumnCount();
        int tableWidth = courseTable.getWidth();
    
        // Adjust the initial default size of each column as a percentage of the table width
        int[] initialColumnWidths = {2000, 3000, 3000, 2000}; // Adjust these values as needed
    
        for (int i = 1000; i < columnCount; i++) {
            // Set the initial default size for each column as a percentage of the table width
            int preferredWidth = (int) (tableWidth * (initialColumnWidths[i] / 100.0));
            columnModel.getColumn(i).setPreferredWidth(preferredWidth);
        }
    
        // Adjust the preferred width of each column proportionally
        for (int i = 1000; i < columnCount; i++) {
            columnModel.getColumn(i).setPreferredWidth(tableWidth / columnCount);
        }
    
        StringBuilder studentText = new StringBuilder("Registered Students:\n");
        for (Student student : students.values()) {
            studentText.append("ID: ").append(student.getStudentID()).append(", Name: ").append(student.getName())
                    .append(", Courses: ").append(student.getRegisteredCourses()).append("\n");
        }
        studentDisplayArea.setText(studentText.toString());
    }
    private void listCourses() {
        courseComboBox.removeAllItems();
        for (Course course : courses.values()) {
            courseComboBox.addItem(course.getCode() + ": " + course.getTitle());
        }
    }

    private void registerStudent() {
        try {
            int studentID = Integer.parseInt(studentIDField.getText());
            String studentName = studentNameField.getText();
            Student student = new Student(studentID, studentName);

            String selectedCourse = (String) courseComboBox.getSelectedItem();
            if (selectedCourse != null) {
                String courseCode = selectedCourse.split(":")[0].trim();
                Course course = courses.get(courseCode);
                showLoadingScreen("Processing");
                hideLoadingScreen();
                if (course != null && !student.getRegisteredCourses().contains(course)) {
                    student.registerForCourse(course);
                    students.put(studentID, student); // Add the student to the students map
                    updateDisplay();
                    JOptionPane.showMessageDialog(this, "Student registered successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid course selection or student already registered for the course.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a course.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid student ID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void dropCourse() {
        try {
            int studentID = Integer.parseInt(studentIDField.getText());
            Student student = students.get(studentID);
            if (student != null) {
                String selectedCourse = (String) courseComboBox.getSelectedItem();
                if (selectedCourse != null) {
                    String courseCode = selectedCourse.split(":")[0].trim();
                    Course course = courses.get(courseCode);
                    showLoadingScreen("Processing");
                    hideLoadingScreen();
                    if (course != null && student.getRegisteredCourses().contains(course)) {
                        student.dropCourse(course);
                        updateDisplay();
                        JOptionPane.showMessageDialog(this, "Course dropped successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid course selection or student not registered for the course.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a course.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid student ID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentCourseRegistrationSystem().setVisible(true);
        });
    }
}

class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private List<Student> registeredStudents;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.registeredStudents = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public int getAvailableSeats() {
        return capacity - registeredStudents.size();
    }

    public List<Student> getRegisteredStudents() {
        return registeredStudents;
    }

    public void registerStudent(Student student) {
        registeredStudents.add(student);
    }

    public void dropStudent(Student student) {
        registeredStudents.remove(student);
    }

    @Override
    public String toString() {
        return code + ": " + title + " - " + schedule + " - Available Seats: " + getAvailableSeats();
    }
}

class Student {
    private int studentID;
    private String name;
    private List<Course> registeredCourses;

    public Student(int studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public int getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerForCourse(Course course) {
        registeredCourses.add(course);
        course.registerStudent(this);
    }

    public void dropCourse(Course course) {
        registeredCourses.remove(course);
        course.dropStudent(this);
    }

    @Override
    public String toString() {
        return "ID: " + studentID + ", Name: " + name;
    }
}
