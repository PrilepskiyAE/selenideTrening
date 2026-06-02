package fakestoreapi;

import io.restassured.response.Response;
import model.ProductData;
import model.Rating;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.testng.AssertJUnit.*;

public class FakestoreapiTest {
    private final static String URL = "https://fakestoreapi.com";
    /**   {
     "id": 0,
     "title": "string",
     "price": 0.1,
     "description": "string",
     "category": "string",
     "image": "http://example.com"
     }
     */

    ProductData product = new ProductData(
            999,
            "New Product",
            29.99f,
            "string",
            "string",
            "string",
            new Rating(
                    0,0
            )
    );

    /**
     * Проверяет структуру и базовые валидации ответа от /products.
     * <p>
     * В рамках теста проверяются:
     * <ol>
     *   <li>Статус‑код 200 OK</li>
     *   <li>Content-Type: application/json</li>
     *   <li>Наличие хотя бы одного элемента в массиве</li>
     *   <li>Заполненность и корректность полей первого продукта (id, title, price, description, category, image)</li>
     *   <li>Соответствие хотя бы одной категории из предопределённого списка</li>
     * </ol>
     * <p>
     * Особое внимание уделяется:
     * <ul>
     *   <li>Положительной цене товара (price > 0)</li>
     *   <li>Форматированию URL изображения (начинается с http:// или https://)</li>
     *   <li>Принадлежности к одной из базовых категорий магазина</li>
     * </ul>
     */
    @Test
    public void correctStruct() {
        given()
                .log().all()
                .when()
                .get(URL+"/products")
                .then()
                .log().all()
                .statusCode(200)
                .contentType("application/json")
                .body("$", hasSize(greaterThan(0)))
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue())
                .body("[0].price", greaterThan(0f))
                .body("[0].description", notNullValue())
                .body("[0].category", notNullValue())
                .body("[0].image", matchesPattern("https?://.*"))
                .body("category", hasItem(
                        anyOf(
                                equalTo("electronics"),
                                equalTo("jewelery"),
                                equalTo("men's clothing"),
                                equalTo("women's clothing")
                        )
                ));
    }

    /**
     * Проверяет, что ответ от эндпоинта /products корректно мапится в объекты ProductData.
     * <p>
     * Тест подтверждает:
     * <ul>
     *   <li>Успешный HTTP-статус (200)</li>
     *   <li>Наличие хотя бы одного продукта в ответе</li>
     *   <li>Заполненность ключевых полей (title, category, image, description) первого продукта</li>
     *   <li>Положительное значение цены (price > 0)</li>
     * </ul>
     */

    @Test
    public void correctStructToPOJO() {
        Response response = given()
                .log().all()
                .when()
                .get(URL+"/products")
                .then()
                .statusCode(200)
                .extract().response();

        List<ProductData> products = response.jsonPath().getList("", ProductData.class);

        assertNotNull(products);
        assertFalse(products.isEmpty());

        ProductData firstProduct = products.get(0);
        assertNotNull(firstProduct.getTitle());
        assertTrue(firstProduct.getPrice() > 0);
        assertNotNull(firstProduct.getCategory());
        assertNotNull(firstProduct.getImage());
        assertNotNull(firstProduct.getDescription());
    }
    /**
     * Проверяет, что создание продукта через POST-запрос завершается успешно
     * и возвращает корректный ответ от сервера.
     *
     * <p>Тест отправляет POST-запрос к API для создания нового продукта
     * с предопределёнными данными ({@code title = "New Product", price = 29.99}).
     * Затем верифицирует:
     * <ul>
     *   <li>HTTP-статус ответа равен 201 (Created) — что означает успешное создание ресурса;</li>
     *   <li>Поле {@code title} в теле ответа совпадает с отправленным значением;</li>
     *   <li>Поле {@code price} в ответе соответствует отправленному (с учётом приведения к float);</li>
     *   <li>Сервер вернул поле {@code id} (оно не null) — уникальный идентификатор созданного продукта.</li>
     * </ul>
     *
     * @throws AssertionError если любой из ожидаемых критериев не выполнен
     *                        (неверный статус, несоответствие полей или отсутствующий id)
     */
    @Test
    public void createProductShouldReturn201AndCorrectData() {


        given()
                .contentType("application/json")
                .body(product)
                .when()
                .post(URL+"/products")
                .then()
                .statusCode(201)
                .body("title", equalTo("New Product"))
                .body("price", equalTo(29.99f))
                .body("id", notNullValue());
    }
    /**
     * Проверяет структуру (схему) ответа при создании продукта через POST-запрос.
     *
     * <p>Тест отправляет запрос на создание нового продукта и верифицирует, что типы данных
     * в ответе от сервера соответствуют ожидаемым:
     * <ul>
     *   <li>поле {@code title} имеет тип {@code String};</li>
     *   <li>поле {@code price} имеет тип, совместимый с {@code Number} (что позволяет корректно обрабатывать
     *       как {@code float}, так и {@code double});</li>
     *   <li>поле {@code id} имеет тип {@code Integer} — это идентификатор, присвоенный продукту сервером.</li>
     * </ul>
     *
     * <p>Хотя тест не использует внешнюю JSON Schema (через {@code JsonSchemaValidator}), он обеспечивает базовую
     * типовую валидацию ключевых полей ответа, что помогает выявлять ошибки сериализации и несоответствия контракта API.
     *
     * @throws AssertionError если HTTP-статус не равен 201 либо тип любого из проверяемых полей
     *                        в ответе не соответствует ожидаемому
     *
     */
    @Test
    public void createProductValidateResponseSchema() {

        given()
                .contentType("application/json")
                .body(product)
                .when()
                .post(URL+"/products")
                .then()
                .statusCode(201)
                .body("title", instanceOf(String.class))
                .body("price", instanceOf(Number.class))
                .body("id", instanceOf(Integer.class));
    }

    /**
     * Проверяет, что GET-запрос для получения продукта по идентификатору возвращает успешный ответ
     * и корректные данные.
     *
     * <p>Тест выполняет запрос {@code GET /products/1} к API и верифицирует ключевые аспекты ответа:
     * <ul>
     *   <li>HTTP-статус равен 200 (OK) — запрос успешно обработан сервером;</li>
     *   <li>поле {@code id} в ответе равно 1 — соответствует запрошенному идентификатору продукта;</li>
     *   <li>поле {@code title} присутствует (не null) — у продукта есть название;</li>
     *   <li>поле {@code price} больше нуля — значение цены валидно и имеет экономический смысл;</li>
     *   <li>поля {@code description}, {@code category} и {@code image} присутствуют в ответе
     *       (не равны null) — все обязательные атрибуты продукта доступны.</li>
     * </ul>
     *
     * <p>Этот тест служит базовым чек-листом для проверки целостности данных при чтении продукта
     * из API: он подтверждает, что сервер возвращает полный набор ожидаемых полей
     * с логически корректными значениями.
     *
     * @throws AssertionError если:
     *         <ul>
     *           <li>статус ответа не равен 200;</li>
     *           <li>значение {@code id} не соответствует запрошенному (1);</li>
     *           <li>любое из обязательных полей ({@code title}, {@code description},
     *               {@code category}, {@code image}) отсутствует;</li>
     *           <li>значение {@code price} меньше или равно нулю.</li>
     *         </ul>
     *
     */
    @Test
    public void getProductByIdShouldReturn200AndCorrectData() {
        given()
                .when()
                .get(URL+"/products/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", notNullValue())
                .body("price", greaterThan(0f))
                .body("description", notNullValue())
                .body("category", notNullValue())
                .body("image", notNullValue());
    }

    /**
     * Проверяет структурную корректность (типы полей) ответа при запросе продукта по идентификатору.
     *
     * <p>Тест отправляет запрос {@code GET /products/1} и верифицирует, что типы данных в ответе
     * соответствуют ожидаемому контракту API:
     * <ul>
     *   <li>{@code id} имеет тип {@code Integer} — целочисленный идентификатор продукта;</li>
     *   <li>{@code title} имеет тип {@code String} — текстовое название товара;</li>
     *   <li>{@code price} имеет тип, совместимый с {@code Number} — позволяет корректно обрабатывать
     *       числовые значения (включая {@code Float}, {@code Double} и т. д.);</li>
     *   <li>{@code description}, {@code category} и {@code image} имеют тип {@code String} —
     *       строковые поля для описания, категории и URL изображения соответственно.</li>
     * </ul>
     *
     * <p>Такая проверка помогает выявлять ошибки сериализации на стороне сервера, несоответствия
     * спецификации API и потенциальные проблемы при десериализации на клиенте (например,
     * когда число приходит в виде строки или наоборот).
     *
     * @throws AssertionError если:
     *         <ul>
     *           <li>статус ответа не равен 200;</li>
     *           <li>тип любого из проверяемых полей не соответствует ожидаемому;</li>
     *           <li>поле отсутствует в ответе (в этом случае {@code instanceOf} также провалится).</li>
     *         </ul>
     */

    @Test
    public void getProductByIdValidateResponseStructure() {
        given()
                .when()
                .get(URL+"/products/1")
                .then()
                .statusCode(200)
                .body("id", instanceOf(Integer.class))
                .body("title", instanceOf(String.class))
                .body("price", instanceOf(Number.class))
                .body("description", instanceOf(String.class))
                .body("category", instanceOf(String.class))
                .body("image", instanceOf(String.class));
    }
}
