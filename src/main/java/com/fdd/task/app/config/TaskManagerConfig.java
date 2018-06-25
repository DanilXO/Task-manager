package com.fdd.task.app.config;

import com.fdd.task.app.controller.TaskManagerController;
import com.fdd.task.app.model.CurrentTasks;
import com.fdd.task.app.model.Task;
import com.fdd.task.app.model.TaskManager;
import com.fdd.task.app.service.NotificationService;
import com.fdd.task.app.service.TaskService;
import com.fdd.task.app.service.TaskServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class TaskManagerConfig extends AbstractModule {
    @Override
    protected void configure() {
        bind(TaskService.class).to(TaskServiceImpl.class).in(Singleton.class);
        bind(NotificationService.class).in(Singleton.class);
        bind(TaskManagerController.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    public Marshaller createMarshaller() throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(TaskManager.class, Task.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return jaxbMarshaller;
    }

    @Provides
    @Singleton
    public Unmarshaller createUnmarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(TaskManager.class, Task.class);
        return jaxbContext.createUnmarshaller();
    }
}
