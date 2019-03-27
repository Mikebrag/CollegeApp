package cse5236.collegeapp;

public class Rating {

    public String Rating;
    public String Date;
    public String RatingID;
    public String UniversityID;
    public String UserID;


    public Rating(){

    }

    public Rating(String r, String d, String r1, String u1, String u2){
        Rating = r;
        Date = d;
        RatingID = r1;
        UniversityID = u1;
        UserID = u2;
    }

    public String toString(){
        return "";
    }

}
