package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class SampleTest {

    @Test
    void openWikipediaTest() {
        Configuration.headless = true;
        open("https://ru.wikipedia.org");
    }
}

