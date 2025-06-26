# Wikipedia Search UI Tests

UI автотесты для страницы поиска на [русской Википедии](https://ru.wikipedia.org).

## Технологии

- Java
- Maven
- JUnit 5
- Selenide

## Как запустить проект

### 1. Клонируй репозиторий

git clone https://github.com/YaMorozova/VK_task1/wikipedia-search-tests.git<br>
cd wikipedia-search-tests

### 2. Запусти тесты

mvn clean test

## Структура тестов
| Тест |	Описание |
|:-----|:----------|
| suggestionsShouldStartWithQueryAndHighlightIt() |	Проверка, что саджесты начинаются с запроса и выделены жирным |
| clickingFirstSuggestionOpensCorrectPage() |	Клик по первому саджесту открывает соответствующую статью |
| clickingSearchButtonWithSuggestionsGoesToFirstSuggestion() |	По кнопке поиска открывается первая подсказка |
| clickingSearchButtonWithNoSuggestionsGoesToSearchPage() |	Если подсказок нет — переход на страницу поиска |
| clickingSearchContainingLinkIsNotAutomatable() | Тест задокументирован как неавтоматизируемый |

## Примечание
Тест clickingSearchContainingLinkIsNotAutomatable всегда падает, т.к. проверка "Поиск страниц, содержащих..." невозможно реализовать средствами WebDriver. Подробнее см. комментарий в коде теста.
