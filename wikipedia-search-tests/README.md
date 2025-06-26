# Wikipedia Search UI Tests

UI автотесты для страницы поиска на [русской Википедии](https://ru.wikipedia.org).

## Технологии

- Java
- Maven
- JUnit 5
- Selenide

## Как запустить проект

Проект использует Maven Wrapper, поэтому не требуется устанавливать Maven вручную — он загрузится автоматически при первом запуске.

### 1. Клонирование репозитория

git clone https://github.com/YaMorozova/VK_task1.git<br>
cd wikipedia-search-tests

### 2. Запуск всех тестов

- ./mvnw test   - на Linux/macOS
- mvnw test     - на Windows

### Запуск одного конкретного теста

- ./mvnw -Dtest=WikipediaSearchNavigationTest#название_теста_из_таблицы test   - на Linux/macOS
- mvnw -Dtest=WikipediaSearchNavigationTest#название_теста_из_таблицы test     - на Windows

## Структура тестов

| Тест |	Описание |
|:-----|:----------|
| suggestionsShouldStartWithQueryAndHighlightIt |	Проверка, что саджесты начинаются с запроса и выделены жирным |
| clickingFirstSuggestionOpensCorrectPage |	Клик по первому саджесту открывает соответствующую статью |
| clickingSearchButtonWithSuggestionsGoesToFirstSuggestion |	По кнопке поиска открывается первая подсказка |
| clickingSearchButtonWithNoSuggestionsGoesToSearchPage |	Если подсказок нет — переход на страницу поиска |
| clickingSearchContainingLinkIsNotAutomatable | Тест задокументирован как неавтоматизируемый |

## Примечание

Тест clickingSearchContainingLinkIsNotAutomatable всегда падает, т.к. проверка "Поиск страниц, содержащих..." невозможно реализовать средствами WebDriver. Подробнее см. комментарий в коде теста.
