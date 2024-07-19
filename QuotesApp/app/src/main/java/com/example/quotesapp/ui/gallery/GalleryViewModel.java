package com.example.quotesapp.ui.gallery;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GalleryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final List<String> quotes;
    private final Context context;

    private static final String PREFS_NAME = "QuotesAppPrefs";
    private static final String FAVORITES_KEY = "Favorites";

    public GalleryViewModel(Context context) {
        this.context = context;
        mText = new MutableLiveData<>();
        quotes = new ArrayList<>();
        quotes.add("Success is not the key to happiness. Happiness is the key to success.");
        quotes.add("The only way to do great work is to love what you do.");
        quotes.add("Your time is limited, don't waste it living someone else's life.");
        quotes.add("The best revenge is massive success.");
        quotes.add("Believe you can and you're halfway there.");
        setRandomQuote();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setRandomQuote() {
        Random random = new Random();
        int index = random.nextInt(quotes.size());
        mText.setValue(quotes.get(index));
    }

    public void addToFavorites(String quote) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        List<String> favorites = getFavorites();
        if (!favorites.contains(quote)) {
            favorites.add(quote);
            editor.putStringSet(FAVORITES_KEY, new HashSet<>(favorites));
            editor.apply();
        }
    }

    public List<String> getFavorites() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> set = prefs.getStringSet(FAVORITES_KEY, new HashSet<>());
        return new ArrayList<>(set);
    }
}
