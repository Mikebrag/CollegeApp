package cse5236.collegeapp;

public class User {
    public String id;
    public String email;
    public Portfolio portfolio;

    public User(){

    }

    public User(String i, String e){
        id = i;
        email = e;
        portfolio = new Portfolio();
    }

    public String toString(){
        return "Email: "+ email;
    }

}
