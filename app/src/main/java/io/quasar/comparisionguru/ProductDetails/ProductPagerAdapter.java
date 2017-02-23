package io.quasar.comparisionguru.ProductDetails;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by prashantn.pol on 2017-02-22.
 */

public class ProductPagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public ProductPagerAdapter(android.support.v4.app.FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PriceFragment tab1 = new PriceFragment();
                return tab1;
            case 1:
                SpecsFragment tab2 = new SpecsFragment();
                return tab2;
            case 2:
                ReviewsFragment tab3 = new ReviewsFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}
