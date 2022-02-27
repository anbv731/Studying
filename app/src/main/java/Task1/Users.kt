package com.example.studying

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*




enum class Type { DEMO, FULL }

class User(id: Int, name: String, age: Int, type: Type) {
    var id = id
    var name = name
    var age = age
    var type = type
    val startTime: String by lazy {
        SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(Date())
    }
}

fun User.ageFilter() {
    if (this.age >= 18) {
        println("Пользователь ${this.name} старше 18 лет")
            } else {
        java.lang.Exception("Возрастное ограничение!")
    }
}

interface AuthCallback {
    fun authSuccess()
    fun authFailed()

}
fun updateCache (){
    println("Кэш Обновлен")
}

inline fun auth( user:User ,inLine: () -> Unit) {
    try{user.ageFilter()
        authCallback.authSuccess()
        inLine()}
    catch(ex: Exception){
        authCallback.authFailed()
    }
}

sealed class Action {}
class Registration : Action() {}
class LogIn(val user: User) : Action() {}
class LogOut : Action() {}

val authCallback = object : AuthCallback {
    override fun authFailed() {
        println("Authentication failed")
    }

    override fun authSuccess() {
        println("Authentication successful")
    }
}


fun doAction(action: Action) {
    if (action is Registration) {
        println("Начало регистрации")
    } else if (action is LogIn) {
        println("Вход")
        auth(action.user){ updateCache()}
    } else if (action is LogOut) {
        println("Выход")
    }
}

fun users () {


    val user1 = User(1, "Jack", 19, Type.FULL)

    println("User Start Time: ${user1.startTime}")
    Thread.sleep(1000)
    println("User Start Time: ${user1.startTime}")

    var userList = mutableListOf<User>()
    userList.add(user1)
    userList.apply {
        this.add(User(3, "Zeus", 99999, Type.FULL))
        userList.add(User(2, "Io", 18, Type.DEMO))

    }

    userList.forEach() { println(it.name) }
    val fullUserList = userList.filter {
        it.type == Type.FULL
    }
    println("Пользователи с полным доступом")
    fullUserList.forEach() { println("${it.name}") }

    val userNames = userList.map { it.name }
    println("First: ${userNames.first()}, Last: ${userNames.last()}")


    doAction(LogIn(user1))


}


