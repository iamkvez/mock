// Находим контейнер (например, LinearLayout в основном активити), куда будем вставлять
val container = findViewById<LinearLayout>(R.id.my_container)

// Инфлейтим (создаем) вью из XML
val inflater = LayoutInflater.from(this)
val myElement = inflater.inflate(R.layout.my_custom_item, container, false)

// Можно сразу настроить данные внутри этого элемента
myElement.findViewById<TextView>(R.id.title).text = "Привет!"

// Добавляем в контейнер
container.addView(myElement)

val myView = MyCustomView(this)
container.addView(myView)

<androidx.drawerlayout.widget.DrawerLayout 
    android:id="@+id/drawer_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <FrameLayout ... />

    <com.google.android.material.navigation.NavigationView
        android:id="@+id/nav_view"
        android:layout_width="match_parent"  android:layout_height="match_parent"
        android:layout_gravity="start" />

</androidx.drawerlayout.widget.DrawerLayout>


