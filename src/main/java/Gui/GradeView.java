package Gui;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GradeView {
//    extends
//} VBox {

    private final StringProperty subject = new SimpleStringProperty();
    private final StringProperty gradeTitle = new SimpleStringProperty();
    private final StringProperty grade = new SimpleStringProperty();
    private final StringProperty date = new SimpleStringProperty();

    public GradeView(String subject, String gradeTitle, String grade, String date) {
        this.subject.set(subject);
        this.gradeTitle.set(gradeTitle);
        this.grade.set(grade);
        this.date.set(date);
    }

    // Subject Property
    public String getSubject() {
        return subject.get();
    }
    public void setSubject(String subject) {
        this.subject.set(subject);
    }
    public StringProperty subjectProperty() {
        return subject;
    }

    // Gradetitle Property
    public String getGradeTitle() {
        return gradeTitle.get();
    }
    public void setGradeTitle(String gradeTitle) {
        this.gradeTitle.set(gradeTitle);
    }
    public StringProperty gradeTitleProperty() {
        return gradeTitle;
    }

    // Grade Property
    public String getGrade() {
        return grade.get();
    }
    public void setGrade(String grade) {
        this.grade.set(grade);
    }
    public StringProperty gradeProperty() {
        return grade;
    }

    // Date Property
    public String getDate() {
        return date.get();
    }
    public void setDate(String date) {
        this.date.set(date);
    }
    public StringProperty dateProperty() {
        return date;
    }

//    private Label subjectLabel;
//    private Label gradeLabel;
//    private Label dateLabel;
//
//    public GradeView(String subject, String grade, String date) {
//        // Initialize UI components
//        subjectLabel = new Label("Subject: " + subject);
//        gradeLabel = new Label("Grade: " + grade);
//        dateLabel = new Label("Date: " + date);
//
//        // Add components to the layout
//        getChildren().addAll(subjectLabel, gradeLabel, dateLabel);
//
//        // Add styling or additional customization if needed
//        // For example, you might set styles, fonts, or spacing here.
//    }
}
