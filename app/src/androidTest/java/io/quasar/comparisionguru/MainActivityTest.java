package io.quasar.comparisionguru;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.quasar.comparisionguru.ProductSearchList.SearchListActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by prashantn.pol on 2017-03-29.
 */
public class MainActivityTest {

    private  MainActivity mainActivity =null;
    Instrumentation.ActivityMonitor monitor=getInstrumentation().addMonitor(SearchListActivity.class.getName(),null,false);

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

    @Test
    public void testLaunchSearchListActivity()
    {
        assertNotNull(mainActivity.findViewById(R.id.search));
        onView(withId(R.id.search)).perform(click());
        Activity activity= getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(activity);

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