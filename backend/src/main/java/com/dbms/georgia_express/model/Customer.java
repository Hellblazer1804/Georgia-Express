package com.dbms.georgia_express.model;
import jakarta.persistence.*;


@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "customerId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int customerId;

    @Column(name = "name")
    private String name;

    @Column(name = "ssn")
    private String ssn;

    @Column(name = "email")
    private String email;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "creditScore")
    private int creditScore;

    @Column(name = "salary")
    private int salary;

    @Column(name = "dob")
    private String dateOfBirth;

    public Customer() {}

    public Customer(String name, String ssn, String email, String phoneNumber,
                    String address, int creditScore, int salary, String dateOfBirth) {
        this.name = name;
        this.ssn = ssn;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.creditScore = creditScore;
        this.salary = salary;
        this.dateOfBirth = dateOfBirth;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phoneNumber;
    }

    public void setPhone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
