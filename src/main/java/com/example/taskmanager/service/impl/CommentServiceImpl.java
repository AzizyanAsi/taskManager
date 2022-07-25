package com.example.taskmanager.service.impl;

import com.example.taskmanager.converter.CommentConverter;
import com.example.taskmanager.converter.dataTransferObjects.CommentRequest;
import com.example.taskmanager.domain.Comment;
import com.example.taskmanager.domain.Task;
import com.example.taskmanager.domain.User;
import com.example.taskmanager.repository.CommentRepository;
import com.example.taskmanager.service.CommentService;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.example.taskmanager.service.impl.TaskServiceImpl.groupUsersForNotify;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;
    private final CommentConverter commentConverter;
    private final UserService userService;
    private final TaskService taskService;

    @Value("${notification.add.comment}")
    private String commentNotification;

    @Override
    @Transactional
    public Comment createComment(CommentRequest commentRequest) {
        LOGGER.debug("User with  : {} user id requested to add comment", commentRequest.getCreatorId());
        final User user = userService.findById(commentRequest.getCreatorId());
        final Task task = taskService.findById(commentRequest.getTaskId());

        if (user.equals(task.getAssignee())) {
            Comment comment = commentRepository.save(commentConverter.convertToEntity(commentRequest));

            Map<String, String> groupUsers = groupUsersForNotify(task);
            userService.sendNotificationToUsers(groupUsers, commentNotification);
            return comment;
        } else throw new RuntimeException("comment can create only assignee");
    }

}
