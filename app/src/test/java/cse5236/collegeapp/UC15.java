package cse5236.collegeapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UC15 {

    @Test
    public void canCreateValidNote() {
        String title = "title";
        String d = "04-11-2019";
        String nID = "0";
        String b = "body";
        Note note1 = new Note(b, d, nID, title);
        assertNotNull(note1);
    }

}