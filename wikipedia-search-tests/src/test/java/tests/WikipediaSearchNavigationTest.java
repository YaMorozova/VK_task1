package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import pages.WikipediaSearchPage;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.Wait;


public class WikipediaSearchNavigationTest {

    private final WikipediaSearchPage page = new WikipediaSearchPage();
    
    // Тестовые данные
    private static final String VALID_QUERY = "Иван";
    private static final String INVALID_QUERY = "Иваннннн";

    @BeforeEach
    void setup() {
        Configuration.headless = true;
        Configuration.browserSize = "1280x800";
        page.openMainPage();
    }
    
    @Test
    @DisplayName("1. Саджесты начинаются с запроса и выделяют его жирным")
    void suggestionsShouldStartWithQueryAndHighlightIt() {
        // Вводим запрос
        page.typeSearch(VALID_QUERY);

        List<String> suggestions = page.getSuggestions();

        // Проверяем, что первые саджесты начинаются на запрос
        assertFalse(suggestions.isEmpty(), "The sagets did not appear");
        assertTrue(suggestions.get(0).toLowerCase().startsWith(VALID_QUERY.toLowerCase()),
                "The first suggestion does not begin with a request");

        // Проверяем, что поисковый запрос выделен жирным
        List<String> rawHtmls = page.getRawSuggestionsHtml();
        
        System.out.println("HTML suggestions:");
        rawHtmls.forEach(System.out::println);

        boolean foundHighlighted = rawHtmls.stream()
            .anyMatch(html -> html.toLowerCase().contains("<span class=\"highlight\">" + VALID_QUERY.toLowerCase() + "</span>"));


        assertTrue(foundHighlighted, "The request is not highlighted in bold in the suggestions");
    }

    @Test
    @DisplayName("2. Клик по первому саджесту открывает корректную статью")
    void clickingFirstSuggestionOpensCorrectPage() {

        // Вводим запрос
        page.typeSearch(VALID_QUERY);

        // Получаем список саджестов
        List<String> suggestions = page.getSuggestions();
        assertFalse(suggestions.isEmpty(), "Suggestions did not appear");

        String firstSuggestion = suggestions.get(0);

        // Кликаем по первому саджесту
        page.clickFirstSuggestion();

        // Проверяем заголовок страницы — он должен совпадать с саджестом
        String heading = $("h1").text().trim();

        assertEquals(firstSuggestion, heading,
                "Page heading doesn't match the suggestion clicked");
    }
    
    @Test
    @DisplayName("3. Кнопка поиска с саджестами открывает первую статью")
    void clickingSearchButtonWithSuggestionsGoesToFirstSuggestion() {
        page.typeSearch(VALID_QUERY);

        // Ждём появления саджестов
        page.waitForSuggestions();

        // Получаем текст первого саджеста до клика
        String firstSuggestion = page.getFirstSuggestionText();

        // Нажимаем кнопку поиска
        page.clickSearchButton();

        // Получаем заголовок страницы
        String heading = $("h1").text().trim();

        // Проверяем, что заголовок содержит текст первой подсказки
        assertTrue(heading.contains(firstSuggestion),
                "The page title does not contain the article title from the first suggestion. Expected: " + firstSuggestion + ", was: " + heading);
    }



    @Test
    @DisplayName("4. Кнопка поиска без саджестов ведёт на страницу поиска")
    void clickingSearchButtonWithNoSuggestionsGoesToSearchPage() {
        page.typeSearch(INVALID_QUERY);

        // Проверяем, что подсказок действительно нет
        page.waitForNoSuggestions();

        // Нажимаем на кнопку поиска
        page.clickSearchButton();

        // Явное ожидание: либо URL содержит 'search=', либо заголовок указывает на страницу поиска
        Wait().until(driver ->
                WebDriverRunner.url().contains("search=") ||
                $(By.id("firstHeading")).exists() &&
                $(By.id("firstHeading")).getText().toLowerCase().contains("результаты поиска")
        );

        // Проверяем, что мы действительно на странице поиска
        String currentUrl = WebDriverRunner.url();
        assertTrue(currentUrl.contains("search="),
                "Expected to be on search results page, but was: " + currentUrl);
    }
    
    
    @Test
    @DisplayName("Невозможно автоматизировать клик по ссылке 'Поиск страниц, содержащих'")
    void clickingSearchContainingLinkIsNotAutomatable() {
        // Этот тест задокументирован как неавтоматизируемый.

        /*
         * Причина:
         * Ссылка "Поиск страниц, содержащих..." появляется только при ручном вводе запроса с клавиатуры.
         * При программном вводе текста (через setValue() и sendKeys())
         * эта подсказка не появляется.
         *
         */

        fail("We cannot automate the test: the 'Search pages containing...' element appears only when entered manually.");
    }

}


