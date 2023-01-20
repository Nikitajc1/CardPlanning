import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateClass {

    public static void main(String[] args) {
    }

    public String threeDaysAfter() {

        return LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
