package com.fdd.task.app.model;

import javafx.collections.ObservableList;

/**
 * Одиночка, для хранения списка задач
 */
public class CurrentTasks {
    private static ObservableList<Task> tasks;
    private CurrentTasks(){
    }
    public static ObservableList getTasks() {
        return tasks;
    }

    public static void setTasks(ObservableList<Task> tasks) {
        CurrentTasks.tasks = tasks;
    }
    public static void addTask(Task newTask){
        CurrentTasks.tasks.add(newTask);
    }
    public static void removeTask(int index){
        CurrentTasks.tasks.remove(index);
    }
}
