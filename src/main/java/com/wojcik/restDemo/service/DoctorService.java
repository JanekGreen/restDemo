package com.wojcik.restDemo.service;

import com.wojcik.restDemo.entity.Comment;
import com.wojcik.restDemo.entity.Doctor;

import java.util.List;

public interface DoctorService {
    List<Doctor> findAll();

    Doctor findById(Integer id);

    void save(Doctor doctor);

    void commentOnDoctor(Integer doctorId, Comment comment);

    void deleteDoctor(Integer id);

    Doctor editDoctor(Integer doctorId, Doctor doctor);


}
