package org.example;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import org.example.helper.IWorkWithData;
import org.example.helper.WorkWithXML;
import org.example.helper.WorkWithJSON;

import org.ini4j.Ini;

record Third<K>(K first, K second, K third) { }

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static HashMap<String, Third<String>> dataKey = new HashMap<String, Third<String>>();
    static Scanner in;
    static String pathXML = "data/records.xml";
    static String pathJSON = "data/records.json";
    static String pathConfig = "data/config.ini";

    //static WorkWithXML xml;
    //static WorkWithJSON json;
    static IWorkWithData workWithData;

    public static void main(String[] args) {
        try{
            Properties props = new Properties();
            props.load(new FileInputStream(new File(pathConfig)));
            //String config = props.getProperty("data").toString();

            Ini ini = new Ini(new File(pathConfig));
            String config = ini.get("data", "value");

            if(!config.isEmpty()){
                if(config.equals("xml")){
                    workWithData = new WorkWithXML(pathXML);
                }else if(config.equals("json")){
                    workWithData = new WorkWithJSON(pathJSON);
                }
            }else{
                workWithData = new WorkWithXML(pathXML);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            workWithData = new WorkWithXML(pathXML);
        }


        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how GIGA IDE suggests fixing it.
        System.out.println("Тестовая версия программы планера (консольная версия)");
        createKey();
        in = new Scanner(System.in);

        while(true) {
            start();
        }
    }
    private static void start(){
        printHelp();
        System.out.print("Введите нужное действие: ");
        try {
            var value = in.next();
            //System.out.println("Value: "+value);
            checkKey(value);
        } catch (Exception e) {
            System.out.println("Что-то пошло не так!!!! "+e.getMessage());
            //по логике, здесь надо повторить ввод числа
        }
    }
    //печать справки
    private static void printHelp(){
        for (var key : dataKey.keySet()){
            var value = dataKey.get(key);
            System.out.println(key+" "+value.third());
        }
        System.out.println();
    }
    //формирование списка ключей для выбора действий
    private static void createKey(){
        dataKey.clear();
        dataKey.put("-cN", new Third<String>("c", "N", "Добавить заметку"));
        dataKey.put("-cM", new Third<String>("c", "M", "Добавить напоминание"));
        dataKey.put("-r", new Third<String>("r", "", "Удалить запись"));
        dataKey.put("-p", new Third<String>("p", "", "Вывести все записи"));
        dataKey.put("-uT", new Third<String>("u", "", "Добавить заголовок"));
        dataKey.put("-tags", new Third<String>("u", "", "Добавить теги"));
        dataKey.put("-x", new Third<String>("x", "", "Выйти из программы"));
    }
    //проверка введенного ключа
    private static void checkKey(String key) throws Exception {
        if(!dataKey.containsKey(key)){
            throw new Exception("Нет такого значения");
        }else{
            //System.out.println(dataKey.get(key));
            var data = dataKey.get(key);
            if(data.first().equals("c")){
                System.out.println("CREATE");
                System.out.print("Введите текст: ");
                var text = in.next();
                var date = LocalDate.now();
                Record record = new Note();
                if(data.second().equals("N")){
                    //record = new Note();
                    record.createRecord(text, date);
                }else if(data.second().equals("M")){
                    record = new Member();
                    record.createRecord(text, date);
                }
                saveRecord(record);
            }
            if(data.first().equals("p")){
                //var recs = xml.readRecords();
                var recs = workWithData.readRecords();
                recs.forEach(rec->System.out.println(rec.getData()));
                System.out.println();
            }
            if(data.first().equals("x")){
                System.out.println("Выход из программы");
                System.exit(0);
            }
        }
    }
    public  static void saveRecord(Record record){
        //xml.writeRecord(record);
        workWithData.writeRecord(record);
    }
}