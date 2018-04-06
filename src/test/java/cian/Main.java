package cian;

import org.testng.annotations.Test;

import java.util.*;

public class Main extends TestBase {

    @Test
    public void CianTest() throws Exception {

        List<Map<String, String>> searchData = Helper.readFile();
        List<Map<String, String>> dataToWrite = new LinkedList<>();


        PageManager manager = new PageManager();

        for (Map<String, String> search : searchData) {

            Map<String, String> data = manager
                    .mainPage()
                    .selectOption(search.get("Option"))
                    .selectBuildingType(search.get("Type"))
                    .selectRoomNumber(search.get("Room number"))
                    .selectPrice(search.get("Price from"), search.get("Price to"))
                    .selectSquare(search.get("Square from"), search.get("Square to"))
                    .selectAddress(search.get("Address"))
                    .submitRequest()
                    .scrollPhotos()
                    .getInfo(search.get("Type"));

            data.put("Option", search.get("Option"));
            dataToWrite.add(data);
        }

        Helper.writeToExcel(dataToWrite);
    }

}
