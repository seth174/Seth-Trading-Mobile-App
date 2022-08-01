package com.sethfagen.sethstradingapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sethfagen.sethstradingapplication.databinding.FragmentStockInfoBinding;

public class StockInfoFragment extends Fragment {
    private FragmentStockInfoBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStockInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}