package com.wojcik.restDemo.mapstruct;

import com.wojcik.restDemo.dto.CommentDto;
import com.wojcik.restDemo.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toComment(CommentDto commentDto);
    CommentDto toCommentDto(Comment comment);
}
