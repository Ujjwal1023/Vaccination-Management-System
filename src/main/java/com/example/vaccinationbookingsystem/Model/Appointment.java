package com.example.vaccinationbookingsystem.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int Id;

    String appointmentId;
    @CreationTimestamp
    Date appointmentDate;
    @ManyToOne
    @JoinColumn
    Person person;

    @ManyToOne
    @JoinColumn
    Doctor doctor;
}
