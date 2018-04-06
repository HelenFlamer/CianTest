package cian;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static cian.Helper.*;


public class MainPage extends TestBase {
    public MainPage() {
    }

    //Options
    @FindBy(xpath = "//a[@data-ref='rent_longterm']")
    private WebElement rentOption;
    @FindBy(xpath = "//a[@data-ref='sale']")
    private WebElement saleOption;
    @FindBy(xpath = "//a[@data-ref='newbuildings']")
    private WebElement newBuildingOption;
    @FindBy(xpath = "//a[@data-ref='commercial']")
    private WebElement commercialOption;
    @FindBy(xpath = "//a[@data-ref='rent_daily']")
    private WebElement dailyRentOption;

    //Type
    @FindBy(xpath = "//button[@data-reactid='18']")
    private WebElement selectTypeBtn;

    //Room Number
    @FindBy(xpath = "//button[@data-reactid='22']")
    private WebElement selectRoomNumber;
    @FindBy(xpath = "//*[button[@data-reactid='22']]/div/div/div/div")
    private List<WebElement> roomOptions;

    //PriceOptions
    @FindBy(xpath = "//input[contains(@class, 'price')][@placeholder='от']")
    private WebElement priceFrom;
    @FindBy(xpath = "//input[contains(@class, 'price')][@placeholder='до']")
    private WebElement priceTo;

    //Address
    @FindBy(xpath = "//div[label[contains(@class, 'geo_input')]]//input")
    private WebElement addressField;
    @FindBy(xpath = "//div[contains(text(), 'Укажите другой адрес')]")
    private WebElement incorrectAddressMessage;
    @FindBy(xpath = "//div[contains(@class, 'suggest_popup-content')]")
    private WebElement addressesPopup;

    //Square
    @FindBy(xpath = "//div[button[contains(text(), 'Площадь')]]")
    private WebElement squareDropDown;

    //AreaOptions
    @FindBy(xpath = "//div[contains(@class, 'area')]//input[@placeholder='от']")
    private WebElement areaFrom;
    @FindBy(xpath = "//div[contains(@class, 'area')]//input[@placeholder='до']")
    private WebElement areaTo;

    //submit btn
    @FindBy(xpath = "//button[contains(text(), 'Найти')]")
    private WebElement submitBtn;

    public MainPage selectOption(String option) {
        WebElement selectOption = driver.findElement(By.xpath("//a[contains(@data-ref, '" + option + "')]"));
        wait.until(ExpectedConditions.visibilityOf(selectOption));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", selectOption);
        return this;
    }

    public MainPage selectBuildingType(String type) {
        if (type == null)
            return this;

        wait.until(ExpectedConditions.elementToBeClickable(selectTypeBtn));
        selectTypeBtn.click();

        //find and select building type
        String xpath = "//*[contains(text(), '" + type + "')]";
        driver.findElement(By.xpath(xpath)).click();

        return this;
    }

    public MainPage selectRoomNumber(String rooms) {
        if (rooms == null)
            return this;

        //get all necessary rooms
        String[] allRooms = rooms.split(",");

        wait.until(ExpectedConditions.elementToBeClickable(selectRoomNumber));
        selectRoomNumber.click();

        //remove all options
        for (WebElement roomOption : roomOptions) {
            if (roomOption.findElement(By.tagName("span")).getAttribute("class").contains("_2TXtVIdi1LdzA7au"))
                roomOption.click();
        }

        //select necessary rooms
        for (String room : allRooms) {
            for (WebElement roomOption : roomOptions) {
                if (roomOption.getText().contains(room))
                    roomOption.click();
            }
        }

        return this;
    }

    public MainPage selectPrice(String prFrom, String prTo) {
        wait.until(ExpectedConditions.visibilityOf(priceFrom));
        if (prFrom != null) {
            priceFrom.clear();
            priceFrom.sendKeys(prFrom);
        }
        if (prTo != null) {
            priceTo.clear();
            priceTo.sendKeys(prTo);
        }

        return this;
    }

    public MainPage selectAddress(String address) {
        wait.until(ExpectedConditions.visibilityOf(addressField));

        //enter address
        if (address != null) {
            addressField.clear();
            addressField.sendKeys(address);

            //check for incorrect address
            try {
                if (incorrectAddressMessage.isDisplayed()) {
                    addressField.clear();
                    addressField.sendKeys("Москва");
                }
            } catch (Exception ignore) {
            }

            //ждем выпадающего списка с вариантами
            //выбираем первый, где упомянут указанный адрес
            wait.until(ExpectedConditions.visibilityOf(addressesPopup));
            WebElement selectAddress = addressesPopup.findElement(By.xpath("//div[contains(text(), '" + address + "')]"));
            selectAddress.click();

        }
        return this;
    }

    public SearchResultPage submitRequest() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);
        return new SearchResultPage();
    }

    public MainPage selectSquare(String from, String to) {
        if (from == null && to == null)
            return this;

        squareDropDown.click();

        if (from != null) {
            areaFrom.sendKeys(from);
        }

        if (to != null) {
            areaTo.sendKeys(to);
        }

        return this;
    }
}
