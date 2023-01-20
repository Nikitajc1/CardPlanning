import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

@UtilityClass
public class DataGenerator {

    @UtilityClass
    public static class registration{

        public int random3or7() {
            Random r = new Random();
            int low = 3;
            int high = 7;
            return r.nextInt(high - low) + low;
        }

        public String name() {
            Faker faker = new Faker(new Locale("ru"));
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            return firstName + lastName;
        }

        public static RegistrationByCardInfo generateInfo(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new RegistrationByCardInfo(
                    faker.name().fullName(),
                    faker.phoneNumber().phoneNumber(),
                    LocalDate.now().plusDays(random3or7()).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            );
        }

    }
}
