package HoliAssessment;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class ExamHandler implements Serializable{
    Scanner sc=new Scanner(System.in);
    Student student;

    public ExamHandler(Student student){
        this.student=student;
    }

    public void showStudentInterface() throws FileNotFoundException, InterruptedException {
        boolean flag=true;

        while(true) {
            System.out.println("\nWhat operation do you want to perform?");
            System.out.println("Take Exam : 1");
            System.out.println("Change Password : 2");
            System.out.println("Show Results : 3");
            System.out.println("Exit : 4");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice){
                case 1:
                    System.out.println("Please select exam: [java, python, mysql]");
                    String category=sc.nextLine().toLowerCase();
                    String paper="";
                    if(category.equals("java")){
                        paper="java";
                    }
                    else if(category.equals("python")){
                        paper="python";
                    }
                    else if (category.equals("mysql")) {
                        paper="mysql";
                    }
                    else{
                        System.out.println("Please select correct exam...");
                        break;
                    }
                    showQuestionPaper(paper);
                    break;
                case 2:
                    changePassword();
                    break;
                case 3:
                    showResults();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Please enter valid operation...");
                    break;
            }
        }
    }

    private void showResults() {
        List<Result> results=student.getAllResults();

        if(results.size()==0){
            System.out.println("\nYou haven't given any exam till now...\n");
            return;
        }

        results.stream().forEach(result -> {
            System.out.println(result.toString());
            System.out.println();
        });
    }

    private void changePassword() {
        System.out.println("Enter your old password: ");
        String oldPassword=sc.nextLine();

        AtomicBoolean changed= new AtomicBoolean(false);

        if(student.getUserPassword().equals(oldPassword)){
            System.out.println("Enter new Password: ");
            String newPassword=sc.nextLine();
            student.setUserPassword(newPassword);
            System.out.println("Password changed...");
            changed.set(true);
        }

        if(!changed.get())
            System.out.println("Incorrect old password...");
    }

    public void showQuestionPaper(String paperType) throws FileNotFoundException, InterruptedException {
        System.out.println("We are going to show you question paper. Be ready...");
        Thread.sleep(5000);
        int size=student.getAllResults().size();
        String answerKey="/home/vivek/Java training/"+paperType+"/answerKey.txt";
        String questionPaper="/home/vivek/Java training/"+paperType+"/QuestionPaper.txt";
        String answerSheet="/home/vivek/Java training/"+paperType+"/AnswerSheet"+size+".txt";

        try(FileReader fr=new FileReader(questionPaper);
            FileWriter fw=new FileWriter(answerSheet);
            BufferedReader br=new BufferedReader(fr);
            BufferedWriter bw=new BufferedWriter(fw)){

            String line="";
            int questionNumber=1;

            System.out.println("Please select your answer one by one...");
            int lines=0;

            while((line=br.readLine())!=null){
                System.out.println(line);
                lines++;

                if(lines<6)
                    continue;

                lines=0;
                System.out.println("Select option a,b,c,d : ");
                String ans=(questionNumber++)+"."+sc.nextLine();
                bw.write(ans);
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Evaluator evaluator=new Evaluator();
        evaluator.evaluate(answerSheet,answerKey,student,paperType);
    }
}
