package cse5236.collegeapp;

public class University {

    public String City;
    public String Name;
    public String QueryTag;
    public String Size;
    public String State;
    public String UniversityID;
    public String Zip;


    public University() {

    }

    public University(String city, String name, String queryTag, String size, String state,
                      String universityID, String zip) {
        City = city;
        Name = name;
        QueryTag = queryTag;
        Size = size;
        State = state;
        UniversityID = universityID;
        Zip = zip;
    }

    public String toString() {
        return "";
    }

}

