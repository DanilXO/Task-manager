package com.fdd.task.app.exception;

public class TaskManagerException extends RuntimeException {
    private String messageInUi;
    private String titleInUI;
    private TypeUserInterfaceNotification typeUserInterfaceNotification;

    public TaskManagerException(String message) {
        super(message);
    }

    public TaskManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskManagerException(String message,
                                Throwable cause,
                                String messageInUi,
                                String titleInUI,
                                TypeUserInterfaceNotification typeUserInterfaceNotification) {
        super(message, cause);
        this.messageInUi = messageInUi;
        this.titleInUI = titleInUI;
        this.typeUserInterfaceNotification = typeUserInterfaceNotification;
    }

    public String getMessageInUi() {
        return messageInUi;
    }

    public String getTitleInUI() {
        return titleInUI;
    }

    public TypeUserInterfaceNotification getTypeUserInterfaceNotification() {
        return typeUserInterfaceNotification;
    }
}
