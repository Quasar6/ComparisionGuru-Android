package io.quasar.comparisionguru;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by prashantn.pol on 2017-03-29.
 */
public class MainActivityTest {

    private  MainActivity mainActivity =null;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public  void testSearch(){
        View view=mainActivity.findViewById(R.id.query_text);
        assertNotNull(view);
    }
    @Test
    public void testSponserdList()
    {
        View view =mainActivity.findViewById(R.id.rv_sponsoredlist);
        assertNotNull(view);
    }

    @Before
    public void setUp() throws Exception {

        mainActivity=mActivityRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {

        mainActivity=null;
    }

}