package com.example.taskmanager.service;

import com.example.taskmanager.converter.dataTransferObjects.CommentRequest;
import com.example.taskmanager.domain.Comment;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    Comment createComment(CommentRequest commentRequest);

}
