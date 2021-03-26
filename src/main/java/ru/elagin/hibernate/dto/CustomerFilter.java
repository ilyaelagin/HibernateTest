package ru.elagin.hibernate.dto;

import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.time.LocalDate;

@Value
public class CustomerFilter {

    @Min(value = 0, message = "Limit should not be negative")
    Integer limit;

    @Min(value = 0, message = "Offset should not be negative")
    Integer offset;

    @Min(value = 0, message = "Tabnum should not be negative")
    Integer tabnum;

    String name;
    String surname;
    String email;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    LocalDate birth;

}
