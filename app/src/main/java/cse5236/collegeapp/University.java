package cse5236.collegeapp;

public class University {

    public String City;
    public String Name;
    public String Size;
    public String UniversityID;
    public String State;
    public String Zip;


    public University(){

    }

    public University(String n, String c, String s, String z, String s1, String u){
        Name = n;
        City = c;
        Size = s1;
        UniversityID = u;
        State = s;
        Zip = z;
    }

    public String toString(){
        return "";
    }

}

