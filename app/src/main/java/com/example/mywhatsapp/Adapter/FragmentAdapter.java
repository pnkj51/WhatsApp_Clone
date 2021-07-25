package com.example.mywhatsapp.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mywhatsapp.Fragments.CallsFragment;
import com.example.mywhatsapp.Fragments.ChatsFragment;
import com.example.mywhatsapp.Fragments.StatusFragment;

import org.jetbrains.annotations.NotNull;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull @NotNull FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0: return new ChatsFragment();
            case 1: return new StatusFragment();
            case 2: return new CallsFragment();
            default: return new ChatsFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title = null;
        switch (position){
            case 0: title= "CHATS";break;
            case 1: title= "STATUS";break;
            case 2: title= "CALLS";break;
        }
        return title;

    }
}
