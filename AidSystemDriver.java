package com.example.aidmanagerprojectgui2026;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class AidSystemDriver {

    public static void main(String[] args) throws DuplicateRegistrationException, CityNotServedException, ItemNotFoundException, IOException {
        Scanner scanner = new Scanner(System.in);
        AidManager admin = new AidManager(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        int option = -1;
        while (option != 0) {
            menu();
            System.out.println("Enter your option: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
            option = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (option) {
                case 1:
                    admin.addBeneficiary(scanner);
                    break;
                case 2:
                    admin.addAidItem(scanner);
                    break;
                case 3:
                    admin.addDistributionEvent(scanner);
                    break;
                case 4:
                    admin.DisplayBeneficiaries();
                    break;
                case 5:
                    admin.DisplayAidItems();
                    break;
                case 6:
                    admin.DisplayDistributionEvents();
                    break;
                case 7:
                    handleStat(scanner, admin);
                    break;
                case 8:
                    admin.saveToTextFile("aidDataText.txt");
                    break;
                case 9:
                    admin.saveToBinaryFile("aidData.dat");
                    break;
                case 10:
                    admin.loadFromTextFile("aidDataText.txt");
                    break;
                case 11:
                    admin.loadFromBinaryFile("aidData.dat");
                    break;
                case 0:
                    System.out.println("Existing...");
                    System.exit(0);
                    break;
            }
        }
    }

    public static void menu() {
        System.out.println("==== Smart Aid Distribution System ====\r\n"
                + "1. Register Beneficiary\r\n"
                + "2. Add Aid Item\r\n"
                + "3. Record Aid Distribution\r\n"
                + "4. Display All Beneficiaries\r\n"
                + "5. Display All Aid Items\r\n"
                + "6. Display All Distribution Events\r\n"
                + "7. Generate Statistical Reports\r\n"
                + "8. Save to Text File\r\n"
                + "9. Save to Binary File\r\n"
                + "10. Load from Text File\r\n"
                + "11. Load from Binary File\r\n"
                + "0. Exit \r");
    }

    public static void statisticalMenu() {
        System.out.println("==== Statistical Reports ====\r\n"
                + "1. Number of families served in a city\r\n"
                + "2. Total aid items distributed\r\n"
                + "3. Count aid items by category\r\n"
                + "4. Beneficiaries served between two dates\r\n"
                + "5. Most served city\r\n"
                + "0. Back to main menu");
    }

    public static int validateIntInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    public static GregorianCalendar readDate(Scanner scanner) {
        String line = scanner.nextLine().trim();
        try {
            String[] parts = line.split("/");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]) - 1;
            int year = Integer.parseInt(parts[2]);
            return new GregorianCalendar(year, month, day);
        } catch (Exception e) {
            System.out.println("Invalid date format ");
            return null;
        }
    }

    public static void handleStat(Scanner scanner, AidManager admin) throws CityNotServedException {
        int option = -1;
        statisticalMenu();
        while (option != 0) {
            System.out.println("Choose a report");
            option = validateIntInput(scanner);
            switch (option) {
                case 1:
                    System.out.println("enter city name: (gaza,rafah,jenin,tulkarm");
                    String city = scanner.nextLine().trim();
                    if (!city.equalsIgnoreCase("rafah")
                            && !city.equalsIgnoreCase("gaza")
                            && !city.equalsIgnoreCase("tulkarm")
                            && !city.equalsIgnoreCase("jenin")) {
                        throw new CityNotServedException("Sorry this city is not available yet");
                    }
                    int num = admin.numOfFamiliesServedInCity(city);
                    System.out.println("Fmailies served in " + city + " is : " + num);
                    break;
                case 2:
                    int total = admin.totalAidItemsDistributed();
                    System.out.println("total aid items distributed is: " + total);
                    break;
                case 3:
                    System.out.println("Choose category:");
                    System.out.println("1. FoodPackage");
                    System.out.println("2. MedicalKit");
                    System.out.println("3. WinterBag");
                    System.out.println("4. EmergencyKit");
                    System.out.println("enter category num: ");
                    int cat = validateIntInput(scanner);
                    String category = null;
                    switch (cat) {
                        case 1:
                            category = "FoodPackage";
                            break;
                        case 2:
                            category = "MedicalKit";
                            break;
                        case 3:
                            category = "WinterBag";
                            break;
                        case 4:
                            category = "EmergencyKit";
                            break;
                        default:
                            System.out.println("Invalid category.");
                    }

                    if (category != null) {
                        int count = admin.countAidItemsbyCategory(category);
                        System.out.println("Items distributed of " + category + " is: " + count);
                    }
                    break;
                case 4:
                    System.out.println("enter start date as the following: (dd/mm/yyyy");
                    GregorianCalendar start = readDate(scanner);
                    if (start == null) {
                        break;
                    }
                    System.out.println("enter the end date as the following (dd/mm/yyyy)");
                    GregorianCalendar end = readDate(scanner);
                    if (end == null) {
                        break;
                    }

                    int num1 = admin.beneficiariesServedBetweenDates(start, end);
                    System.out.println("Beneficiaries served between dates: " + num1);
                    break;
                case 5:
                    String mostServedCity = admin.mostServedCity();
                    if (mostServedCity == null) {
                        System.out.println("no events were made yet");
                    } else {
                        System.out.println("most served city is : " + mostServedCity);
                    }
                    break;
                case 0:
                    System.out.println("back to main menu...");
                    break;
            }
        }
    }
}
