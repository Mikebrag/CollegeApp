package cse5236.collegeapp;

public class Application {

    public String ApplicationID;
    public String Date;
    public String Status;
    public String UniversityID;
    public String UserID;


    public Application(){

    }

    public Application(String a, String d, String s, String u1, String u2){
        ApplicationID = a;
        Date = d;
        Status = s;
        UniversityID = u1;
        UserID = u2;
    }

    public String toString(){
        return "";
    }

}
