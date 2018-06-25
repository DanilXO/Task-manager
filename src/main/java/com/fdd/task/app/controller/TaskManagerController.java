package com.fdd.task.app.controller;

import com.cathive.fx.guice.FXMLController;
import com.fdd.task.app.model.CurrentTasks;
import com.fdd.task.app.model.Task;
import com.fdd.task.app.service.NotificationService;
import com.fdd.task.app.service.Notifier;
import com.fdd.task.app.service.TaskService;
import com.google.common.base.Strings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.text.ParseException;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

@FXMLController
public class TaskManagerController {
    @FXML
    private TableView<Task> tasksTable;
    @FXML
    private TableColumn<Task, String> nameColumn;
    @FXML
    private TableColumn<Task, String> descriptionColumn;
    @FXML
    private TableColumn<Task, String> dateColumn;
    @FXML
    private TextField NameField;
    @FXML
    private TextArea DescriptionArea;
    @FXML
    private TextField TimeField;
    @FXML
    private DatePicker datePicker;

    private final TaskService taskService;
    @Inject
    public TaskManagerController(TaskService taskService) {
        this.taskService = taskService;
    }

    @FXML
    void initialize() throws IOException {
        CurrentTasks.setTasks(FXCollections.observableArrayList(taskService.loadTasks()));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Task,String>("Name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Task,String>("Description"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Task,String>("Date"));
        tasksTable.setItems(CurrentTasks.getTasks());
        Notifier.notifyAboutDeadline(CurrentTasks.getTasks());
    }
    /**
     * Метод добавления в таблицу
     * */
    public void clickAdd() throws ParseException {

        StringBuilder errorMessage = new StringBuilder();
        if (Strings.isNullOrEmpty(NameField.getText())) {
            errorMessage.append("No valid task's name!\n");
        }
        if (datePicker.getValue()==null) {
            errorMessage.append("No valid task's date!\n");
        }
        if (Strings.isNullOrEmpty(TimeField.getText())) {
            errorMessage.append("No valid task's time!\n");
        }

        if (Strings.isNullOrEmpty(DescriptionArea.getText())) {
            errorMessage.append("No valid task's  description!\n");
        }

        if (Strings.isNullOrEmpty(errorMessage.toString())) {
            Pattern pattern = Pattern.compile(":");
            String[] time = pattern.split(TimeField.getText());
            Date date = Date.from(datePicker.getValue()
                        .atTime(Integer.parseInt(time[0]),Integer.parseInt(time[1]))
                        .atZone(ZoneId.systemDefault()).toInstant());
            CurrentTasks.addTask(new Task(NameField.getText(),DescriptionArea.getText(),date));
            NotificationService.showInfoNotification("Add a task","The task was added");
        } else {
            NotificationService.showErrorNotification("Invalid Fields","Please correct invalid fields");
        }
    }

    /**
     * Метод удаления из таблицы
     * */
    public void clickDelete() {
        try {
            int selectedIndex = tasksTable.getSelectionModel().getSelectedIndex();
            CurrentTasks.removeTask(selectedIndex);
            NotificationService.showInfoNotification("Delete task","The task was deleted");
        } catch (Exception e){
            NotificationService.showWarningNotification("No selection","No task selected!");
        }
    }
}
