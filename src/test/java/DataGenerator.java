import com.github.javafaker.Faker;
import lombok.Value;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@UtilityClass
public class DataGenerator {


    public String randomName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        return firstName + " " + lastName;
    }

    public String randomCity() {
        Faker fake = new Faker();
        String[] cities = new String[5];

        cities[0] = "Краснодар";
        cities[1] = "Ростов-на-Дону";
        cities[2] = "Владивосток";
        cities[3] = "Москва";
        cities[4] = "Санкт-Петербург";

        return cities[fake.number().numberBetween(0, 5)];
    }

    public String randomPhoneNumber(String locale) {
        Faker fake = new Faker(new Locale(locale));
        return fake.phoneNumber().phoneNumber();
    }

    public String generateDate(int days) {

        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    public static class registration {

        private registration() {
        }

        public static UserInfo generateInfo(String locale) {
            return new UserInfo(randomCity(), randomName(locale), randomPhoneNumber(locale));
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
