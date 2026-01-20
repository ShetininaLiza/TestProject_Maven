package org.example.model;
import java.time.LocalDate;

//класс заметки
//extends - значит, что кпасс наследуется от другого
public class Note extends Record{
    @Override
    public void createRecord(String text, LocalDate dateCreate) {
        super.createRecord(text, dateCreate, TypeRecord.Note, -1);
    }
}
