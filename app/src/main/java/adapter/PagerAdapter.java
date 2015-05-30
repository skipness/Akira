package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import fragment.BlankFragment;
import fragment.NowplayingFragment;
import fragment.PopularFragment;
import fragment.TopratedFragment;
import fragment.UpcomingFragment;


public class PagerAdapter extends FragmentStatePagerAdapter {

    private final String[] Titles = {"NOW PLAYING", "UPCOMING", "POPULAR", "TOP RATED"};
    private Boolean isNetworkAvailable;

    public PagerAdapter(Boolean x, FragmentManager fm) {
        super(fm);
        isNetworkAvailable = x;
    }

    @Override
    public Fragment getItem(int position) {
        if(isNetworkAvailable == true) {
            Log.d("PAGER---", isNetworkAvailable.toString());
            switch (position) {
                case 0:
                    return new NowplayingFragment();
                case 1:
                    return new UpcomingFragment();
                case 2:
                    return new PopularFragment();
                case 3:
                    return new TopratedFragment();

            }
        }else{
            return new BlankFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
          return Titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }
}
