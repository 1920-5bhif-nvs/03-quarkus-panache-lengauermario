package at.htl.cinema.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@Entity
public class Employee extends Person{

    public double salary;
    public String personalNumber;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate employedSince;

    @ManyToOne(
            cascade = { CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.EAGER
    )
    public Cinema cinema;

    public Employee() {
    }

    public Employee(String firstName, String lastName, double salary, String personalNumber, LocalDate employedSince) {
        super(firstName, lastName);
        this.salary = salary;
        this.personalNumber = personalNumber;
        this.employedSince = employedSince;
    }

    public Employee(String firstName, String lastName, double salary, String personalNumber, LocalDate employedSince, Cinema cinema) {
        super(firstName, lastName);
        this.salary = salary;
        this.personalNumber = personalNumber;
        this.employedSince = employedSince;
        this.cinema = cinema;
    }
}
