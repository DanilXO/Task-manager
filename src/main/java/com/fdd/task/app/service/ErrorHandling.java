package com.fdd.task.app.service;

import com.fdd.task.app.exception.TaskManagerException;
import com.fdd.task.app.exception.TypeUserInterfaceNotification;
import javafx.fxml.LoadException;
import javafx.scene.control.Alert;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class ErrorHandling {

    public static void errorHandling(Thread t, Throwable e) {
        System.err.println("***Default exception handler***");

        verifyCauseLoadException(e);

        e.printStackTrace();
    }

    private static void verifyCauseLoadException(Throwable throwable)
    {
        if (throwable instanceof LoadException)
        {
            LoadException loadException = (LoadException)throwable;
            if (loadException.getCause() instanceof InvocationTargetException)
            {
                InvocationTargetException invocationTargetException = (InvocationTargetException) loadException.getCause();
                if (invocationTargetException.getCause() instanceof TaskManagerException)
                {
                    showApplicationException((TaskManagerException)invocationTargetException.getCause());
                }
            }
        }
        else if (throwable instanceof TaskManagerException)
        {
            showApplicationException((TaskManagerException) throwable);
        }
    }

    private static void showApplicationException(TaskManagerException exception)
    {
        Alert alert = new Alert(convertException(
                Optional.ofNullable(exception.getTypeUserInterfaceNotification())
                        .orElse(TypeUserInterfaceNotification.ERROR))
        );
        alert.setTitle(Optional.ofNullable(exception.getTitleInUI()).orElse("Unknow error"));
        alert.setContentText(Optional.ofNullable(exception.getMessageInUi()).orElse(exception.getMessage()));
        alert.showAndWait();
    }

    private static Alert.AlertType convertException(TypeUserInterfaceNotification typeUserInterfaceNotification)
    {
        switch (typeUserInterfaceNotification)
        {
            case ERROR:
                return Alert.AlertType.ERROR;
            case WARNING:
                return Alert.AlertType.WARNING;
            default:
                throw new IllegalStateException(String.format("Not supported notification type %s", typeUserInterfaceNotification));
        }
    }
}
