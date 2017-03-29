package io.quasar.comparisionguru.ProductDetails;

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
public class ProductDetailsTest {
    private ProductDetails pActivity =null;

    @Rule
    public ActivityTestRule<ProductDetails> mActivityRule = new ActivityTestRule(ProductDetails.class);

    @Test
    public  void testImage(){
        View view=pActivity.findViewById(R.id.productbigimage);
        assertNotNull(view);
    }


    @Before
    public void setUp() throws Exception {

        pActivity=mActivityRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {

        pActivity=null;
    }


}