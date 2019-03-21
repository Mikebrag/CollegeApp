package cse5236.collegeapp;

public class Note {

    public String Body;
    public String Date;
    public String NoteID;
    public String Subject;

    public Note(){

    }

    public Note(String b, String d, String n, String s){
        Body = b;
        Date = d;
        NoteID = n;
        Subject = s;
    }

    public String toString(){
        return "Subject: "+Subject + "Date: "+Date + "NoteID: "+NoteID + "Body: "+Body;
    }

}
