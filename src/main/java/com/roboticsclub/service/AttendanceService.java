package com.roboticsclub.service;

import com.roboticsclub.model.Attendance;
import com.roboticsclub.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository){
        this.attendanceRepository = attendanceRepository;
    }

    public List<Attendance> getAllAttendance(){
        return attendanceRepository.findAll();
    }

    public Attendance saveAttendance(Attendance attendance){
        return attendanceRepository.save(attendance);
    }

    public Attendance getAttendanceById(Long id){
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Attendance not found"));
    }

    public void deleteAttendance(Long id){
        attendanceRepository.deleteById(id);

    }
}