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

    // Expose the list of employees for editing
    public List<Employee> getEmployees() {
        return employees;
    }
}

// Abstract Employee Class
abstract class Employee implements Cloneable {
    protected String name;
    protected String role;
    protected double salary;
    protected String department;

    public abstract void displayDetails();

    // Prototype Pattern: Clone method
    @Override
    public Employee clone() {
        try {
            return (Employee) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Should never happen
        }
    }
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
        System.out.println("=====================================");
        System.out.println("Employee Name  : " + name);
        System.out.println("Role           : " + role);
        System.out.println("Salary         : " + salary);
        System.out.println("Department     : " + department);
        System.out.println("=====================================");
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
        System.out.println("=====================================");
        System.out.println("Employee Name  : " + name);
        System.out.println("Role           : " + role);
        System.out.println("Salary         : " + salary);
        System.out.println("Department     : " + department);
        System.out.println("=====================================");
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
        System.out.println("=====================================");
        System.out.println("Employee Name  : " + name);
        System.out.println("Role           : " + role);
        System.out.println("Salary         : " + salary);
        System.out.println("Department     : " + department);
        System.out.println("=====================================");
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
            System.out.println("4. Edit Employee");

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
                case 4 -> {
                    System.out.println("\nEdit Employee Details:");

                    // Display employees with indexes
                    System.out.println("Select an Employee to Edit:");
                    List<Employee> employees = database.getEmployees();
                    for (int i = 0; i < employees.size(); i++) {
                        System.out.println(i + 1 + ". " + employees.get(i).name);
                    }

                    System.out.print("Enter the Employee number: ");
                    int employeeIndex = scanner.nextInt() - 1;
                    scanner.nextLine(); // Consume newline

                    if (employeeIndex >= 0 && employeeIndex < employees.size()) {
                        Employee originalEmployee = employees.get(employeeIndex);

                        // Clone the original employee
                        Employee clonedEmployee = originalEmployee.clone();

                        // Update attributes of the cloned employee
                        System.out.println("Enter new details (leave blank to keep original):");

                        System.out.print("Name (" + clonedEmployee.name + "): ");
                        String newName = scanner.nextLine();
                        if (!newName.isBlank()) clonedEmployee.name = newName;

                        System.out.print("Role (" + clonedEmployee.role + "): ");
                        String newRole = scanner.nextLine();
                        if (!newRole.isBlank()) clonedEmployee.role = newRole;

                        System.out.print("Salary (" + clonedEmployee.salary + "): ");
                        String salaryInput = scanner.nextLine();
                        if (!salaryInput.isBlank()) clonedEmployee.salary = Double.parseDouble(salaryInput);

                        System.out.print("Department (" + clonedEmployee.department + "): ");
                        String newDepartment = scanner.nextLine();
                        if (!newDepartment.isBlank()) clonedEmployee.department = newDepartment;

                        // Replace the original employee in the database
                        employees.set(employeeIndex, clonedEmployee);
                        System.out.println("Employee details updated successfully!");
                    } else {
                        System.out.println("Invalid employee selection.");
                    }
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
