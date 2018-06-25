package com.fdd.task.app.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
/**
 * Вспомогательный класс обертка для сохранения задач в xml
 * */
@XmlRootElement(name = "task-manager")
@XmlType(propOrder = { "tasks" })
public class TaskManager {
    private List<Task> tasks;

    public TaskManager(){
        tasks = new ArrayList<>();
    }

    public TaskManager(List<Task> tasks) {
        this.tasks = tasks;
    }

    @XmlElement(name = "task", type = Task.class)
    @XmlElementWrapper
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
