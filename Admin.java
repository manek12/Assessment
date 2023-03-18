package HoliAssessment;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Admin {
    private final static String adminPwd = "ROOT101";
    private static Map<String, Student> userCredentials = new HashMap<>();
    public Scanner sc = new Scanner(System.in);

    private String getUniqueUserId() {
        String c = "" + System.currentTimeMillis();
        return c.substring(c.length()-5);
    }

    public static Map<String, Student> getUserCredentials() {
        return userCredentials;
    }

    public Admin() throws InterruptedException, FileNotFoundException {
        System.out.println("Welcome to Examination center");
        loadCredentials();
        startApplication();
    }

    public Admin(String from){
        writeCredentials();
    }

    @SuppressWarnings("unchecked")
    private void loadCredentials() {
        Connection con=null;
        try {
//            File toRead=new File("/home/vivek/IdeaProjects/Java Training/src/data");
//            FileInputStream fis=new FileInputStream(toRead);
//            ObjectInputStream ois=new ObjectInputStream(fis);
//
//            userCredentials = (Map<String,Student>) ois.readObject();
//            ois.close();
//            fis.close();

            con=DB_Connection.getConnection();
            Statement smt=con.createStatement();
            ResultSet resultSet= smt.executeQuery("select * from student");

            while(resultSet.next()){
                String id=resultSet.getString(1);
                String name=resultSet.getString(2);
                String password=resultSet.getString(3);

                Student student=new Student(name,id,password);
                userCredentials.put(id,student);
            }

        } catch(Exception e) {
            System.out.println("Error while loading the credentials");
        }
        finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeCredentials() {
        try {
            File fileOne=new File("/home/vivek/IdeaProjects/Java Training/src/data");
            FileOutputStream fos=new FileOutputStream(fileOne);
            ObjectOutputStream oos=new ObjectOutputStream(fos);

            oos.writeObject(userCredentials);
            oos.flush();
            oos.close();
            fos.close();
        } catch(Exception e) {
            System.out.println("Error while writing credentials");
        }
    }

    private void startApplication() throws InterruptedException, FileNotFoundException {
        int count = 0;
        sc = new Scanner(System.in);
        while (true) {
            if (count > 3){
                System.out.println("You've tried multiple times! Please contact admin...");
                System.exit(0);
            }
            count++;
            System.out.println("Do you want to Login, Register, Display all users or Exit the system? [login, register, display, exit]");
            String choice = (sc.nextLine()).toLowerCase();
            if (choice.equals("login")){
                login();
                writeCredentials();
            } else if (choice.equals("register")){
                register();
                writeCredentials();
            } else if (choice.equals("display")) {
                display();
            } else if (choice.equals("exit")){
                System.out.println("Bye. Exiting the system");
                Thread.sleep(2000);
                System.exit(0);
            } else {
                System.out.println("Error! Please enter a correct input [login, register, display, exit] : ");
            }
        }
    }

    private void display() throws InterruptedException, FileNotFoundException {
        // sc = new Scanner(System.in);
        System.out.print("WARNING! You are accessing priviliged information... Please add ADMIN password to continue : ");
        String currUserPassword = sc.nextLine();
        if (currUserPassword.equals(Admin.adminPwd)){
            displayAllDetails();
        } else {
            System.out.println("Incorrect Password! Taking you back to Home Page");
            System.out.println("---------------------------------------------------------------------------\n");

        }
        Thread.sleep(2000);
        startApplication();
    }

    private void displayAllDetails() {
        System.out.println("\n---------------------------------------------------------------------------");
        System.out.println("Welcome ADMIN, here are all the users registered in your database");
        for (String userId : userCredentials.keySet())
            System.out.println("User ID: " + userId + ", User name : " + userCredentials.get(userId).getName());
        System.out.println("---------------------------------------------------------------------------\n");
    }

    public void register() throws InterruptedException, FileNotFoundException {
        // sc = new Scanner(System.in);
        System.out.println("Please wait. Taking you to register page...");
        Thread.sleep(2000);
        System.out.println("\n---------------------------------------------------------------------------");
        System.out.print("Please enter your name : ");
        String userName = sc.nextLine();
        String userId = "U" + getUniqueUserId();
        String firstPwd = "$";
        String secondPwd = "#";
        int count = 0;
        while (true) {
            if (count > 3) {
                System.out.println("We need to verify if you're a human!");
                Thread.sleep(2000);
                firstPwd = secondPwd = "user78" + getUniqueUserId();
                System.out.println("Clearly you cannot type... We've generated a password for you...");
                Thread.sleep(2000);
                System.out.println("Your UserId is : " + userId);
                System.out.println("Your password is : " + firstPwd);
                break;
            }
            ++count;
            System.out.print("Please enter a password of your choice : ");
            firstPwd = sc.nextLine();
            System.out.print("Please Re-Enter the password : ");
            secondPwd = sc.nextLine();

            if (firstPwd.equals(secondPwd)){
                Thread.sleep(3000);
                System.out.println("\n---------------------------------------------------------------------------");
                System.out.println("Success! User Created!!!");
                Student student = new Student(userName, userId, firstPwd);
                saveCredentials(userId, student);
                System.out.println("Your UserId is : " + userId);
                System.out.println("Taking you to login page");
                System.out.println("---------------------------------------------------------------------------");
                login();
                break;
            }
            else {
                Thread.sleep(2000);
                System.out.println("Error! Passwords do not match! Please enter again...");
                System.out.println("\n---------------------------------------------------------------------------");
            }
        }
    }

    public void login() throws InterruptedException, FileNotFoundException {
        System.out.println("Please wait...");
        Thread.sleep(2000);
        // sc = new Scanner(System.in);


        int count = 0;
        while (true) {
            if (count > 3) {
                System.out.println("\n---------------------------------------------------------------------------");
                System.out.println("You've tried multiple times... ");
                Thread.sleep(2000);
                System.out.println("We cannot verify if you're a human... Please restart the application");
                Thread.sleep(2000);
                System.out.println("---------------------------------------------------------------------------");
                System.exit(1);
            }
            ++count;
            System.out.println("Please enter your UserId : ");
            String currentUserId = sc.nextLine();
            System.out.println("Please enter your password : ");
            String currentUserPassword = sc.nextLine();

            if (validateCredentials(currentUserId, currentUserPassword)) {
                Student curUser = userCredentials.get(currentUserId);
                ExamHandler examHandler=new ExamHandler(curUser);
                examHandler.showStudentInterface();
                break;
            } else {
                Thread.sleep(2000);
                System.out.println("Incorrect UserId or Password! Please try again...");
                System.out.println("---------------------------------------------------------------------------");

            }
        }
    }

    public void saveCredentials(String userId, Student user) {
        userCredentials.put(userId, user);
    }

    public boolean validateCredentials(String userId, String password) {
        Map<String, Student> credentials = getUserCredentials();
        if (userId != null && credentials.containsKey(userId) && (credentials.getOrDefault(userId, new Student("DefaultUser", userId, "#*&#*@&#*@&333")).getUserPassword()).equals(password))
            return true;
        else
            return false;
    }

}