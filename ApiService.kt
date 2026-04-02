object NetworkClient {
    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    // Базовый URL, чтобы не писать его везде
    private const val BASE_URL = "https://my-api.com"

    // Универсальный метод для GET
    fun get(endpoint: String, callback: Callback) {
        val request = Request.Builder()
            .url("$BASE_URL$endpoint")
            .build()
        client.newCall(request).enqueue(callback)
    }

    // Универсальный метод для POST
    fun post(endpoint: String, jsonBody: String, callback: Callback) {
        val body = jsonBody.toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url("$BASE_URL$endpoint")
            .post(body)
            .build()
        client.newCall(request).enqueue(callback)
    }
}

