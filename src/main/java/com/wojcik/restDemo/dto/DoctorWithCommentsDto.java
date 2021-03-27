package com.wojcik.restDemo.dto;

import java.util.List;

public class DoctorWithCommentsDto {
    private DoctorDto doctorDto;
    private List<CommentDto> comments;

    public DoctorWithCommentsDto(DoctorDto doctorDto, List<CommentDto> comments) {
        this.doctorDto = doctorDto;
        this.comments = comments;
    }


    public DoctorDto getDoctorDto() {
        return doctorDto;
    }

    public void setDoctorDto(DoctorDto doctorDto) {
        this.doctorDto = doctorDto;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }
}
