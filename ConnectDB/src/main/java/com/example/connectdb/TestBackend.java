package com.example.connectdb;

import com.example.connectdb.DAO.StudentDAO;
import com.example.connectdb.model.Student;

import java.util.List;
import java.util.Scanner;
public class TestBackend {
    private static final StudentDAO dao = new StudentDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== STUDENT MANAGE SYSTEM ===");

        while (true) {
            System.out.println("\n-------------------------------------------");
            System.out.println("SELECT OPTION:");
            System.out.println("1. View student list");
            System.out.println("2. Add new student");
            System.out.println("3. Update student information");
            System.out.println("4. Delete student");
            System.out.println("5. Search student");
            System.out.println("0. Exit");
            System.out.print("Your option is: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("!!! Invalid selection. Please enter number.");
                continue;
            }

            switch (choice) {
                case 1:
                    showAllStudents();
                    break;
                case 2:
                    addStudent();
                    showAllStudents();
                    break;
                case 3:
                    updateStudent();
                    showAllStudents();
                    break;
                case 4:
                    deleteStudent();
                    showAllStudents();
                    break;
                case 5:
                    searchStudent();
                    break;
                case 0:
                    System.out.println("Exiting the program...");
                    return;
                default:
                    System.out.println("!!! Invalid selection!");
            }
        }
    }

    private static void showAllStudents() {
        System.out.println("\n>>> LIST OF CURRENT STUDENTS <<<");
        List<Student> list = dao.getAllStudents();

        if (list.isEmpty()) {
            System.out.println("(Empty list)");
        } else {
            System.out.printf("%-5s %-20s %-20s %-20s %-30s\n", "ID", "USERNAME", "PASSWORD", "NAME", "ADDRESS");
            System.out.println(
                    "----------------------------------------------------------------------------------------------------");
            for (Student s : list) {
                System.out.printf("%-5d %-20s %-20s %-20s %-30s\n",
                        s.getId(), s.getUsername(), s.getPassword(), s.getName(), s.getAddress());
            }
        }
        System.out.println(
                "----------------------------------------------------------------------------------------------------");
    }

    private static void addStudent() {
        System.out.println("\n[ADD NEW STUDENT]");
        try {
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Address: ");
            String address = scanner.nextLine();

            Student s = new Student();
            s.setUsername(username);
            s.setPassword(password);
            s.setName(name);
            s.setAddress(address);

            if (dao.addStudent(s)) {
                System.out.println("-> Successfully added!");
            } else {
                System.out.println("-> Failed to add! (The username may already exist.)");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateStudent() {
        System.out.println("\n[UPDATE STUDENT INFORMATION]");
        try {
            System.out.print("Enter Student ID to Update: ");
            int id = Integer.parseInt(scanner.nextLine());

            List<Student> all = dao.getAllStudents();
            boolean found = false;
            for (Student s : all) {
                if (s.getId() == id) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("!!! No student found with ID : " + id);
                return;
            }

            System.out.print("Enter new Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter new Password: ");
            String password = scanner.nextLine();
            System.out.print("Enter new Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter new Address: ");
            String address = scanner.nextLine();

            Student s = new Student(id, username, password, name, address);
            if (dao.updateStudent(s)) {
                System.out.println("-> Successfully Updated!");
            } else {
                System.out.println("-> Failed Updated!");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: ID must be a number!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteStudent() {
        System.out.println("\n[DELETE STUDENT]");
        try {
            System.out.print("Enter Student ID to Delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Are you sure you want to delete ID " + id + "? (y/n): ");
            String confirm = scanner.nextLine();
            if (!confirm.equalsIgnoreCase("y")) {
                System.out.println("-> Cancelled Deletion.");
                return;
            }

            if (dao.deleteStudent(id)) {
                System.out.println("-> Successfully Deletion!");
            } else {
                System.out.println("-> Failed Deletion (ID does not exist or DB error)!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: ID must be a number!");
        }
    }

    private static void searchStudent() {
        System.out.println("\n[SEARCH STUDENT]");
        System.out.print("Enter ID: ");
        int keyword;
        try {
            String input = scanner.nextLine();
            keyword = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Error: ID must be a number!");
            return;
        }
        List<Student> results = dao.searchStudent(keyword);

        if (results.isEmpty()) {
            System.out.println("-> No results found.");
        } else {
            System.out.println("-> Found " + results.size() + " result:");
            System.out.printf("%-5s %-20s %-20s %-20s %-30s\n", "ID", "USERNAME", "PASSWORD", "NAME", "ADDRESS");
            for (Student s : results) {
                System.out.printf("%-5d %-20s %-20s %-20s %-30s\n",
                        s.getId(), s.getUsername(), s.getPassword(), s.getName(), s.getAddress());
            }
        }
    }
}