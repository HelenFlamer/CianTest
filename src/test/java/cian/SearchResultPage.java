package cian;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static cian.Helper.driver;
import static cian.Helper.wait;

public class SearchResultPage extends TestBase {

    private static WebElement offer;

    public SearchResultPage() {
    }

    //offers
    @FindBy(xpath = "//div[contains(@class, 'offer-container')]")
    private List<WebElement> offers;

    public SearchResultPage scrollPhotos() {
        wait.until(ExpectedConditions.visibilityOfAllElements(offers));

        //get current window handle
        Helper.mainWindowHandle = driver.getWindowHandle();

        //select offer
        int num = new Random().nextInt(offers.size()) + 1;
        offer = driver.findElement(By.xpath("//div[contains(@class, 'offer-container')][" + num + "]"));
        WebElement photos = offer.findElement(By.xpath("//div[contains(@class, 'slides')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", photos);
        Actions action = new Actions(driver);
        action.moveToElement(photos);
        action.perform();
        String countText = offer.findElement(By.xpath("//div[contains(@class, 'controls-counter')]")).getText();
        int photoCount = Integer.parseInt(countText.split(" ")[countText.split(" ").length - 1]);

        WebElement nextPhoto = offer.findElement(By.xpath("//button[contains(@class, 'controls-next')]"));
        for (int i = 0; i < photoCount - 1; i++) {
            nextPhoto.click();
        }

        return this;
    }

    public Map<String, String> getInfo(String type){

        ((JavascriptExecutor) driver).executeScript("javascript:window.scrollBy(0,-200)");
        Map<String, String> data = new HashMap<>();
        data.put("Type", type);

        switch (type) {
            case "Квартира":
            case "Дом":
                //общая информация
                data.put("Main info", offer.findElement(By.xpath("//div[contains(@class, 'info-section')]//div[contains(@class, 'title')]")).getText());

                //стоимость
                data.put("Prices", offer.findElement(By.xpath("//div[contains(@class, 'info-section')]/div[2]")).getText());

                //Адрес
                data.put("Address", offer.findElement(By.xpath("//div[contains(@class, 'address-link')]")).getText());

                //Телефон
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", offer.findElement(By.xpath("//span[contains(@class, 'simplified-phone')]")));
                data.put("Phone", offer.findElement(By.xpath("//div[contains(@class, 'simplified-text')]")).getText());
                break;

            case "Офис":
                //общая информация
                data.put("Main info",offer.findElement(By.xpath("//a[contains(@class, 'header-link')][contains(@target,'blank')]")).getText());

                //стоимость
                data.put("Prices", offer.findElement(By.xpath("//div[contains(@class, 'header-subTerm')]")).getText());

                //Адрес
                data.put("Address", offer.findElement(By.xpath("//div[contains(@class, 'address-path')]")).getText());

                //Телефон
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", offer.findElement(By.xpath("//div[contains(@class, 'phone')]//button")));
                data.put("Phone", offer.findElement(By.xpath("//div[contains(@class, 'phone')]//div")).getText());
                break;

        }
        return data;
    }
}
