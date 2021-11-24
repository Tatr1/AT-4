package ru.netology;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {

    String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldBeDeliveryCardToThreeDay() {
        open("http://localhost:9999");
        $("[data-test-id='city'] [class='input__control']").setValue("Москва");
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] [class='input__control']").setValue(planningDate);
        $("[data-test-id='name'] [class='input__control']").setValue("Иванова-Петрова Анна");
        $("[data-test-id='phone'] [class='input__control']").setValue("+32223332222");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(20)).shouldHave(exactText("Встреча успешно забронирована на " + planningDate));
    }

}
