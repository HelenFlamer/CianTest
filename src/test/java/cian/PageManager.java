package cian;

public class PageManager {

    PageManager() {}

    private static MainPage mainPage;

    public MainPage mainPage(){
        if (mainPage == null)
            mainPage = new MainPage();
        return mainPage;
    }

}
