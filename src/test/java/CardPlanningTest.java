import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class CardPlanningTest {

    String deleteString = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
    DataGenerator.UserInfo validUser = DataGenerator.registration.generateInfo("ru");

    @BeforeEach
    void openChrome() {
        open("http://localhost:9999/");
    }

    @Test
    void replanInSameWindowTest() {
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue(DataGenerator.generateDate(3));
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=success-notification]").should(Condition.appear);
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue(DataGenerator.generateDate(7));
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=replan-notification]").should(Condition.appear);
        $("[data-test-id=replan-notification] button").click();
        $("[data-test-id=success-notification]").should(Condition.appear).shouldHave(Condition.text("Встреча успешно запланирована на " + DataGenerator.generateDate(7)));
    }

    @Test
    void validValueTestWithFaker() {
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue(DataGenerator.generateDate(3));
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=success-notification]").should(Condition.appear);
    }

    @Test
    void invalidCity() {
        $("[data-test-id=city] input").setValue("dfsfd23r23f");
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue(DataGenerator.generateDate(3));
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldBe(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void emptyCity() {
        $("[data-test-id=city] input").setValue("");
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue(DataGenerator.generateDate(3));
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldBe(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void invalidName() {
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue(DataGenerator.generateDate(3));
        $("[data-test-id=name] input").setValue("Ivan Petrov");
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldBe(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void emptyName() {
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue(DataGenerator.generateDate(3));
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldBe(Condition.exactText("Поле обязательно для заполнения"));
    }


    @Test
    void invalidDate() {
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(Condition.exactText("Неверно введена дата"));
    }

    @Test
    void invalidDateSecond() {
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue("01.01.0001");
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void emptyPhone() {
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue(DataGenerator.generateDate(3)).pressEscape();
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldBe(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void checkboxMissed() {
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue(DataGenerator.generateDate(3));
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=agreement].input_invalid").should(Condition.exist);
    }

    @Test
    void notificationXMark() {
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue(DataGenerator.generateDate(3));
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=success-notification]").should(Condition.appear, Duration.ofMillis(14000));
        $("[data-test-id=success-notification] button .icon-button__content").click();
    }

    @Test
    void notificationValue() {
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").setValue(deleteString);
        $("[data-test-id=date] input").setValue(DataGenerator.generateDate(3));
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=success-notification]").should(Condition.appear, Duration.ofMillis(14000));
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + DataGenerator.generateDate(3))).shouldBe(Condition.visible);
    }


}
