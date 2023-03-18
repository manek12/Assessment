package HoliAssessment;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student implements Serializable{
    private String name;
    private final String userId;
    private String userPassword;
    private List<Result> allResults = new ArrayList<>();

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserPassword(String pwd){
        userPassword = pwd;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public List<Result> getAllResults() {
        return allResults;
    }

    public void addResult(Result result){
        allResults.add(result);
    }

    public Student(String name, String userId, String userPassword) {
        this.name = name;
        this.userId = userId;
        this.userPassword = userPassword;
    }
}