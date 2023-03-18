package HoliAssessment;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Evaluator {
    public void evaluate(String answerSheetPath, String answerKeyPath, Student student,String examName) {
        int marksObtained = 0;
        int maxMarks = 0;
        List<String> rightChoices = new ArrayList<>();
        File answerSheet = new File(answerSheetPath);

        try (FileReader fr1 = new FileReader(answerSheetPath);
             FileReader fr2 = new FileReader(answerKeyPath);
             BufferedReader br1 = new BufferedReader(fr1);
             BufferedReader br2 = new BufferedReader(fr2);
        ) {
            String line1 = "";
            String line2 = "";

            while ((line1 = br2.readLine()) != null) {
                line2 = br1.readLine();
                maxMarks++;
                if (line1.equals(line2))
                    rightChoices.add(line1);
            }

            marksObtained = rightChoices.size();

            Date date = new Date();
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm");
            String examTime = sdf1.format(date) + " " + sdf2.format(date);

            Result result = new Result(maxMarks, marksObtained, answerSheet, rightChoices,examTime,examName);
            student.addResult(result);
            new Admin("evaluator");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}