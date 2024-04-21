package com.dudu.wechat.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.dudu.wechat.R;

public class MyselfFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, group, false);
        return view;
    }
}

