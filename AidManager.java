package com.example.aidmanagerprojectgui2026;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class AidManager implements Serializable, FileOperations {

    private ArrayList<Beneficiary> beneficiaries = new ArrayList<>();
    private ArrayList<AidItem> aidItems = new ArrayList<>();
    private ArrayList<DistributionEvent> distributionEvents = new ArrayList<>();

    public AidManager(ArrayList<Beneficiary> beneficiaries, ArrayList<AidItem> aidItems,
                      ArrayList<DistributionEvent> distributionEvents) {
        this.beneficiaries = beneficiaries;
        this.aidItems = aidItems;
        this.distributionEvents = distributionEvents;

    }
    public AidManager(){
        this(new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
    }
    public ArrayList<Beneficiary> getBeneficiaries() {
        return beneficiaries;
    }

    public ArrayList<AidItem> getAidItems() {
        return aidItems;
    }

    public ArrayList<DistributionEvent> getDistributionEvents() {
        return distributionEvents;
    }
    //add to arrayList methods  

    public void addBeneficiary(Scanner scanner) throws DuplicateRegistrationException, CityNotServedException {
        String type;
        System.out.println("Enter beneficiary type (family/individual): ");
        type = scanner.nextLine();
        if (type.equalsIgnoreCase("family")) {
            System.out.println("Enter family id: ");
            String id = scanner.nextLine().trim();
            if (searchBeneficiarie(id) != null) {
                throw new DuplicateRegistrationException("this id:" + id + " already exists");
            }
            System.out.println("Enter family name: ");
            String name = scanner.nextLine().trim();
            System.out.println("Enter family city: ");
            System.out.println("available cities: Rafah, gaza, tulkarm, jenin");
            String city = scanner.nextLine().trim();
            if (!city.equalsIgnoreCase("rafah")
                    && !city.equalsIgnoreCase("gaza")
                    && !city.equalsIgnoreCase("tulkarm")
                    && !city.equalsIgnoreCase("jenin")) {
                throw new CityNotServedException("Sorry this city is not available yet");
            }
            System.out.println("Enter number of family members: ");
            int memberCount;
            //validates that an integer is the input
            while (!scanner.hasNextInt()) {
                System.out.println("please enter am integer");
                scanner.next();
            }
            memberCount = scanner.nextInt();
            scanner.nextLine();
            Family family = new Family(city, id, name, memberCount);
            beneficiaries.add(family);
            System.out.println("Family has been added successfully");

        } else if (type.equalsIgnoreCase("individual")) {
            System.out.println("Enter id: ");
            String id = scanner.nextLine();
            if (searchBeneficiarie(id) != null) {
                throw new DuplicateRegistrationException("this id already exists");
            }
            System.out.println("Enter name: ");
            String name = scanner.nextLine();
            System.out.println("Enter city name: ");
            System.out.println("available cities: Rafah, gaza, tulkarm, jenin");
            String city = scanner.nextLine();
            if (!city.equalsIgnoreCase("rafah")
                    && !city.equalsIgnoreCase("gaza")
                    && !city.equalsIgnoreCase("tulkarm")
                    && !city.equalsIgnoreCase("jenin")) {
                throw new CityNotServedException("Sorry this city is not available yet");
            }
            System.out.println("Enter individual status: ");
            String status = scanner.nextLine();
            Individual individual = new Individual(city, id, name, status);
            beneficiaries.add(individual);
            System.out.println("Individual has been added successfully");
        } else {
            System.out.println("invalid type...Type must be either Family or individual");
        }
    }

    public void addAidItem(Scanner scanner) throws DuplicateRegistrationException {
        System.out.println("enter ID: ");
        String id = scanner.nextLine();
        for (int i = 0; i < aidItems.size(); i++) {
            //check for duplicate id
            if (aidItems.get(i).getCode().equalsIgnoreCase(id)) {
                throw new DuplicateRegistrationException("Aid item with code " + id + " already exists.");
            }
        }
        System.out.println("enter item description: ");
        String description = scanner.nextLine();
        System.out.println("Choose item category:");
        System.out.println("1. FoodPackage");
        System.out.println("2. MedicalKit");
        System.out.println("3. WinterBag");
        System.out.println("4. EmergencyKit");
        System.out.print("Enter option: ");
        int op;
        while (!scanner.hasNextInt()) {
            System.out.println("please enter a valid integer");
            scanner.next();
        }
        op = scanner.nextInt();

        AidItem item = null;
        switch (op) {
            case 1:
                item = new FoodPackage(id, description);
                break;
            case 2:
                item = new MedicalKit(id, description);
                break;
            case 3:
                item = new WinterBag(id, description);
                break;
            case 4:
                item = new EmergencyKit(id, description);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }

        if (item != null) {
            aidItems.add(item);
            System.out.println("Aid item has been added successfully");
        }
        scanner.nextLine();
    }

    public void addDistributionEvent(Scanner scanner) throws ItemNotFoundException {
        System.out.println("enter beneficiary ID: ");
        String benId = scanner.nextLine().trim();
        Beneficiary beneficiary = searchBeneficiarie(benId);
        if (beneficiary == null) {
            throw new ItemNotFoundException("Beneficary with the ID" + benId + " was not found");
        }
        System.out.println("enter Aid Item ID: ");
        String aidId = scanner.nextLine();
        AidItem item = searchAidItem(aidId);
        if (item == null) {
            throw new ItemNotFoundException("Aid Item with the ID " + aidId + " was not found");
        }
        System.out.println("enter the date of the distribution as the following format: (dd//mm/yyyy)");
        String dateString = scanner.nextLine().trim();
        GregorianCalendar date;
        try {
            String[] parts = dateString.split("/");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]) - 1;
            int year = Integer.parseInt(parts[2]);
            date = new GregorianCalendar(year, month, day);

        } catch (Exception e) {
            System.out.println("invalid format please try again");
            return;
        }
        DistributionEvent e = new DistributionEvent(item, beneficiary, date);
        distributionEvents.add(e);
        System.out.println("event was added ");
    }
    //display methods

    public void DisplayBeneficiaries() {
        for (int i = 0; i < beneficiaries.size(); i++) {
            System.out.println(beneficiaries.get(i));
        }
    }

    public void DisplayAidItems() {
        for (int i = 0; i < aidItems.size(); i++) {
            System.out.println(aidItems.get(i));
        }
    }

    public void DisplayDistributionEvents() {
        for (int i = 0; i < distributionEvents.size(); i++) {
            System.out.println(distributionEvents.get(i));
        }
    }

    // save to file methods
    public void saveToTextFile(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

        for (int i = 0; i < beneficiaries.size(); i++) {
            Beneficiary b = beneficiaries.get(i);
            if (b instanceof Family) {
                Family f = (Family) b;
                writer.write("Family," + f.getId() + "," + f.getName() + ","
                        + f.getCity() + "," + f.getMemberCount());
            } else if (b instanceof Individual) {
                Individual d = (Individual) b;
                writer.write("Individual," + d.getId() + "," + d.getName() + ","
                        + d.getCity() + "," + d.getStatus());
            }
            writer.newLine();
        }

        for (int i = 0; i < aidItems.size(); i++) {
            AidItem item = aidItems.get(i);
            writer.write("Item," + item.getCode() + "," + item.getDescription() + "," + item.getCategory());
            writer.newLine();
        }

        for (int i = 0; i < distributionEvents.size(); i++) {
            DistributionEvent e = distributionEvents.get(i);
            int day = e.getDate().get(GregorianCalendar.DAY_OF_MONTH);
            int month = e.getDate().get(GregorianCalendar.MONTH) + 1;
            int year = e.getDate().get(GregorianCalendar.YEAR);
            writer.write("distributionEvent," + e.getItem().getCode() + "," + e.getBeneficiary().getId()
                    + "," + day + "/" + month + "/" + year);
            writer.newLine();
        }

        writer.close();
        System.out.println("data has been saved successfully to text file: " + filename);
    }

    public void saveToBinaryFile(String filename) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(beneficiaries);
        out.writeObject(aidItems);
        out.writeObject(distributionEvents);
        out.close();
        System.out.println("data has been saved to binary file successfully: " + filename);
    }

    public void loadFromTextFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String Line;
        while ((Line = reader.readLine()) != null) {

            String[] parts = Line.split(",");
            String type = parts[0];
            if (type.equalsIgnoreCase("family")) {
                String id = parts[1];
                String name = parts[2];
                String city = parts[3];
                int members = Integer.parseInt(parts[4]);
                beneficiaries.add(new Family(city, id, name, members));
            } else if (type.equalsIgnoreCase("Individual")) {
                String id = parts[1];
                String name = parts[2];
                String city = parts[3];
                String status = parts[4];
                beneficiaries.add(new Individual(city, id, name, status));
            } else if (type.equalsIgnoreCase("item")) {
                String code = parts[1];
                String discription = parts[2];
                String category = parts[3];
                if (category.equalsIgnoreCase("foodPackage")) {
                    aidItems.add(new FoodPackage(code, discription));
                } else if (category.equalsIgnoreCase("MedicalKit")) {
                    aidItems.add(new MedicalKit(code, discription));
                } else if (category.equalsIgnoreCase("WinterBag")) {
                    aidItems.add(new WinterBag(code, discription));
                } else if (category.equalsIgnoreCase("EmergencyKit")) {
                    aidItems.add(new EmergencyKit(code, discription));
                }
            } else if (type.equalsIgnoreCase("distributionEvent")) {
                String itemCode = parts[1];
                String beneficiaryId = parts[2];
                String[] dateParts = parts[3].split("/");
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]) - 1;
                int year = Integer.parseInt(dateParts[2]);
                GregorianCalendar date = new GregorianCalendar(year, month, day);
                AidItem item = findItemByCode(itemCode);
                Beneficiary beneficiary = findBeneficiaryById(beneficiaryId);
                if (item != null && beneficiary != null) {
                    DistributionEvent e = new DistributionEvent(item, beneficiary, date);
                    distributionEvents.add(e);
                }

            }

        }
        reader.close();
        System.out.println("data has been loaded successfully from text file: " + filename);
    }

    public void loadFromBinaryFile(String filename) throws IOException, FileNotFoundException {
        FileInputStream fileIn = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        try {
            beneficiaries = (ArrayList<Beneficiary>) in.readObject();
            aidItems = (ArrayList<AidItem>) in.readObject();
            distributionEvents = (ArrayList<DistributionEvent>) in.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("class not found while reading binary file", e);
        } finally {
            in.close();
            fileIn.close();
        }
    }



    //helper methods to find object by their id to able able to rebuild the object when reading from file
    private AidItem findItemByCode(String code) {
        for (int i = 0; i < aidItems.size(); i++) {
            if (aidItems.get(i).getCode().equalsIgnoreCase(code)) {
                return aidItems.get(i);
            }
        }
        return null;
    }

    private Beneficiary findBeneficiaryById(String id) {
        for (int i = 0; i < beneficiaries.size(); i++) {
            if (beneficiaries.get(i).getId().equalsIgnoreCase(id)) {
                return beneficiaries.get(i);
            }
        }
        return null;
    }

    //statistical report methods
    public int numOfFamiliesServedInCity(String city) {
        int count = 0;
        //to check if a family was served more than once
        ArrayList<String> servedFamilyIds = new ArrayList<>();
        for (int i = 0; i < distributionEvents.size(); i++) {
            DistributionEvent e = distributionEvents.get(i);
            Beneficiary b = e.getBeneficiary();
            if (b instanceof Family && b.getCity().equalsIgnoreCase(city)) {
                String familyId = b.getId();
                if (!servedFamilyIds.contains(familyId)) {
                    servedFamilyIds.add(familyId);
                    count++;
                }

            }
        }
        return count;
    }

    public int totalAidItemsDistributed() {
        return distributionEvents.size();
    }

    public int countAidItemsbyCategory(String category) {
        int count = 0;
        for (int i = 0; i < distributionEvents.size(); i++) {
            DistributionEvent e = distributionEvents.get(i);
            AidItem item = e.getItem();
            if (item.getCategory().equalsIgnoreCase(category)) {
                count++;
            }
        }
        return count;
    }

    public int beneficiariesServedBetweenDates(GregorianCalendar startDate, GregorianCalendar endDate) {
        int count = 0;
        //to avoid counting a beneficary twice
        ArrayList<String> servedBeneficiaryIds = new ArrayList<>();
        for (int i = 0; i < distributionEvents.size(); i++) {
            DistributionEvent e = distributionEvents.get(i);
            GregorianCalendar eventDate = e.getDate();
            if (eventDate.compareTo(startDate) >= 0 && eventDate.compareTo(endDate) <= 0) {
                String id = e.getBeneficiary().getId();
                if (!servedBeneficiaryIds.contains(id)) {
                    servedBeneficiaryIds.add(id);
                    count++;
                }

            }
        }
        return count;
    }

    public String mostServedCity() {
        String mostServedCity = "";
        int maxCount = 0;
        for (int i = 0; i < beneficiaries.size(); i++) {
            String city = beneficiaries.get(i).getCity();
            int count = 0;
            for (int j = 0; j < distributionEvents.size(); j++) {
                DistributionEvent e = distributionEvents.get(j);
                Beneficiary b = e.getBeneficiary();
                if (b.getCity().equalsIgnoreCase(city)) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                mostServedCity = city;
            }
        }
        System.out.println("Most served city is: " + mostServedCity + " it was served " + maxCount + " times");
        return mostServedCity;
    }

    public Beneficiary searchBeneficiarie(String id) {
        for (int i = 0; i < beneficiaries.size(); i++) {
            if (beneficiaries.get(i).getId().equalsIgnoreCase(id)) {
                return beneficiaries.get(i);
            }
        }
        return null;
    }

    public AidItem searchAidItem(String code) throws ItemNotFoundException {
        for (int i = 0; i < aidItems.size(); i++) {
            if (aidItems.get(i).getCode().equalsIgnoreCase(code)) {
                return aidItems.get(i);
            }
        }
        throw new ItemNotFoundException("Item with code " + code + " was not found");
    }

    public DistributionEvent searchDistributionEvent(String itemCode, String beneficiaryId) {
        for (int i = 0; i < distributionEvents.size(); i++) {
            DistributionEvent e = distributionEvents.get(i);
            if (e.getItem().getCode().equalsIgnoreCase(itemCode)
                    && e.getBeneficiary().getId().equalsIgnoreCase(beneficiaryId)) {
                return e;
            }
        }
        return null;
    }
}
