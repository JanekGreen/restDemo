package com.wojcik.restDemo.controller;

import com.wojcik.restDemo.dto.DoctorDto;
import com.wojcik.restDemo.entity.Comment;
import com.wojcik.restDemo.mapstruct.DoctorMapper;
import com.wojcik.restDemo.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    private DoctorService service;
    @Autowired
    private DoctorMapper doctorMapper;


    @RequestMapping(method = RequestMethod.POST)
    public void addDoctor(@RequestBody DoctorDto doctorDto) {
        service.save(doctorMapper.toDoctor(doctorDto));
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<DoctorDto> getDoctors() {
        return service.findAll().stream()
                .map(doctor -> doctorMapper.toDoctorDto(doctor))
                .collect(Collectors.toList());
    }

    @GetMapping("doctor/{id}")
    public DoctorDto getDoctor(@PathVariable Integer id) {
        return doctorMapper.toDoctorDto(service.findById(id));
    }

    @PostMapping(value = "doctor/{doctorId}/comment")
    public void commentOnDoctor(@PathVariable Integer doctorId, @RequestBody Comment comment) {
        service.commentOnDoctor(doctorId, comment);

    }
    @DeleteMapping("doctor/{id}")
    public void deleteDoctor(@PathVariable Integer id){
        service.deleteDoctor(id);

    }
    @PutMapping("doctor/{id}")
    public void editDoctor(@PathVariable Integer id, @RequestBody DoctorDto doctorDto){
        service.editDoctor(id, doctorMapper.toDoctor(doctorDto));

    }
}
