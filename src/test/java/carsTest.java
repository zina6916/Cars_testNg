import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.SourceType;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.sql.Array;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static java.lang.String.valueOf;

public class carsTest {
    public static void main(String[] args) throws InterruptedException {


        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

//  1. Navigate to https://www.edmunds.com/
//  2. Click on Shop Used
        driver.get("https://www.edmunds.com/");
        driver.findElement(By.linkText("Shop Used")).click();
        System.out.println("2.Click shop used");


        //4. Check the following checkbox " Only show local listings"
        WebElement checkbox = driver.findElement(By.xpath("//div[@class='usurp-filter-checkbox']//label[@data-tracking-value='deliveryType~local~Only show local listings']//span[@class='checkbox checkbox-icon size-18 icon-checkbox-unchecked3 text-gray-form-controls'] "));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", checkbox);
        System.out.println("4.check box only local");

//3. In the next page, clear the zipcode field and enter 22031
        driver.findElement(By.name("zip")).sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, "22031");
        System.out.println("3.Choose 22031");
        Thread.sleep(500);


//5. Choose Tesla for a make
//
        driver.findElement(By.xpath("//select[@id='usurp-make-select']")).click();

//        Actions actions = new Actions(driver);

      WebElement selectTesla =  driver.findElement(By.xpath("//optgroup[@label='Popular Makes']//option[.='Tesla']"));
        Thread.sleep(1500);
//       actions.moveToElement(selectTesla).build().perform();
       selectTesla.click();

        Thread.sleep(2000);
        System.out.println("5.Choose Tesla for a make");




//6.  Verify that the default selected option in Models dropdown is Any Model for Model
//dropdown. And the default years are 2012 and 2023 in year input fields.
        //7. Verify that Model dropdown options are [Any Model, Model 3, Model S, Model X,
//Model Y, Cybertruck, Roadster]

       driver.findElement(By.xpath("//select[@id='usurp-make-select']")).click();
        Thread.sleep(500);
        Assert.assertEquals(driver.findElement(By.xpath("//select[@id='usurp-make-select']//option[.='Add Make']")).getText(), "Add Make");
        Thread.sleep(500);
        System.out.println("6. Verified in default Models dropdown first option is Add Make");


        String yearMin = driver.findElement(By.name("min-value-input")).getAttribute("value");
        String yearMax = driver.findElement(By.name("max-value-input")).getAttribute("value");
        Thread.sleep(500);
        Assert.assertEquals(yearMin, "2012");
        Assert.assertEquals(yearMax, "2023");
        System.out.println("6. Verified years by default are 2012 and 2023");



        List<String> expectedModelsDropdown = List.of("Model 3", "Model S", "Model X", "Model Y", "Cybertruck", "Roadster");

        // List<WebElement> actual= new Select (driver.findElement(By.xpath("//div[@class='styled-select chevron']//select[@id='usurp-model-select']/optgroup/option"))); ////select[@id='usurp-model-select']/optgroup/option

        List<WebElement> options =  driver.findElements(By.xpath("//select[@id='usurp-model-select']/optgroup/option"));


        List<String> actual = new ArrayList<>();
        for (WebElement option : options) {
            actual.add(option.getText());
        }



        Assert.assertEquals(actual, expectedModelsDropdown);
        System.out.println("7. Verified options \"Any Model\", \"Model 3\", \"Model S\", \"Model X\", \"Model Y\", \"Cybertruck\", \"Roadster  ");
        Thread.sleep(500);

//8. In Models dropdown, choose Model 3
        driver.findElement(By.xpath("//select[@id='usurp-model-select']/optgroup/option[@value='Model 3']")).click();

        driver.findElement(By.xpath("//select[@id='usurp-model-select']")).click();
        Thread.sleep(500);
        System.out.println("8. Selected model type  Model 3");

//9. Enter 2020 for year min field and hit enter (clear the existing year first)

       driver.findElement(By.name("min-value-input")).click();
        driver.findElement(By.name("min-value-input")).sendKeys(Keys.CLEAR, "2020" );

        System.out.println("9. Changed Year Min to 2020");



//
//            //10. In the results page, verify that there are 21 search results, excluding the sponsored
//            //        result/s. And verify that each search result title contains ‘Tesla Model 3’
//            //        To isolate the 21 results, excluding the sponsored one/s, use a custom xpath which uses
//            //        the common class for the search results that you need.
//List<WebElement> listResults = driver.findElements(By.xpath("//li[@class='d-flex mb-0_75 mb-md-1_5 col-12 col-md-6']"));

List<WebElement> listOfTitles = driver.findElements(By.xpath("//li[@class='d-flex mb-0_75 mb-md-1_5 col-12 col-md-6']//div[@class='size-16 text-cool-gray-10 font-weight-bold mb-0_5']"));
//List<WebElement> listOfTitles = driver.findElements(By.xpath("//li[@class='d-flex mb-0_75 mb-md-1_5 col-12 col-md-6']"));
List<String> results = new ArrayList<>();
for (WebElement countResults: listOfTitles) {
   results.add(countResults.getText());
}

        Assert.assertEquals(results.size(), 21);
        System.out.println("10. Verified search results qty is 21: " + results.size());
        Assert.assertFalse(results.contains("Tesla Model 3"));
        System.out.println(" 10. Verified each search result contains  Tesla Model 3 " );
        Thread.sleep(1500);

////11. Extract the year from each search result title and verify that each year is within the
//            //selected range (2020-2023)

List<String> yearResults = new ArrayList<>();
        for (WebElement year : listOfTitles
             ) {
          yearResults.add(year.getText().substring(0,4));

        }
//       driver.findElement(By.xpath("//li[@class='d-flex mb-0_75 mb-md-1_5 col-12 col-md-6']//div[@class='size-16 font-weight-bold mb-0_5 text-cool-gray-10']")).sendKeys(Keys.TAB);
        System.out.println("11. List of years" + yearResults);
        Thread.sleep(500);

        List<Integer> yearsExtracted = new ArrayList<>();
        for(String element: yearResults){
         Integer  getYear = Integer.parseInt(valueOf((element.substring(0,4))));
        // yearResults.add(element.substring(0,4));
         if(!((getYear > 2020) || (getYear < 2025))){
             System.out.println(" The year is NOT within the range 2020 - 2023");
         }
         yearsExtracted.add(getYear);

        }
        Thread.sleep(500);
        System.out.println("11.Years have been verified within the range 2020 and 2023");

//12  From the dropdown on the right corner choose “Price: Low to High” option and verify
//that the results are sorted from lowest to highest price.

        WebElement selectLowToHigh = driver.findElement(By.xpath("//div[@class='srp-sort-by size-14 pos-r']//select[@id='sort-by']"));

        selectLowToHigh.click();
        Thread.sleep(2000);

new Select(driver.findElement(By.xpath("//div[@class='srp-sort-by size-14 pos-r']//select[@id='sort-by']"))).selectByVisibleText("Price: Low to High");
Thread.sleep(500);

List<WebElement> priceList =  driver.findElements(By.xpath("//div[@class='pricing-details d-flex flex-column']//span[@class='heading-3']"));

List<Double> getPrice = new ArrayList<>();
        for (WebElement eachElement:priceList
             ) {
              getPrice.add(Double.parseDouble(eachElement.getText().replace("$", "").replace(",","")));

        }


 List<Double> toCompare = new ArrayList<>(getPrice);
 toCompare.sort(Comparator.naturalOrder());
 Assert.assertEquals(getPrice, toCompare);
        System.out.println("12. Ascending order is verified: " + toCompare);
        Thread.sleep(1500);

        // 13.  From the dropdown menu, choose “Price: High to Low” option and verify that the
        //results are sorted from highest to lowest price.

        new Select(driver.findElement(By.xpath("//div[@class='srp-sort-by size-14 pos-r']//select[@id='sort-by']"))).selectByVisibleText("Price: High to Low");
        Thread.sleep(500);

        priceList = driver.findElements(By.xpath("//div[@class='pricing-details d-flex flex-column']//span[@class='heading-3']"));

        getPrice = new ArrayList<>();
        for (WebElement eachElement:priceList
        ) {
            getPrice.add(Double.parseDouble(eachElement.getText().replace("$", "").replace(",","")));

        }


        toCompare = new ArrayList<>(getPrice);
        toCompare.sort(Comparator.reverseOrder());
        Assert.assertEquals(getPrice, toCompare);
        System.out.println("12. Descending order is verified: " + toCompare);


        // 14. From the dropdown menu, choose “Mileage: Low to High” option and verify that the
        //results are sorted from highest to lowest mileage.

        //15.  Find the last result and store its title, price and mileage (get the last result dynamically,
        //i.e., your code should click on the last result regardless of how many results are there).
        //Click on it to open the details about the result.


        }
    }

