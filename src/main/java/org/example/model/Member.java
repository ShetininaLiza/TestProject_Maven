package org.example.model;
import java.time.LocalDate;
import java.util.Date;

public class Member extends Record{
    //дата напоминания
    Date dateMember;
    //по умолчанию новое напоминание
    StateMember state = StateMember.New;
    public void setDateMember(Date value){
        dateMember = value;
    }
    public void setStatusMember(StateMember value){
        state = value;
    }

    @Override
    public void createRecord(String text, LocalDate dateCreate) {
        super.createRecord(text, dateCreate, TypeRecord.Member, -1);
    }
}
