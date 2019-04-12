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
public class UC7 {

    @Test
    public void canCreateValidPortfolio() {

        Portfolio p1 = new Portfolio();
        assertNotNull(p1);
    }

    @Test
    public void canCreateValidUniversity() {
        String city = "Test";
        String name = "Test";
        String queryTag = "Test";
        String size = "Test";
        String state = "Test";
        String universityID = "0";
        String zip = "Test";
        University u1 = new University(city, name, queryTag, size, state, universityID, zip);
        assertNotNull(u1);
    }

}