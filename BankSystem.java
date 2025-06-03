import java.io.*;

public class BankSystem {
    private Customer[] customers;
    private int count;

    public BankSystem(int capacity) {
        customers = new Customer[capacity];
        count = 0;
    }

    public void addCustomer(Customer c) throws Exception {
        if (count >= customers.length) {
            throw new Exception("Bank is full. Cannot add more customers.");
        }
        customers[count] = c;
        count++;
    }

    public void deleteCustomer(String id) {
        for (int i = 0; i < count; i++) {
            if (customers[i].getId().equals(id)) {
                // Shift remaining customers left
                for (int j = i; j < count - 1; j++) {
                    customers[j] = customers[j + 1];
                }
                customers[count - 1] = null;
                count--;
                break;
            }
        }
    }

    public Customer[] getCustomers() {
        Customer[] currentCustomers = new Customer[count];
        for (int i = 0; i < count; i++) {
            currentCustomers[i] = customers[i];
        }
        return currentCustomers;
    }

    // File I/O for saving customers
    public void saveToFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < count; i++) {
                Customer c = customers[i];
                bw.write(c.getName() + "," + c.getId() + "," + c.getBalance());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving customers: " + e.getMessage());
        }
    }

    // Load customers from file
    public void loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            count = 0;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    String id = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    if (count < customers.length) {
                        customers[count++] = new Customer(name, id, balance);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }
    }
}
