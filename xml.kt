// Находим контейнер (например, LinearLayout в основном активити), куда будем вставлять
val container = findViewById<LinearLayout>(R.id.my_container)

// Инфлейтим (создаем) вью из XML
val inflater = LayoutInflater.from(this)
val myElement = inflater.inflate(R.layout.my_custom_item, container, false)

// Можно сразу настроить данные внутри этого элемента
myElement.findViewById<TextView>(R.id.title).text = "Привет!"

// Добавляем в контейнер
container.addView(myElement)

====================================

val myView = MyCustomView(this)
container.addView(myView)


========/=/=/=/=/=/=//4/=_=_÷_÷_=<=>2_÷_=<_=
import kotlinx.coroutines.*

private var timerJob: Job? = null
private var timeInMillis = 0L

fun startTimer() {
    timerJob = CoroutineScope(Dispatchers.Main).launch {
        while (isActive) {
            timeInMillis += 10
            updateText(timeInMillis)
            delay(10) // Задержка 10 мс
        }
    }
}

private fun updateText(time: Long) {
    val minutes = (time / 1000) / 60
    val seconds = (time / 1000) % 60
    // Если нужно именно 00:00 формат:
    findViewById<TextView>(R.id.timerText).text = 
        String.format("%02d:%02d", minutes, seconds)
}

fun stopTimer() {
    timerJob?.cancel()
}

//=/=/=/=/=/=_÷>÷_=>÷_=[=_=[#_÷>=_=_÷/#>=_÷>=_=
val navView = findViewById<NavigationView>(R.id.nav_view)
val params = navView.layoutParams
// Устанавливаем ширину равной ширине экрана
params.width = resources.displayMetrics.widthPixels
navView.layoutParams = params



