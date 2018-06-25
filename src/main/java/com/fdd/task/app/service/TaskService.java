package com.fdd.task.app.service;

import com.fdd.task.app.model.Task;

import java.io.IOException;
import java.util.List;

public interface TaskService {
    List<Task> loadTasks() throws IOException;

    void saveTasks(List<Task> savetasks);
}
