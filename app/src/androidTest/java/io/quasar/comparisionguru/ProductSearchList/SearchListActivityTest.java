package io.quasar.comparisionguru.ProductSearchList;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.quasar.comparisionguru.MainActivity;
import io.quasar.comparisionguru.R;

import static org.junit.Assert.*;

/**
 * Created by prashantn.pol on 2017-03-29.
 */
public class SearchListActivityTest {

    private SearchListActivity searchListActivity =null;

    @Rule
    public ActivityTestRule<SearchListActivity> mActivityRule = new ActivityTestRule(SearchListActivity.class);

    @Test
    public  void testSearchList(){
        View view=searchListActivity.findViewById(R.id.rv_searchlist);
        assertNotNull(view);
    }

    @Before
    public void setUp() throws Exception {

        searchListActivity=mActivityRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {

        searchListActivity=null;
    }

}