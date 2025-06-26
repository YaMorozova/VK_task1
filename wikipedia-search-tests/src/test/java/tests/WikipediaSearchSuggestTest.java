package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.WikipediaSearchPage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WikipediaSearchSuggestTest {

    WikipediaSearchPage page = new WikipediaSearchPage();

    @BeforeEach
    void setup() {
        Configuration.headless = true;
        Configuration.browserSize = "1280x800";
        page.openMainPage();
    }

    @Test
    void suggestionsShouldStartWithQueryAndHighlightIt() {
        String query = "иван";
        page.typeSearch(query);

        List<String> suggestions = page.getSuggestions();

        // Проверка: первые саджесты начинаются на "иван"
        assertFalse(suggestions.isEmpty(), "The sagets did not appear");
        assertTrue(suggestions.get(0).toLowerCase().startsWith(query),
                "The first suggestion does not begin with a request");

        // Проверка: поисковый запрос выделен жирным
        List<String> rawHtmls = page.getRawSuggestionsHtml();
        
        System.out.println("HTML suggestions:");
        rawHtmls.forEach(System.out::println);

        boolean foundHighlighted = rawHtmls.stream()
            .anyMatch(html -> html.toLowerCase().contains("<span class=\"highlight\">" + query.toLowerCase() + "</span>"));


        assertTrue(foundHighlighted, "The request is not highlighted in bold in the suggestions");
    }
    
    
}

