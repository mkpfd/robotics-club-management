package com.roboticsclub.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="event_id", nullable=false)
    private Event event;

    @ManyToOne
    @JoinColumn(name="member_id", nullable=false)
    private Member member;

    @Column(name="attendance_date", nullable=false)
    private LocalDate attendanceDate;

    @Column(nullable=false)
    private String status;

    public Attendance(){}

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id=id;
    }

    public Event getEvent(){
        return event;
    }

    public void setEvent(Event event){
        this.event=event;
    }

    public Member getMember(){
        return member;
    }

    public void setMember(Member member){
        this.member=member;
    }

    public LocalDate getAttendanceDate(){
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate){
        this.attendanceDate=attendanceDate;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status=status;
    }
}