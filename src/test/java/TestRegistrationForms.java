import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestRegistrationForms {
    private String genDate(int addDays, String pattern){
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }
    @Test
    void testRegistrationForm(){
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id = city] input").setValue("Москва");
        String testData = genDate(4, "dd.MM.yyyy");
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = date] input").sendKeys(testData);
        $("[data-test-id = name] input").setValue("Сан Саныч");
        $("[data-test-id = phone] input").setValue("+71112223344");
        $("[data-test-id = agreement]").click();
        $(".button").click();
        $(".notification__title").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.exactText("Встреча успешно забронирована на " + testData));
    }
}
