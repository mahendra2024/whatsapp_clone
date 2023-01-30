package com.example.waclone.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.waclone.Fragment.callsFragment;
import com.example.waclone.Fragment.chatFragment;
import com.example.waclone.Fragment.statusFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new chatFragment();
            case 1: return new statusFragment();
            case 2 : return new callsFragment();
            default: return new chatFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String titel = null;
        if (position == 0) {
            titel = "CHATS";
        }
        if (position == 1) {
            titel = "STATUS";
        }
        if (position == 2) {
            titel = "CALLS";

        }
        return titel;
    }
}

