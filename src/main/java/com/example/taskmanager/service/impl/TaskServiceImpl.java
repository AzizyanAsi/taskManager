package com.example.taskmanager.service.impl;

import com.example.taskmanager.converter.TaskConverter;
import com.example.taskmanager.converter.dataTransferObjects.TaskRequest;
import com.example.taskmanager.converter.dataTransferObjects.UpdateTaskRequest;
import com.example.taskmanager.domain.enums.StatusEnum;
import com.example.taskmanager.domain.Task;
import com.example.taskmanager.domain.User;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;
    private final TaskConverter taskConverter;
    private final UserService userService;

    @Value("${notification.update.status}")
    private String statusNotification;

    @Value("${notification.update.assignee}")
    private String assigneeNotification;

    @Override
    @Transactional
    public Task createTask(TaskRequest taskRequest) {
        LOGGER.debug("User with  : {} user id requested to create task", taskRequest.getReporterId());
        return taskRepository.save(taskConverter.convertToEntity(taskRequest));

    }

    @Override
    @Transactional
    public Task updateAssignee(Long id, UpdateTaskRequest taskRequest) {
        LOGGER.info("Requested to update assigned user for task  {}", id);
        final Task task = this.taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found by this id {}", id));
        User user = userService.findById(taskRequest.getUpdaterId());

        if (user.equals(task.getReporter()) || user.equals(task.getAssignee())) {
            Long oldAssigneeId = task.getAssignee().getId();
            task.setAssignee(userService.findById(taskRequest.getAssigneeId()));
            Map<String, String> groupUsers = groupUsersForChaneAssigneeNotify(task, oldAssigneeId);
            LOGGER.info("trying to send notification to users {} ", groupUsers);
            userService.sendNotificationToUsers(groupUsers, assigneeNotification);
        } else throw new RuntimeException("Only reporter or assignee can change assigner in the task");
        LOGGER.info("Assignee for task {} is successfully updated", id);
        return taskRepository.save(task);
    }

    private Map<String, String> groupUsersForChaneAssigneeNotify(Task task, Long oldAssigneeId) {
        Map<String, String> userIds = new HashMap<>();
        userIds.put("taskReporterId", String.valueOf(task.getReporter().getId()));
        userIds.put("oldAssigneeId", String.valueOf(oldAssigneeId));
        userIds.put("newAssigneeId", String.valueOf(task.getAssignee().getId()));
        return userIds;
    }

    @Override
    @Transactional
    public Task updateTaskStatus(Long id, UpdateTaskRequest taskRequest) {
        LOGGER.info("Requested to update status for task  {}", id);
        final Task task = this.taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found by this id {}", id));
        User user = userService.findById(taskRequest.getUpdaterId());
        if (user.equals(task.getAssignee())) {
            task.setStatus(StatusEnum.valueOf(taskRequest.getStatus()));
            Map<String, String> userIds = groupUsersForNotify(task);
            userService.sendNotificationToUsers(userIds, statusNotification);
        } else throw new RuntimeException("Only  assignee can change status for the task");
        LOGGER.info("Status for task {} is successfully updated", id);
        return taskRepository.save(task);
    }

    public static Map<String, String> groupUsersForNotify(Task task) {
        Map<String, String> userIds = new HashMap<>();
        userIds.put("taskReporterId", String.valueOf(task.getReporter().getId()));
        userIds.put("assigneeId", String.valueOf(task.getAssignee().getId()));
        LOGGER.info("Grouped users {} for sending notification", userIds);
        return userIds;
    }


    @Override
    @Transactional(readOnly = true)
    public Task findById(Long id) {
        LOGGER.debug("In findById requested to get the task with  id: {}", id);
        return this.taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found by this id: {}", id));
    }

}

