package com.healthcare.system.healthcare.services;

import com.healthcare.system.healthcare.models.dtos.AppointmentDto;
import com.healthcare.system.healthcare.models.dtos.DepartmentDto;
import com.healthcare.system.healthcare.models.dtos.DoctorDto;
import com.healthcare.system.healthcare.models.dtos.UserDto;
import com.healthcare.system.healthcare.models.entities.Appointment;
import com.healthcare.system.healthcare.models.entities.Department;
import com.healthcare.system.healthcare.models.entities.Doctor;
import com.healthcare.system.healthcare.models.entities.User;
import com.healthcare.system.healthcare.repositories.AppointmentsRepository;
import com.healthcare.system.healthcare.repositories.DepartmentsRepository;
import com.healthcare.system.healthcare.repositories.DoctorRepository;
import com.healthcare.system.healthcare.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientAppointmentService {


    private AppointmentsRepository appointmentsRepository;
    private DepartmentsRepository departmentsRepository;
    private DoctorRepository doctorRepository;
    private UserRepository userRepository;

    public PatientAppointmentService(AppointmentsRepository appointmentsRepository, DepartmentsRepository departmentsRepository, DoctorRepository doctorRepository, UserRepository userRepository) {
        this.appointmentsRepository = appointmentsRepository;
        this.departmentsRepository = departmentsRepository;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
    }

    public AppointmentDto MapToDto(Appointment appointment) {
        return new AppointmentDto(appointment.getAid(), appointment.getPatient(), appointment.getDoctor(), appointment.getRoom(), appointment.getNote(), appointment.getDate(), appointment.getTime(), appointment.getStatus());
    }

    public List<AppointmentDto> getAppointments(Integer uid) {
        List<Appointment> appointments = appointmentsRepository.findByPatientAndStatusIn(uid, List.of("upcoming", "accepted"));

        if (appointments.isEmpty()) {
            return Collections.emptyList();
        }

        return appointments.stream()
                .map(this::MapToDto)
                .collect(Collectors.toList());
    }

    public AppointmentDto getSingleAppointment(Integer aid) {
        Appointment singleAppointment = appointmentsRepository.findById(aid).orElse(null);
        if(singleAppointment != null) {
            return MapToDto(singleAppointment);
        }
        throw new RuntimeException("No appointment found");
    }

    public AppointmentDto bookAppointment(AppointmentDto newAppointment, Integer uid) {
        Appointment appointment = new Appointment();
        appointment.setPatient(uid);
        appointment.setDoctor(newAppointment.getDoctorId());
        appointment.setRoom(null);
        appointment.setNote(newAppointment.getNote());
        appointment.setDate(null);
        appointment.setTime(null);
        appointment.setStatus("upcoming");
        appointmentsRepository.save(appointment);

        AppointmentDto result = new AppointmentDto(appointment.getAid(), appointment.getPatient(), appointment.getDoctor(), appointment.getRoom(), appointment.getNote(), appointment.getDate(), appointment.getTime(), appointment.getStatus());

        return result;
    }

    public AppointmentDto cancelAppointment(Integer aid) {
        Optional<Appointment> optional = appointmentsRepository.findById(aid);
        if(optional.isPresent()) {
            Appointment appointment = optional.get();
            appointment.setStatus("cancelled");
            appointmentsRepository.save(appointment);
            return MapToDto(appointment);
        }
        throw new RuntimeException("Appointment not found");

    }

    public List<DepartmentDto> getDepartments() {
        List<Department> departments = this.departmentsRepository.findAll();
        List <DepartmentDto> departmentDtos = new ArrayList<>();
        for(Department department : departments) {
            DepartmentDto departmentDto = new DepartmentDto(department.getDid(), department.getName(), department.getFloor());
            departmentDtos.add(departmentDto);
        }

        return departmentDtos;
    }

    public List<DoctorDto> getDoctorsByDepartment(Integer did) {
        List<Doctor> doctors = doctorRepository.findByDepartment(did);

        return doctors.stream()
                .map(doc -> new DoctorDto(doc.getDid(), userRepository.findById(doc.getDid()).get().getName()))
                .collect(Collectors.toList());
    }

    public UserDto getDoctorFromUser(Integer did) {
        User user = userRepository.findById(did).orElse(null);
        if(user != null) {
            return new UserDto(user.getUid(), user.getName(), user.getEmail(), user.getPassword(), user.getPhone(), user.getAddress(), user.getCity(), user.getDob(), user.getGender(), user.getBlood_group(), user.getRole(), user.getJmbg());
        }
        throw new RuntimeException("No doctor found");
    }

    public DepartmentDto getDepartmentById(Integer did) {
        Doctor doctor = doctorRepository.findById(did).orElse(null);
        if(doctor != null) {
            Integer deptId = doctor.getDepartment().getDid();
            Department department = departmentsRepository.findById(deptId).orElse(null);
            if(department != null) {
                return new DepartmentDto(department.getDid(), department.getName(), department.getFloor());
            }
            throw new RuntimeException("No department found");
        }
        throw new RuntimeException("No doctor found");
        }

}
