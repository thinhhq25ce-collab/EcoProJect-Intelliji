package udn.vku.greenstayapp;

import udn.vku.greenstayapp.dao.HomestayDAO;
import udn.vku.greenstayapp.model.Homestay;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final HomestayDAO dao = new HomestayDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== GREENSTAY MANAGEMENT SYSTEM (CONSOLE) ===");

        while (true) {
            System.out.println("\n-------------------------------------------");
            System.out.println("SELECT OPTION:");
            System.out.println("1. View Homestay list");
            System.out.println("2. Add new Homestay");
            System.out.println("3. Delete Homestay");
            System.out.println("4. Update Homestay");
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
                    showAllHomestays();
                    break;
                case 2:
                    addHomestay();
                    showAllHomestays();
                    break;
                case 3:
                    deleteHomestay();
                    showAllHomestays();
                    break;
                case 4:
                    updateHomestay();
                    showAllHomestays();
                case 0:
                    System.out.println("Exiting the program...");
                    System.exit(0);
                default:
                    System.out.println("!!! Invalid selection !!!");
            }
        }
    }

    private static void showAllHomestays() {
        System.out.println("\n>>> LIST OF CURRENT HOMESTAYS <<<");
        List<Homestay> list = dao.getAllHomestays();

        if (list.isEmpty()) {
            System.out.println("(Empty list)");
        } else {
            System.out.printf("%-5s %-25s %-20s %-15s %-10s\n", "ID", "NAME", "ADDRESS", "PRICE", "ECO");
            System.out.println("----------------------------------------------------------------------------------");
            for (Homestay h : list) {
                System.out.printf("%-5d %-25s %-20s %-15.1f %-10s\n",
                        h.getId(),
                        h.getName(),
                        h.getAddress(),
                        h.getPrice(),
                        h.isEcoCertified() ? "Yes" : "No");
            }
        }
        System.out.println("----------------------------------------------------------------------------------");
    }

    private static void addHomestay() {
        System.out.println("\n[ADD NEW HOMESTAY]");
        try {
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Address: ");
            String address = scanner.nextLine();

            System.out.print("Enter Price: ");
            double price = Double.parseDouble(scanner.nextLine());

            System.out.print("Is Eco Certified? (true/false): ");
            boolean isEco = Boolean.parseBoolean(scanner.nextLine());

            Homestay h = new Homestay(0, name, address, price, isEco);

            dao.addHomestay(h);
            System.out.println("-> Successfully added: " + name);

        } catch (NumberFormatException e) {
            System.out.println("Error: Price must be a number!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteHomestay() {
        System.out.println("\n[DELETE HOMESTAY]");
        try {
            System.out.print("Enter Homestay ID to Delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Are you sure you want to delete ID " + id + "? (y/n): ");
            String confirm = scanner.nextLine();

            if (!confirm.equalsIgnoreCase("y")) {
                System.out.println("-> Cancelled Deletion.");
                return;
            }

            dao.deleteHomestay(id);
            System.out.println("-> Delete command sent for ID: " + id);

        } catch (NumberFormatException e) {
            System.out.println("Error: ID must be a number!");
        }
    }

    private static void updateHomestay() {

        System.out.println("\n[UPDATE HOMESTAY]");
        try {
            System.out.println("Enter Homestay ID you want to Update: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter new Name: ");
            String name = scanner.nextLine();

            System.out.println("Enter new Address: ");
            String address = scanner.nextLine();

            System.out.println("Enter new Price: ");
            double price = Double.parseDouble(scanner.nextLine());

            System.out.println("Is Eco Certified? (true/false): ");
            boolean isEco = Boolean.parseBoolean(scanner.nextLine());

            Homestay h = new Homestay(id, name, address, price, isEco);

            dao.updateHomestay(h);

            System.out.println("-> Successfully Update ID: " + id);
        }catch (NumberFormatException e){
            System.out.println("Error: Price or ID must be a number !!!");
        }catch(Exception e){
            System.out.println("Error: "+ e.getMessage());
        }
    }
}