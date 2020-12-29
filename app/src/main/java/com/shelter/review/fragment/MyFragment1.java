package com.shelter.review.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shelter.review.R;

/**
 * @author: Shelter
 * Create time: 2020/12/29, 22:45.
 */
public class MyFragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("Shelter", "MyFragment1 onCreateView()");
        View view = inflater.inflate(R.layout.layout_fragment1, container, false);
        return view;
    }

    public MyFragment1() {
        super();
        Log.i("Shelter", "MyFragment1 MyFragment1()");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i("Shelter", "MyFragment1 onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Shelter", "MyFragment1 onCreate()");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("Shelter", "MyFragment1 onViewCreated()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Shelter", "MyFragment1 onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Shelter", "MyFragment1 onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Shelter", "MyFragment1 onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Shelter", "MyFragment1 onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Shelter", "MyFragment1 onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("Shelter", "MyFragment1 onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Shelter", "MyFragment1 onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("Shelter", "MyFragment1 onDetach()");
    }
}
