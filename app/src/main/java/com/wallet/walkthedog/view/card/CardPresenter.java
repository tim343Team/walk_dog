package com.wallet.walkthedog.view.card;

import com.wallet.walkthedog.data.DataSource;
import com.wallet.walkthedog.view.dog.DogDetailContract;

public class CardPresenter implements CardContract.CardPresenter{
    private CardContract.CardView view;
    private DataSource dataRepository;

    public CardPresenter(DataSource dataRepository,CardContract.CardView view) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.view.setPresenter(this);
    }
}
