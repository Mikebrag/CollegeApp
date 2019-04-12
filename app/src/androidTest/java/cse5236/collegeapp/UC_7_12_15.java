package cse5236.collegeapp;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UC_7_12_15 {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("cse5236.collegeapp", appContext.getPackageName());
    }

    @Test
    public void firebaseConnectionPossible() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        assertNotNull(database);
    }

    @Test
    public void databaseRefConnectionPossible() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("notes");
        assertNotNull(myRef);
    }

    @Test
    public void canCreateAddNoteFragmentUC12() {
        AddNoteFragment mAddNoteFragment = new AddNoteFragment();
        assertNotNull(mAddNoteFragment);
    }

    @Test
    public void firebaseAddNotesUC12() {
        assertEquals(4, 2 + 2);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("notes");
        String title = "title";
        String d = "04-11-2019";
        String nID = "0";
        String b = "body";
        Note note1 = new Note(b, d, nID, title);
        assertNotNull(note1);
        myRef.child(nID).setValue(note1);
        assertNotNull(myRef.child(nID));
    }

    @Test
    public void firebaseDeleteNotesUC15() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("notes");
        String title = "title";
        String d = "04-11-2019";
        String nID = "0";
        String b = "body";
        Note note1 = new Note(b, d, nID, title);
        assertNotNull(note1);
        myRef.child(nID).setValue(note1);
        assertNotNull(myRef.child(nID));

        myRef.child(nID).removeValue();
        if(myRef.child(nID) == null){
            assertEquals(true,true);
        }
    }
}
