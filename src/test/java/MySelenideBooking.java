import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.NoSuchElementException;

import static com.codeborne.selenide.Selenide.*;

public class MySelenideBooking {
  public static void main(String[] args) {

    open("https://booking.com");
    try {
      $x("//div[@role='dialog']//button").click();
    } catch (NoSuchElementException e) {
    }
    WebElement city = $(By.name("ss"));
    city.clear();
    city.sendKeys("Paris");
    $x("//div[text()='Paris']").click();

    // dates
    LocalDate dateFrom = LocalDate.now(ZoneId.systemDefault());
    LocalDate dateTo = dateFrom.plusDays(7);
    $x(String.format("//*[@data-date='%s']", dateFrom)).click();
    $x(String.format("//*[@data-date='%s']", dateTo)).click();

    // guests
    $(By.id("xp__guests__toggle")).click();
    WebElement adult = $x("//span[@id='group_adults_desc']/preceding-sibling::button[1]");
    // input[@id='group_adults']/following-sibling::div/button[2]
    adult.click();
    adult.click();
    WebElement rooms = $x("//span[@id='no_rooms_desc']/preceding-sibling::button[1]");
    rooms.click();
    //$(".sb-searchbox__button ").click();

    // search page
    // driver.findElement(By.xpath("//a[@data-type='price']")).click();
    $("button[data-testid='sorters-dropdown-trigger']").click();
    $("button[data-id='price']").click();

    WebElement filterPrice = $x("//div[@data-filters-group='pri']//div[contains(text(), '+')]");
    int expectedMaxPrice = Integer.parseInt(filterPrice.getText().replaceAll("\\D+", ""));
    filterPrice.click();

    String actualMaxPriceText =
        $x("//div[@id='search_results_table']//div[@data-testid='property-card'][1]//div[contains(@data-testid, 'price')]/span")
            .getText();

    int actualMaxPrice = Integer.parseInt(actualMaxPriceText.replaceAll("\\D+", ""));

    System.out.println("Expected price: " + expectedMaxPrice);
    System.out.println("Actual price: " + actualMaxPrice / 7);

    Assert.assertTrue(
        actualMaxPrice / 7 >= expectedMaxPrice,"Expected hotel prise is lower than expected!");
  }
}
