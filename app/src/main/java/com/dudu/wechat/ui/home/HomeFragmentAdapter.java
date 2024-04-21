package com.dudu.wechat.ui.home;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;
import com.dudu.wechat.ui.home.SessionsFragment;

public class HomeFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();

    public HomeFragmentAdapter(FragmentManager fm) {
        super(fm);
        mFragments.add(new SessionsFragment());
        mFragments.add(new ContactsFragment());
		mFragments.add(new MyselfFragment());
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
