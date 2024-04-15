import com.codeborne.selenide.Configuration;
import org.apache.commons.exec.CommandLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class CardDeliveryNegativeTest {
    public static String setLocalDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy",
                new Locale("ru")));
    }

    @BeforeEach
    void setupTest() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitValidData3() {

        Configuration.holdBrowserOpen = true;
        String date = setLocalDate(3);
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id = date] .input__control").doubleClick().sendKeys(date);
        $("[name=name]").setValue("Петров Евгений ");
        $("[name=phone]").setValue("+79002223322");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id = notification]").shouldHave(text("Успешно!"),
                Duration.ofSeconds(15)).shouldBe(visible);
        $(".notification__content").shouldHave(text("Встреча успешно забронирована на " + date),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    void shouldSubmitValidData4() {
        Configuration.holdBrowserOpen = true;
        String date = setLocalDate(4);
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id = date] .input__control").doubleClick().sendKeys(date);
        $("[name=name]").setValue("Петров Евгений");
        $("[name=phone]").setValue("+79002223322");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id = notification]").shouldHave(text("Успешно!"),
                Duration.ofSeconds(15)).shouldBe(visible);
        $(".notification__content").shouldHave(text("Встреча успешно забронирована на " + date),
                Duration.ofSeconds(15)).shouldBe(visible);

    }

    @Test
    void shouldSubmitValidDataMonth() {
        Configuration.holdBrowserOpen = true;
        String date = setLocalDate(31);
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id = date] .input__control").doubleClick().sendKeys(date);
        $("[name=name]").setValue("Петров Евгений");
        $("[name=phone]").setValue("+79002223322");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//div[contains(text(), 'забронирована')]").should(appear, Duration.ofSeconds(15));$("[data-test-id = notification]").shouldHave(text("Успешно!"),
                Duration.ofSeconds(15)).shouldBe(visible);
        $(".notification__content").shouldHave(text("Встреча успешно забронирована на " + date),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    void shouldSubmitNotValidData2() {
        Configuration.holdBrowserOpen = true;
        String date = setLocalDate(2);
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id = date] .input__control").doubleClick().sendKeys(date);
        $("[name=name]").setValue("Петров Евгений");
        $("[name=phone]").setValue("+79002223322");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $(withText("невозможен")).should(visible, Duration.ofSeconds(5));
        $x("//*[@data-test-id=\"notification\"]").shouldNot(visible, Duration.ofSeconds(10));
    }

    @Test
    void shouldSubmitNotValidCity() {
        Configuration.holdBrowserOpen = true;
        String date = setLocalDate(3);
        $("[data-test-id=city] input").setValue("Солнечный");
        $("[data-test-id = date] .input__control").doubleClick().sendKeys(date);
        $("[name=name]").setValue("Петров Евгений");
        $("[name=phone]").setValue("+79002223322");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='city'].input_invalid").shouldBe(visible, Duration.ofSeconds(5))
                .should(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldSubmitNotValidCityEn() {
        Configuration.holdBrowserOpen = true;
        String date = setLocalDate(3);
        $("[data-test-id=city] input").setValue("Kazan");
        $("[data-test-id = date] .input__control").doubleClick().sendKeys(date);
        $("[name=name]").setValue("Петров Евгений");
        $("[name=phone]").setValue("+79883265502");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='city'].input_invalid").shouldBe(visible, Duration.ofSeconds(5))
                .should(exactText("Доставка в выбранный город недоступна"));
    }

}