import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;


public class CardPlanningTest {

    Faker faker;
    DateClass date = new DateClass();
    String deleteString = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
    String generatedDate = DataGenerator.registration.generateInfo("ru").getMeetingDate();
    String generatedName = DataGenerator.registration.generateInfo("ru").getName();
    String generatedPhone = DataGenerator.registration.generateInfo("ru").getPhone();

    @BeforeEach
    void openChrome() {
        open("http://localhost:9999/");
    }

    @Test
    void validValueTestWithFaker() {
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue(generatedDate);
        $("[data-test-id=name] input").setValue(generatedName);
        $("[data-test-id=phone] input").setValue(generatedPhone);
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=success-notification]").should(Condition.appear);
    }

    @Test
    void validValueTest_ToReplanNotification() {
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue(generatedDate);
        $("[data-test-id=name] input").setValue("Иван Иванов-Петров");
        $("[data-test-id=phone] input").setValue("+79998887766");
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=success-notification]").should(Condition.appear);
    }

    @Test
    void validValueTestTo_ReplanlNotificationAppear() {
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue(generatedDate);
        $("[data-test-id=name] input").setValue("Иван Иванов-Петров");
        $("[data-test-id=phone] input").setValue("+79998887766");
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=replan-notification]").should(Condition.appear);
        $("[data-test-id=replan-notification] button").click();
        $("[data-test-id=success-notification]").should(Condition.appear).shouldHave(Condition.text("Встреча успешно запланирована на "));
    }

    @Test
    void invalidCity() {
        $("[data-test-id=city] input").setValue("dfsfd23r23f");
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue(generatedDate);
        $("[data-test-id=name] input").setValue(generatedName);
        $("[data-test-id=phone] input").setValue(generatedPhone);
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldBe(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void emptyCity() {
        $("[data-test-id=city] input").setValue("");
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue(generatedDate);
        $("[data-test-id=name] input").setValue(generatedName);
        $("[data-test-id=phone] input").setValue(generatedPhone);
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldBe(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void invalidName() {
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue(generatedDate);
        $("[data-test-id=name] input").setValue("Ivan Petrov");
        $("[data-test-id=phone] input").setValue(generatedPhone);
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldBe(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void emptyName() {
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue(generatedDate);
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue(generatedPhone);
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldBe(Condition.exactText("Поле обязательно для заполнения"));
    }


    @Test
    void invalidDate() {
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=name] input").setValue(generatedName);
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=phone] input").setValue(generatedPhone);
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(Condition.exactText("Неверно введена дата"));
    }

    @Test
    void invalidDateSecond() {
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=name] input").setValue(generatedName);
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue("01.01.0001");
        $("[data-test-id=phone] input").setValue(generatedPhone);
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void emptyPhone() {
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=name] input").setValue(generatedName);
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue(date.threeDaysAfter()).pressEscape();
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldBe(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void checkboxMissed() {
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue(generatedDate);
        $("[data-test-id=name] input").setValue(generatedName);
        $("[data-test-id=phone] input").setValue(generatedPhone);
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=agreement].input_invalid").should(Condition.exist);
    }

    @Test
    void notificationXMark() {
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue(generatedDate);
        $("[data-test-id=name] input").setValue(generatedName);
        $("[data-test-id=phone] input").setValue(generatedPhone);
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=success-notification]").should(Condition.appear, Duration.ofMillis(14000));
        $("[data-test-id=success-notification] button .icon-button__content").click();
    }

    @Test
    void notificationValue() {
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue(date.threeDaysAfter());
        $("[data-test-id=name] input").setValue(generatedName);
        $("[data-test-id=phone] input").setValue(generatedPhone);
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=success-notification]").should(Condition.appear, Duration.ofMillis(14000));
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + date.threeDaysAfter())).shouldBe(Condition.visible);
    }


}
