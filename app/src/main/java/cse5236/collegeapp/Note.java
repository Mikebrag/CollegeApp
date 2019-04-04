package cse5236.collegeapp;

public class Note {

    public String Body;
    public String Date;
    public String NoteID;
    public String Title;

    public Note(){

    }

    public Note(String b, String d, String n, String t){
        Body = b;
        Date = d;
        NoteID = n;
        Title = t;
    }

    public String toString(){
        return "Title: "+Title + "Date: "+Date + "NoteID: "+NoteID + "Body: "+Body;
    }

}
