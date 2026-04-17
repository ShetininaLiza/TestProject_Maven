package org.example.model

sealed class StateMember {
    object New : StateMember() //новое напоминание
    object Accept : StateMember() //напоминание выполнено
    object Close : StateMember() //напоминание не выполнено
}