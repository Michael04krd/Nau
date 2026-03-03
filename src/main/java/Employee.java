import java.util.ArrayList;

public class Employee {
    private String fullName;
    private Integer age;
    private String department;
    private Double salary;

    public Employee(String fullName, Integer age, String department, Double salary) {
        this.fullName = fullName;
        this.age = age;
        this.department = department;
        this.salary = salary;
    }

    public String getFullName() { return fullName; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public Integer getAge() { return age; }

    public void setAge(Integer age) { this.age = age; }

    public String getDepartment() { return department; }

    public void setDepartment(String department) { this.department = department; }

    public Double getSalary() { return salary; }

    public void setSalary(Double salary) { this.salary = salary; }

    public static void main(String[] args) {
        ArrayList<Employee> company = new ArrayList<>(6);
        company.add(new Employee("Александр", 18, "Менеджмент", 20000.0));
        company.add(new Employee("Виктор", 23, "Реклама", 27000.0));
        company.add(new Employee("Анастасия", 45, "Бухгалтерия", 40000.0));
        company.add(new Employee("Станислав", 33, "Менеджмент", 31000.0));
        company.add(new Employee("Виолета", 25, "Реклама", 35000.0));
        company.add(new Employee("Анатолий", 39, "Бухгалтерия", 110000.0));
        System.out.println("Все зарплаты которые есть:");
        for (Employee employee: company) {
            System.out.printf(employee.getSalary().toString() + " ");
        }

        boolean flag = company.stream()
                .noneMatch(employee -> employee.getSalary() > 100000.0);
        if (flag) {
            System.out.println("\nНи у кого нет зарплаты больше 100.000");
            return;
        }
        System.out.println("\nЕсть хотя бы 1 сотрудник с зарплатой больше 100.000");
    }
}