package org.example.model

import java.time.LocalDate

interface RecordInterface {
    //метод для установки тегов
    fun setTag(tag : String)
    //TODO (это надо будет добавить)
    //метод для удаления тега
    //fun removeTag()

    //метод для создания записи
    fun createRecord(text:String, dateCreate: LocalDate,  type:TypeRecord,  num_:Int)
    //метод для установки заголовка
    fun setTitle( title:String)

    //TODO (это надо будет добавить)
    fun removeTitle()

    fun getType():String
    fun getTextRecord():String
    fun getDateCreate():String
    fun getTitle():String
    fun getTags():List<String>
    fun setNum(value : Int)
    fun getNum(): Int
    fun getData():String
    fun setType(value : TypeRecord)
}