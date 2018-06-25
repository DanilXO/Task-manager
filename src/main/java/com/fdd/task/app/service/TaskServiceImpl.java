package com.fdd.task.app.service;

import com.fdd.task.app.exception.TaskManagerException;
import com.fdd.task.app.model.Task;
import com.fdd.task.app.model.TaskManager;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class TaskServiceImpl implements TaskService {
    private static final String FILE_NAME = "data.xml";
    private final Unmarshaller unmarshaller;
    private final Marshaller marshaller;

    @Inject
    public TaskServiceImpl(Unmarshaller unmarshaller,
                           Marshaller marshaller) {
        this.unmarshaller = unmarshaller;
        this.marshaller = marshaller;
    }

    @Override
    public List<Task> loadTasks() {
        if (!Files.exists(Paths.get(FILE_NAME))) {
            return Collections.emptyList();
        }
        try {
            TaskManager taskManager = (TaskManager) unmarshaller.unmarshal(new FileInputStream(FILE_NAME));
            return taskManager.getTasks();
        } catch (FileNotFoundException e) {
            throw new TaskManagerException(String.format("File not found %s", FILE_NAME), e);
        } catch (JAXBException e) {
            throw new TaskManagerException("Problem parsing JAXB context", e);
        }
    }
    
    public void saveTasks(List<Task> tasks) {

        try {
            marshaller.marshal(new TaskManager(tasks), new FileOutputStream(FILE_NAME));
        } catch (FileNotFoundException e) {
            throw new TaskManagerException(String.format("File not found %s", FILE_NAME), e);
        } catch (JAXBException e) {
            throw new TaskManagerException("problem parsing JAXB context", e);
        }
    }
}
