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
public class MyFragment2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("Shelter", "MyFragment2 onCreateView()");
        View view = inflater.inflate(R.layout.layout_fragment2, container, false);
        return view;
    }

    public MyFragment2() {
        super();
        Log.i("Shelter", "MyFragment2 MyFragment2()");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i("Shelter", "MyFragment2 onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Shelter", "MyFragment2 onCreate()");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("Shelter", "MyFragment2 onViewCreated()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Shelter", "MyFragment2 onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Shelter", "MyFragment2 onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Shelter", "MyFragment2 onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Shelter", "MyFragment2 onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Shelter", "MyFragment2 onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("Shelter", "MyFragment2 onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Shelter", "MyFragment2 onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("Shelter", "MyFragment2 onDetach()");
    }
}
