import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Singleton for Employee Database
class EmployeeDatabase {
    private static EmployeeDatabase instance;
    private List<Employee> employees;

    // Private constructor to prevent instantiation
    private EmployeeDatabase() {
        employees = new ArrayList<>();
    }

    // Public method to provide access to the instance
    public static EmployeeDatabase getInstance() {
        if (instance == null) {
            instance = new EmployeeDatabase();
        }
        return instance;
    }

    // Method to add an employee to the database
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    // Method to display all employees
    public void displayAllEmployees() {
        for (Employee employee : employees) {
            employee.displayDetails();
        }
    }
}

// Abstract Employee Class
abstract class Employee {
    protected String name;
    protected String role;
    protected double salary;
    protected String department;

    public abstract void displayDetails();
}

// Concrete Employee Classes
class Manager extends Employee {
    public Manager(String name, double salary, String department) {
        this.name = name;
        this.role = "Manager";
        this.salary = salary;
        this.department = department;
    }

    @Override
    public void displayDetails() {
        System.out.println("Employee Name: " + name);
        System.out.println("Role: " + role);
        System.out.println("Salary: " + salary);
        System.out.println("Department: " + department);
    }
}

class Developer extends Employee {
    public Developer(String name, double salary, String department) {
        this.name = name;
        this.role = "Developer";
        this.salary = salary;
        this.department = department;
    }

    @Override
    public void displayDetails() {
        System.out.println("Employee Name: " + name);
        System.out.println("Role: " + role);
        System.out.println("Salary: " + salary);
        System.out.println("Department: " + department);
    }
}

class HR extends Employee {
    public HR(String name, double salary, String department) {
        this.name = name;
        this.role = "HR";
        this.salary = salary;
        this.department = department;
    }

    @Override
    public void displayDetails() {
        System.out.println("Employee Name: " + name);
        System.out.println("Role: " + role);
        System.out.println("Salary: " + salary);
        System.out.println("Department: " + department);
    }
}

// Builder Pattern for Employee
class EmployeeBuilder {
    private String name;
    private String role;
    private double salary;
    private String department;

    public EmployeeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public EmployeeBuilder setRole(String role) {
        this.role = role;
        return this;
    }

    public EmployeeBuilder setSalary(double salary) {
        this.salary = salary;
        return this;
    }

    public EmployeeBuilder setDepartment(String department) {
        this.department = department;
        return this;
    }

    public Employee build() {
        if ("Manager".equalsIgnoreCase(role)) {
            return new Manager(name, salary, department);
        } else if ("Developer".equalsIgnoreCase(role)) {
            return new Developer(name, salary, department);
        } else if ("HR".equalsIgnoreCase(role)) {
            return new HR(name, salary, department);
        } else {
            throw new IllegalStateException("Invalid role: " + role);
        }
    }
}

// Main Class
public class EmployeeManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EmployeeDatabase database = EmployeeDatabase.getInstance();

        System.out.println("Welcome to the Employee Management System!");
        boolean running = true;

        while (running) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.println("\nEnter Employee Details:");
                    System.out.print("Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Role (Manager/Developer/HR): ");
                    String role = scanner.nextLine();

                    System.out.print("Salary: ");
                    double salary = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline

                    System.out.print("Department: ");
                    String department = scanner.nextLine();

                    try {
                        Employee employee = new EmployeeBuilder()
                                .setName(name)
                                .setRole(role)
                                .setSalary(salary)
                                .setDepartment(department)
                                .build();
                        database.addEmployee(employee);
                        System.out.println("Employee added successfully!");
                    } catch (IllegalStateException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.println("\nAll Employees in the Database:");
                    database.displayAllEmployees();
                }
                case 3 -> {
                    System.out.println("Exiting the system. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
