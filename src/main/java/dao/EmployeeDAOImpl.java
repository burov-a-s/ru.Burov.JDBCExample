package dao;

import entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    private final String URL = "jdbc:mysql://127.0.0.1:3306/my_db";
    private final String USERNAME = "bestuser";
    private final String PASSWORD = "bestuser";


    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employees");
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                employee.setSurname(resultSet.getString("surname"));
                employee.setDepartment(resultSet.getString("department"));
                employee.setSalary(resultSet.getInt("salary"));
                employees.add(employee);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employees;
    }

    public void saveEmployee(Employee employee) {
        int id = employee.getId();
        String name = employee.getName();
        String surname = employee.getSurname();
        String department = employee.getDepartment();
        int salary = employee.getSalary();

        String sqlQuery = String.format("INSERT INTO employees (id, name, surname, department, salary) VALUES (%d, '%s', '%s', '%s', %d)", id, name, surname, department, salary);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Employee getEmployeeById(int id) {
        List<Employee> employeeList = this.getAllEmployees();
        for (Employee employee : employeeList) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        System.out.println("employee with id= " + id + " not found");
        return null;
    }

    public void deleteEmployee(int id) {
        String sqlQuery = String.format("DELETE FROM employees WHERE id = %d", id);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
