import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

open class BaseNetworkTest {
    protected lateinit var mockWebServer: MockWebServer
    protected lateinit var baseUrl: String

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/").toString()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}

class ApiTests : BaseNetworkTest() {

    // СЦЕНАРИЙ 1: Успешный GET запрос (200 OK)
    @Test
    fun `test successful GET request`() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""{"status": "success", "data": []}""")
        mockWebServer.enqueue(mockResponse)

        val result = myApiService.getData(baseUrl) // твой метод
        
        assert(result.isSuccessful)
        assertEquals(200, result.code())
    }

    // СЦЕНАРИЙ 2: Ошибка "Не найдено" (404 Not Found)
    @Test
    fun `test error 404`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(404))

        val result = myApiService.getData(baseUrl)

        assertEquals(404, result.code())
        // Тут проверяй, что приложение не упало, а корректно обработало ошибку
    }

    // СЦЕНАРИЙ 3: Ошибка сервера (500 Internal Server Error)
    @Test
    fun `test server crash 500`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        val result = myApiService.getData(baseUrl)

        assertEquals(500, result.code())
    }

    // СЦЕНАРИЙ 4: Проверка отправки данных (POST)
    @Test
    fun `test POST request body`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(201))

        myApiService.sendUser(baseUrl, User("Pasha", 18))

        // Проверяем, что реально ушло на "сервер"
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("POST", recordedRequest.method)
        assert(recordedRequest.body.readUtf8().contains("Pasha"))
    }

    // СЦЕНАРИЙ 5: Медленный интернет (Timeout)
    @Test
    fun `test slow network response`() {
        val mockResponse = MockResponse()
            .setBody("""{"id":1}""")
            .setBodyDelay(5, TimeUnit.SECONDS) // Задержка 5 сек
        
        mockWebServer.enqueue(mockResponse)

        // Здесь можно проверить, срабатывает ли твой тайм-аут в OkHttpClient
    }

    // СЦЕНАРИЙ 6: Проверка заголовков (например, Token)
    @Test
    fun `test auth header presence`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200))

        myApiService.getPrivateData(baseUrl)

        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("Bearer my_token", recordedRequest.getHeader("Authorization"))
    }

    // СЦЕНАРИЙ 7: Кривой JSON (Malformed JSON)
    @Test
    fun `test malformed json parsing`() {
        // Подсовываем битый JSON
        mockWebServer.enqueue(MockResponse().setBody("""{"id": 1, "name": """)) 

        // Тут проверяешь, что твой парсер (Gson/Kotlinx) выкинет исключение, 
        // а не просто молча убьет приложение
    }
}

testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3") // если юзаешь suspend функции