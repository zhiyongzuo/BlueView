package com.example.zuo81.blueview.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.example.zuo81.blueview.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment {
    public static final String KEY_PAGE_FRAGMENT = "KEY_PAGE_FRAGMENT";
    private int res;
    private ViewStub mViewStub;

    public PageFragment() {
        // Required empty public constructor
    }

    public static PageFragment newInstance(int res) {
        PageFragment mPageFragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_PAGE_FRAGMENT, res);
        mPageFragment.setArguments(bundle);
        return mPageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments().getInt(KEY_PAGE_FRAGMENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ruler, container, false);
        mViewStub = view.findViewById(R.id.view_stub_activity_main);
        mViewStub.setLayoutResource(res);
        mViewStub.inflate();
        return view;
    }

}
