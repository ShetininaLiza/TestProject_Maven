package org.example.model;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

//данный интерфейс предназначен для определения списка методов (!!!) для потомков
//т.е ЧТО должны делать (а как каждый потомок решает сам)
public interface IRecord {
    //метод для установки тегов
    void setTag(String tag);
    //метод для удаления тега
    void removeTag();
    //метод для создания записи
    void createRecord(String text, LocalDate dateCreate, TypeRecord type, int num_);
    //метод для установки заголовка
    void setTitle(String title);
    void removeTitle();
    String getType();
    String getTextRecord();
    String getDateCreate();
    String getTitle();
    List<String> getTags();
    void  setNum(int value);
    int getNum();
    String getData();
    void setType(TypeRecord value);
}
