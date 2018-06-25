package com.fdd.task.app;

import com.cathive.fx.guice.GuiceApplication;
import com.cathive.fx.guice.GuiceFXMLLoader;
import com.fdd.task.app.config.TaskManagerConfig;
import com.fdd.task.app.model.CurrentTasks;
import com.fdd.task.app.service.ErrorHandling;
import com.fdd.task.app.service.Notifier;
import com.fdd.task.app.service.TaskService;
import com.google.inject.Module;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.util.List;

public class TaskManagerApplication extends GuiceApplication {
    @Inject
    private GuiceFXMLLoader fxmlLoader;
    @Inject
    private TaskService taskService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Thread.setDefaultUncaughtExceptionHandler(ErrorHandling::errorHandling);
        try
        {
            Parent root = fxmlLoader.load(getClass().getResource("/view/application.fxml")).getRoot();
            primaryStage.setScene(new Scene(root, primaryStage.getWidth(), primaryStage.getHeight()));
            primaryStage.setTitle("Task Manager");
            primaryStage.show();
        }
        catch (Exception e)
        {
            ErrorHandling.errorHandling(Thread.currentThread(), e);
        }
    }

    @Override
    public void init(List<Module> list) {
        list.add(new TaskManagerConfig());
    }

    @Override
    public void stop() throws Exception {
        taskService.saveTasks(CurrentTasks.getTasks());
    }

}
