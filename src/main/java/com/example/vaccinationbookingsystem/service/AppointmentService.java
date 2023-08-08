package com.example.vaccinationbookingsystem.service;

import com.example.vaccinationbookingsystem.Model.Appointment;
import com.example.vaccinationbookingsystem.Model.Doctor;
import com.example.vaccinationbookingsystem.Model.Person;
import com.example.vaccinationbookingsystem.Model.VaccinationCenter;
import com.example.vaccinationbookingsystem.dto.RequestDto.BookAppointmentRequestDto;
import com.example.vaccinationbookingsystem.dto.ResponseDto.BookAppointmentResponseDto;
import com.example.vaccinationbookingsystem.dto.ResponseDto.CenterResponseDto;
import com.example.vaccinationbookingsystem.exception.DoctorNotFoundException;
import com.example.vaccinationbookingsystem.exception.PersonNotFoundException;
import com.example.vaccinationbookingsystem.repository.AppointmentRepository;
import com.example.vaccinationbookingsystem.repository.DoctorRepository;
import com.example.vaccinationbookingsystem.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentService {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    JavaMailSender javaMailSender;
    public BookAppointmentResponseDto bookAppointment(BookAppointmentRequestDto bookAppointmentRequestDto) {

        Optional<Person>personOptional=personRepository.findById(bookAppointmentRequestDto.getPersonId());
        if(personOptional.isEmpty()){
            throw new PersonNotFoundException("Invalid personId");
        }
        Optional<Doctor>doctorOptional=doctorRepository.findById(bookAppointmentRequestDto.getDoctorId());
        if(doctorOptional.isEmpty()){
            throw new DoctorNotFoundException("Invalid doctorId");
        }
        Person person=personOptional.get();
        Doctor doctor=doctorOptional.get();

        // create an appointment
        Appointment appointment=new Appointment();
        appointment.setAppointmentId(String.valueOf(UUID.randomUUID()));
        appointment.setPerson(person);
        appointment.setDoctor(doctor);

        Appointment savedAppointment=appointmentRepository.save(appointment);

        doctor.getAppointments().add(savedAppointment);
        person.getAppointments().add(savedAppointment);

        Doctor savedDoctor=doctorRepository.save(doctor);
        Person savedPerson=  personRepository.save(person);
        VaccinationCenter center=savedDoctor.getCenter();


        //send an email
        String text="Congrats!! "+savedPerson.getName()+"Your appointment has been book with doctor "+
                savedDoctor.getName()+". your vaccination center name is:" + center.getCenterName()+"Please reach at this address"
                +center.getAddress() + " at this time: " + savedAppointment.getAppointmentDate()+ "Dhanyawad!!!";

        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom("testmailbackend604@gmail.com");
        simpleMailMessage.setTo(savedPerson.getEmailId());
        simpleMailMessage.setSubject("Congrats!! Appointment Done!!");
        simpleMailMessage.setText(text);


        javaMailSender.send(simpleMailMessage);

        // prepRE THE REquest dto


        CenterResponseDto centerResponseDto=new CenterResponseDto();
        centerResponseDto.setCenterName(center.getCenterName());
        centerResponseDto.setAddress(center.getAddress());
        centerResponseDto.setCenterType(center.getCenterType());

        BookAppointmentResponseDto bookAppointmentResponseDto=new BookAppointmentResponseDto();
        bookAppointmentResponseDto.setPersonName(savedPerson.getName());
        bookAppointmentResponseDto.setDoctorName(savedDoctor.getName());
        bookAppointmentResponseDto.setAppointmentId(savedAppointment.getAppointmentId());
        bookAppointmentResponseDto.setAppointmentDate(savedAppointment.getAppointmentDate());
        bookAppointmentResponseDto.setCenterResponseDto(centerResponseDto);

        return bookAppointmentResponseDto;

    }
}







