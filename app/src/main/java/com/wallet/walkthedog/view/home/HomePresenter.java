package com.wallet.walkthedog.view.home;

import com.wallet.walkthedog.data.DataSource;
import com.wallet.walkthedog.view.email.EmailContract;

public class HomePresenter  implements HomeContract.HomePresenter{
    private HomeContract.HomeView view;
    private DataSource dataRepository;

    public HomePresenter(DataSource dataRepository,HomeContract.HomeView view) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.view.setPresenter(this);
    }
}
