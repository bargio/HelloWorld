package com.sinervis.helloworld;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by studente on 21/03/2016.
 */
public class FragmentText extends Fragment {

    private String name;

    public static FragmentText getInstance(String name) {

        Bundle bundle = new Bundle();
        bundle.putString("NAME", name);

        FragmentText fragmentText = new FragmentText();
        fragmentText.setArguments(bundle);
        return fragmentText;
    }

    @Override
    public void onCreate(Bundle savedInstaceState) {
        this.name = getArguments().getString("NAME");
        // do something with name...
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_text, container);
        return layout;
    }
}
