package ru.elagin.hibernate.models;

import org.springframework.format.annotation.DateTimeFormat;
import ru.elagin.hibernate.validation.UniqueTabnum;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@UniqueTabnum
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "tabnum")
    @Min(value = 0, message = "Tabnum should not be negative")
    private Integer tabnum;

    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Pattern(regexp = "[а-яА-Яa-zA-z]+(\\s|-)?([а-яА-Яa-zA-z]+)?", message = "Name should not contains this the characters")
    private String name;

    @Column(name = "surname")
    @NotEmpty(message = "Surname should not be empty")
    @Size(min = 2, max = 30, message = "Surname should be between 2 and 30 characters")
    @Pattern(regexp = "[а-яА-Яa-zA-z]+(\\s|-)?([а-яА-Яa-zA-z]+)?", message = "Surname should not contains this the characters")
    private String surname;

    @Column(name = "email")
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "birth")
    @PastOrPresent(message = "Date should not be a future time")
    @NotNull(message = "Date should not be empty")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    private LocalDate birth;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Auto> autos;

    public Customer() {
    }

    public Customer(Integer id, Integer tabnum, String name, String surname, String email, LocalDate birth) {
        this.id = id;
        this.tabnum = tabnum;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birth = birth;
        this.autos = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTabnum() {
        return tabnum;
    }

    public void setTabnum(Integer tabnum) {
        this.tabnum = tabnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public List<Auto> getAutos() {
        return autos;
    }

    public void setAutos(List<Auto> autos) {
        this.autos = autos;
    }

}
