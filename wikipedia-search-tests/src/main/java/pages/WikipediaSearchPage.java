package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.size;

public class WikipediaSearchPage {
    
    // Тестовые данные
    private static final String TEST_URL = "https://ru.wikipedia.org";
    
    // Элементы
    private final SelenideElement searchInput = $("#searchInput");
    private final ElementsCollection suggestions = $$(".suggestions-results a.mw-searchSuggest-link");

    // ДЕЙСТВИЯ

    // Открыть главную страницу Википедии
    public void openMainPage() {
        open(TEST_URL);
    }

    // Ввести текст в строку поиска
    public void typeSearch(String query) {
        searchInput.setValue(query);
    }

    // Нажать на кнопку поиска (лупа)
    public void clickSearchButton() {
        $("#searchButton").shouldBe(Condition.visible).click();
    }


    // Получить список текстов саджестов
    public List<String> getSuggestions() {
        suggestions.shouldBe(sizeGreaterThan(0), Duration.ofSeconds(5));
        return suggestions.asFixedIterable().stream()
                .map(SelenideElement::getText)
                .collect(Collectors.toList());
    }

    // Клик по первому саджесту
    public void clickFirstSuggestion() {
        suggestions.first().shouldBe(visible).click();
    }

    // Получить HTML строки саджестов (для проверки жирности)
    public List<String> getRawSuggestionsHtml() {
        return suggestions
            .asFixedIterable().stream()
            .map(SelenideElement::innerHtml)
            .collect(Collectors.toList());
    }
    
    // Получить текст первой подсказки
    public String getFirstSuggestionText() {
        return suggestions.first().shouldBe(visible).getText();
    }
    
    // Ожидание появления саджестов
    public void waitForSuggestions() {
        $$(".suggestions-results div")
            .shouldBe(sizeGreaterThan(0), Duration.ofSeconds(5));
    }
    
    // Проверка, что подсказок нет
    public void waitForNoSuggestions() {
        $$(".suggestions-results a.mw-searchSuggest-link")
            .shouldHave(size(0));
    }
}
