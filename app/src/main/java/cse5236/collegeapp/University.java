package cse5236.collegeapp;

public class University {

    private String City;
    private String Name;
    private String Size;
    private String UniversityID;
    private String State;
    private String Zip;


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

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getUniversityID() {
        return UniversityID;
    }

    public void setUniversityID(String universityID) {
        UniversityID = universityID;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getZip() {
        return Zip;
    }

    public void setZip(String zip) {
        Zip = zip;
    }

}

