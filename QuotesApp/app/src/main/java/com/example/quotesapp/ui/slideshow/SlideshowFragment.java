package com.example.quotesapp.ui.slideshow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.quotesapp.R;
import com.example.quotesapp.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Pass context to ViewModel
        slideshowViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SlideshowViewModel(requireContext());
            }
        }).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button share = root.findViewById(R.id.buttonshare);
        Button favorite = root.findViewById(R.id.buttonrefavourite);
        TextView quote = root.findViewById(R.id.textViewslideshowquote);

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
            slideshowViewModel.addToFavorites(currentQuote);
        });

        final TextView textViewQuote = binding.textViewslideshowquote;
        final Button buttonRefresh = binding.buttonrefresh;

        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textViewQuote::setText);

        buttonRefresh.setOnClickListener(v -> slideshowViewModel.setRandomQuote());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
