package com.example.quotesapp.ui.home;

import android.app.Application;
import android.content.SharedPreferences;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText;
    private final List<String> quotes;
    private final SharedPreferences prefs;

    private static final String PREFS_NAME = "QuotesAppPrefs";
    private static final String FAVORITES_KEY = "Favorites";

    public HomeViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        quotes = new ArrayList<>();
        quotes.add("The best way to predict the future is to create it.");
        quotes.add("You are never too old to set another goal or to dream a new dream.");
        quotes.add("Do not watch the clock. Do what it does. Keep going.");
        quotes.add("The harder you work for something, the greater you’ll feel when you achieve it.");
        quotes.add("Never put off for tomorrow what you can do today.\n" + "-Thomas Jefferson\n");
        quotes.add("Time wasted is existence; used is life.\n" + "Edward Young");
        quotes.add("Getting an idea should be like sitting on a pin; it should make you jump up and do something.\n" + "E. L. Simpson");
        quotes.add("The only difference between success and failure is the ability to take action.\n" + "Alexander Graham Bell\n");
        quotes.add("Begin while others are procrastinating. Work while others are wishing.\n" + "William Arthur Ward");
        setRandomQuote();
        prefs = application.getSharedPreferences(PREFS_NAME, Application.MODE_PRIVATE);
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
        SharedPreferences.Editor editor = prefs.edit();
        List<String> favorites = getFavorites();
        if (!favorites.contains(quote)) {
            favorites.add(quote);
            editor.putStringSet(FAVORITES_KEY, new HashSet<>(favorites));
            editor.apply();
        }
    }

    public List<String> getFavorites() {
        Set<String> set = prefs.getStringSet(FAVORITES_KEY, new HashSet<>());
        return new ArrayList<>(set);
    }
}
