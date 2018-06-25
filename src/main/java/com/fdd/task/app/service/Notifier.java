package com.fdd.task.app.service;

import com.fdd.task.app.model.Task;
import java.util.List;

public class Notifier {
    public static void notifyAboutDeadline(List<Task> currentTasks){
        for (Task task : currentTasks) {
            if(isDeadline(task)){
                NotificationService.showWarningNotification("Dedline","Time to complete the task: "
                        +task.getName()+" ended!",task.toString());
            }else if(isLittleTime(task)){
                NotificationService.showWarningNotification("You don't have enough time!","Hurry! The time for the task: "
                        +task.getName()+" ends! You have less than a day left!",task.toString());
            }
        }
    }
    private static int getDifferentHours(Task task){
        long diff =task.getDate().getTime() - System.currentTimeMillis();
        return (int) diff / (60 * 60 * 1000) % 24;
    }
    private static boolean isDeadline(Task task){
        return (task.getDate().getTime() - System.currentTimeMillis())<=0;
    }
    private static boolean isLittleTime(Task task){
        return (getDifferentHours(task)<=24 && getDifferentHours(task)>0);
    }
}
