package org.example.model;
import com.google.gson.annotations.Expose;

import java.io.Serial;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//implements - показывает, что данный класс реализует интерфейс
public abstract class Record implements IRecord {
    //номер (id)
    int num = -1;
    @Expose
    //текст и заголовок
    String textRecord, titleRecord;
    @Expose
    //дата создания
    LocalDate dateCreateRecord;
    @Expose
    TypeRecord typeRecord;
    @Expose
    List<String> tags;

    public void createRecord(String text, LocalDate dateCreate, TypeRecord type, int num_) {
        if(num_!=-1) {
            num = num_;
        }
        textRecord = text;
        titleRecord = "";
        dateCreateRecord = dateCreate;
        typeRecord = type;
        tags = new ArrayList<>();
    }
    public List<String> getTags(){
        return tags;
    }
    //здесь надо добавить проверку на входные данные
    //если не пустые, то добавляем
    public void setTag(String tag){
        if(!tag.isEmpty()){
            if(!tags.contains(tag)){
                tags.add(tag);
            }
        }
    }
    //метод для удаления тега
    public void removeTag(){}

    public void setTitle(String title){
        titleRecord = title;
    }
    public void removeTitle(){
        titleRecord = "";
    }
    public String getType(){
        String type = "";
        switch (typeRecord){
            case TypeRecord.Note -> {
                type = "Note";
                break;
            }
            case TypeRecord.Member -> {
                type = "Member";
                break;
            }
            default ->{ break;}
        }
        return  type;
    }

    public abstract void createRecord(String text, LocalDate dateCreate);
    public String getTextRecord(){
        return textRecord;
    }
    public String getDateCreate(){
        return dateCreateRecord.toString();
    }
    public String getTitle(){
        return titleRecord;
    }
    public void setNum(int value){
        num = value;
    }
    public int getNum(){
        return num;
    }
    public  String getData(){
        String strType="";
        switch (typeRecord){
            case TypeRecord.Member -> strType = "Напоминание";
            case TypeRecord.Note -> strType = "Заметка";
        };

        String data = "Id: "+num+"\nТип: "+strType+"\n";
        if(!titleRecord.isEmpty())
            data+="Заголовок: "+titleRecord+"\n";
        if(!tags.isEmpty()){
            String str="";
            for(var t : tags){
                str+=t+" ";
            }
            data+="Теги: "+str+"\n";
        }
        data+="Текст:"+textRecord+"\nДата создания: "+dateCreateRecord;
        data+="\n-------------------------------------------------------------";
        return data;
    }
    public void setType(TypeRecord value){
        typeRecord = value;
    }
}
