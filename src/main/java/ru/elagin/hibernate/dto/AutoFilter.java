package ru.elagin.hibernate.dto;

import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.time.LocalDate;

@Value
public class AutoFilter {
    @Min(value = 0, message = "Limit should not be negative")
    Integer limit;

    @Min(value = 0, message = "Offset should not be negative")
    Integer offset;

    String model;

    String color;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")  // yy
    LocalDate year;

}
