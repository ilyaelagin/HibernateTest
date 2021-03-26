package ru.elagin.hibernate.models;

import org.springframework.format.annotation.DateTimeFormat;
import ru.elagin.hibernate.validation.FKeyCustomers;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "autos")
public class Auto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "model")
    @Size(min = 1, max = 30, message = "Model should be between 1 and 30 characters")
    private String model;

    @Column(name = "color")
    @Size(min = 2, max = 30, message = "Color should be between 2 and 30 characters")
    private String color;

    @Column(name = "year")
    @NotNull(message = "Year should not be empty")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    private LocalDate year;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    @FKeyCustomers
    private Customer customer;

    public Auto() {
    }

    public Auto(int id, String model, String color, LocalDate year) {
        this.id = id;
        this.model = model;
        this.color = color;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
