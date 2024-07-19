package com.example.quotesapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.quotesapp.R;
import com.example.quotesapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button share = root.findViewById(R.id.buttonrehomeshare);
        Button favorite = root.findViewById(R.id.buttonrefavourite);
        TextView quote = root.findViewById(R.id.textViewhomequote);

        share.setOnClickListener(v -> {
            String sharetxt = quote.getText().toString();
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, sharetxt);

            startActivity(shareIntent);
        });

        favorite.setOnClickListener(v -> {
            String currentQuote = quote.getText().toString();
            homeViewModel.addToFavorites(currentQuote);
        });

        final TextView textViewQuote = binding.textViewhomequote;
        final Button buttonRefresh = binding.buttonrefresh;

        homeViewModel.getText().observe(getViewLifecycleOwner(), textViewQuote::setText);

        buttonRefresh.setOnClickListener(v -> homeViewModel.setRandomQuote());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
