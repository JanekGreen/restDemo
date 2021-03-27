package com.wojcik.restDemo.service.Impl;

import com.wojcik.restDemo.entity.Comment;
import com.wojcik.restDemo.entity.Doctor;
import com.wojcik.restDemo.repository.DoctorRepository;
import com.wojcik.restDemo.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    DoctorRepository doctorRepository;

    @Override
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor findById(Integer id) {
        return doctorRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void save(Doctor doctor) {
        doctorRepository.save(doctor);

    }

    @Override
    public void commentOnDoctor(Integer doctorId, Comment comment) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(NoSuchElementException::new);
        linkCommentToDoctor(comment, doctor);
        doctorRepository.save(doctor);
    }

    private void linkCommentToDoctor(Comment comment, Doctor doctor) {
        comment.setDoctor(doctor);
        doctor.getComments().add(comment);
    }

}
