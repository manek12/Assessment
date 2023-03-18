package HoliAssessment;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {
    // Max marks to store maximum marks for that particular test;
    // obtained marks will be set by the evaluator after comparing key and sheet;
    // answerSheet will be fetched on user request;
    // rightChoices String array to show the right answers by the user;
    private int maxMarks;
    private int obtainedMarks;
    private File answerSheet;
    private List<String> rightChoices;
    private String examName;
    private String examTime;

    public Result(int maxMarks, int obtainedMarks, File answerSheet, List<String> rightChoices,String examTime,String examName) {
        this.maxMarks = maxMarks;
        this.obtainedMarks = obtainedMarks;
        this.answerSheet = answerSheet;
        this.rightChoices = rightChoices;
        this.examTime=examTime;
        this.examName=examName;
    }


    // getter and setters;
    public File getAnswerSheet() {
        return answerSheet;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    public int getObtainedMarks() {
        return obtainedMarks;
    }

    public String getRightChoices() {
        String choices = "";
        for (String choice : rightChoices) {
            choices += choice + "\n";
        }
        return choices;
    }

    public String toString() {
        return "Result: " + "\nExam Name:" + getExamName() + "\nAttempted at: " + getExamTime() + "\nMaximum Marks: " + getMaxMarks() + "\nMarks Obtained: " + getObtainedMarks() + "\nRightChoices:\n" + getRightChoices();
    }

    private String getExamTime() {
        return examTime;
    }

    private String getExamName() {
        return examName;
    }
}
