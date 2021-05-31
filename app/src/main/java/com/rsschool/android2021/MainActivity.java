package com.rsschool.android2021;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.rsschool.android2021.databinding.ActivityMainBinding;
import com.rsschool.android2021.interfaces.LastResultKeeper;
import com.rsschool.android2021.interfaces.OnGenerateRandomClickListener;

public class MainActivity extends AppCompatActivity implements OnGenerateRandomClickListener,
        LastResultKeeper {

    //view binding
    private ActivityMainBinding binding;

    private int previousResult = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        openFirstFragment(0);
    }

    private void openFirstFragment(int previousNumber) {
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.container.getId(), firstFragment)
                .commit();
    }

    private void openSecondFragment(int min, int max) {
        final Fragment secondFragment = SecondFragment.newInstance(min, max);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.container.getId(), secondFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onGenerateBtnPressed(int min, int max) {
        openSecondFragment(min, max);
    }

    @Override
    public int getLastResult() {
        return previousResult;
    }

    @Override
    public void setLastResult(int lastResult) {
        previousResult = lastResult;
    }
}
