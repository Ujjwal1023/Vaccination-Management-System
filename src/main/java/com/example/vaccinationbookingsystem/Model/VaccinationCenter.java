package com.example.vaccinationbookingsystem.Model;

import com.example.vaccinationbookingsystem.Enum.CenterType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VaccinationCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int Id;
    String centerName;
    @Enumerated(value = EnumType.STRING)
    CenterType centerType;
    String address;
    @OneToMany(mappedBy = "center",cascade = CascadeType.ALL)
    List<Doctor>doctors=new ArrayList<>();
}
