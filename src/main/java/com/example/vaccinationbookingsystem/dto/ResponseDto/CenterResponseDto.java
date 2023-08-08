package com.example.vaccinationbookingsystem.dto.ResponseDto;

import com.example.vaccinationbookingsystem.Enum.CenterType;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CenterResponseDto {

    String centerName;
    CenterType centerType;
    String address;
}
