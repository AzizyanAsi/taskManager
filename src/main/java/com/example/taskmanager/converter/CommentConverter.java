package com.example.taskmanager.converter;

import com.example.taskmanager.converter.dataTransferObjects.CommentResponse;
import com.example.taskmanager.converter.dataTransferObjects.CommentRequest;
import com.example.taskmanager.domain.Comment;

import java.util.List;

public interface CommentConverter {

    Comment convertToEntity(CommentRequest commentRequest);

    CommentResponse convertToDTO(Comment comment);

    List<CommentResponse> bulkConvertToDTO(List<Comment> comments);

    List<Comment> bulkConvertToEntity(List<CommentRequest> commentRequests);
}
