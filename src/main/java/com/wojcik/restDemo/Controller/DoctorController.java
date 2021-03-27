package com.wojcik.restDemo.controller;

import com.wojcik.restDemo.entity.Comment;
import com.wojcik.restDemo.entity.Doctor;
import com.wojcik.restDemo.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    private DoctorService service;

    @RequestMapping(method = RequestMethod.POST)
    public void addDoctor(@RequestBody Doctor doctor) {
        service.save(doctor);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Doctor> getDoctors() {
        return service.findAll();
    }

    @GetMapping("doctor/{id}")
    public Doctor getDoctor(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping(value = "doctor/{doctorId}/comment")
    public void commentOnDoctor(@PathVariable Integer doctorId, @RequestBody Comment comment) {
        service.commentOnDoctor(doctorId, comment);

    }
}
