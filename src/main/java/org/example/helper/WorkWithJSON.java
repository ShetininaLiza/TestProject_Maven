package org.example.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

import org.example.helper.adapter.*;

public class WorkWithJSON implements  IWorkWithData{
    //путь к файлу
    private final String filePath;
    private Gson gson;

    public WorkWithJSON(String path){
        System.out.println("Работа с JSON");
        filePath = path;
        //метод setPrettyPrinting нужен для форматирования файла
        //метод excludeFieldsWithoutExposeAnnotation добавляет только те поля, которые имеют аннотацию
        //метод registerTypeAdapter нужен для регистрации типа адаптера
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .registerTypeAdapter(Record.class, new RecordAdapter())
                .create();
    }

    public void writeRecord(Record record){
        try{
            File file = new File(filePath);
            boolean isExist = file.exists();
            if(!isExist){
                isExist = file.createNewFile();
            }
            if(isExist){
                var list = readRecords();
                int id = 1;
                if(!list.isEmpty()){
                    for(var item : list){
                        if(item.getNum()>id){
                            id = item.getNum();
                        }
                    }
                    id++;
                    System.out.println("ID: "+id);
                }
                record.setNum(id);
                //надо проверить есть ли там данные
                var size = file.length();
                // Java Object to a file
                try (FileWriter writer = new FileWriter(filePath, true)) {
                    //в файле ничего нет
                    if(size == 0) {
                        Type listItemType = new TypeToken<ArrayList<Record>>() {}.getType();
                        var data = gson.toJson(Arrays.asList(record), listItemType);
                        writer.write(data);
                    }else{
                        Type itemType = new TypeToken<Record>() {}.getType();
                        FileReader reader = new FileReader(filePath);
                        int index = reader.readAllAsString().lastIndexOf(']');
                        reader.close();
                        var data = gson.toJson(record, itemType);
                        RandomAccessFile ac = new RandomAccessFile(file, "rw");
                        ac.seek(index);
                        var buf = ","+data+"]";
                        ac.write(buf.getBytes());
                        ac.close();
                    }
                    System.out.println("Данные успешно сохранены");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            //throw new RuntimeException(e);
            System.out.println(e.getMessage());
            return;
        }
    }
    public List<Record> readRecords(){
        ArrayList<Record> records = new ArrayList<>();
        File file = new File(filePath);
        try{
            if(file.exists()){
                Reader reader = Files.newBufferedReader(Path.of(filePath),
                        StandardCharsets.UTF_8);
                // create a List<Item>
                //Type listItemType = new TypeToken<ArrayList<Record>>() {}.getType();
                Type itemType = new TypeToken<List<Record>>() {}.getType();
                records = gson.fromJson(reader, itemType);

                /*
                if(records != null) {
                    records.forEach(d -> System.out.println(d.getData()));
                }else{
                    records = new ArrayList<>();
                }
                */
                if(records == null){
                    records = new ArrayList<>();
                }
            }
        } catch (Exception e) {
            //throw new RuntimeException(e);
            System.out.println(e.getMessage());
        }
        return  records;
    }
}
