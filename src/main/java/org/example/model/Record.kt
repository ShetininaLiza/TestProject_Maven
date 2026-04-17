package org.example.model

import com.google.gson.annotations.Expose
import java.time.LocalDate
import java.util.Collections

abstract class Record: RecordInterface {
    //номер (id)
    var num : Int = -1
    @Expose
    //текст и заголовок
    lateinit var textRecord: String
    lateinit var titleRecord : String
    @Expose
    //дата создания
    lateinit var dateCreateRecord: LocalDate
    @Expose
    lateinit var typeRecord:TypeRecord
    @Expose
     lateinit var tags: MutableList<String>

     override fun createRecord(text : String, dateCreate : LocalDate, type:TypeRecord, num_:Int) {
        if(num_!=-1) {
            num = num_
        }
        textRecord = text
        titleRecord = ""
        dateCreateRecord = dateCreate
        typeRecord = type
        tags = Collections.emptyList()
    }

    override fun getTags():List<String>{
        return tags;
    }
    //здесь надо добавить проверку на входные данные
    //если не пустые, то добавляем
    override fun setTag(tag : String){
        if(tag.length!=0){
            if(!tags.contains(tag)){
                tags.add(tag);
            }
        }
    }
    override fun setTitle(title:String){
        titleRecord = title;
    }
    override fun  getType() : String{
        var type : String = ""
        when(typeRecord){
            is TypeRecord.Note->type = "Note"
            is TypeRecord.Member->type = "Member"
            is TypeRecord.No->type=""
        }
        return  type;
    }
    abstract fun createRecord(text : String, dateCreate : LocalDate)
    override fun getTextRecord() : String{
        return textRecord
    }
    override fun getDateCreate() : String{
        return dateCreateRecord.toString()
    }
    override fun getTitle() : String{
        return titleRecord
    }
    override fun setNum(value: Int){
        num = value
    }
    override fun getNum() : Int{
        return num;
    }
    override fun getData() : String{
        var strType : String = when(typeRecord){
            is TypeRecord.Member->"Напоминание"
            is TypeRecord.Note->"Заметка"
            is TypeRecord.No->""
        }
        var data = "Id: "+num+"\nТип: "+strType+"\n"
        var test : String = "cvkcjvk"
        if(titleRecord.length!=0)
            data+="Заголовок: "+titleRecord+"\n";
        if(!tags.isEmpty()){
            var str : String =""
            tags.forEach {teg_->
                str+=teg_+" "
            }
            data+="Теги: "+str+"\n";
        }
        data+="Текст:"+textRecord+"\nДата создания: "+dateCreateRecord;
        data+="\n-------------------------------------------------------------";
        return data;
    }
    override fun setType(value : TypeRecord){
        typeRecord = value;
    }

    override fun removeTitle() {
        titleRecord = ""
    }

}