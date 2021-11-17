package ru.netology;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardDeliveryTest {

    @Test
    void shouldBeDeliveryCardToThreeDay() {
        open("http://localhost:9999");
        $("[data-test-id='city'] [class='input__control']").setValue("Москва");
        LocalDate date = LocalDate.now().plusDays(4);
        $("[class='input__control'][placeholder='Дата встречи']").doubleClick().append("Delete");
        $("[data-test-id='date'] [class='input__control']").setValue(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        $("[data-test-id='name'] [class='input__control']").setValue("Иванова-Петрова Анна");
        $("[data-test-id='phone'] [class='input__control']").setValue("+32223332222");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $(withText("Успешно")).shouldBe(appear, Duration.ofSeconds(15));
    }

    @Test
    void shouldBeDeliveryCardToRandomDay() {
        open("http://localhost:9999");
        $("[data-test-id='city'] [class='input__control']").setValue("Мо");
        $("div.popup.popup_direction_bottom-left.popup_target_anchor.popup_size_m.popup_visible.popup_height_adaptive.popup_theme_alfa-on-white.input__popup > div > div > div > div").find(byText("Майкоп")).click();
        LocalDate date = LocalDate.now().plusDays(5);
        String day = date.format(DateTimeFormatter.ofPattern("dd"));
// клик на конкретный день через календарь без проверки логики месяц-год, с целью обойти заполнение поля даты встречи
// в связи с тем, что без удаления предустановленного значения заполнить поле через setValue не удавалось
        $("div:nth-child(2) > span > span > span").click();
        $("[class='calendar__day calendar__day_type_off'][role='gridcell']");
        $(withText(day)).click();
        $("[data-test-id='name'] [class='input__control']").setValue("Иванова-Петрова Анна");
        $("[data-test-id='phone'] [class='input__control']").setValue("+32223332222");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $(withText("Успешно")).shouldBe(appear, Duration.ofSeconds(15));
        String expectedDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        assertEquals(expectedDate, $("[class='input__control'][placeholder='Дата встречи']").getValue());
        assertEquals("Майкоп", $("[data-test-id='city'] [class='input__control']").getValue());
    }
}
