package org.example.helper;

import org.w3c.dom.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkWithXML implements IWorkWithData{
    final String filePath;
    //список
    List<Record> records;
    XMLConstants c;

    final DocumentBuilderFactory factory;
    DocumentBuilder builder;
    TransformerFactory transformerFactory;
    Transformer transformer;
    int currentNum;

    //!Тут надо проверять наличие папки data
    public WorkWithXML(String path){
        System.out.println("Работа с XML");
        filePath = path;
        factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        } catch (Exception e) {
            builder = null;
            transformerFactory = null;
        }
        records = new ArrayList<>();
        readRecords();
        currentNum = getNum();
    }

    public void writeRecord(Record record){
        File file = new File(filePath);
        try {
            Element root = null;
            Document document = null;
            //если файл не существует
            if(!file.exists() && builder!=null) {
                var result = file.createNewFile();
                //если успешно создали файл
                if (result) {
                    // Create a new Document
                    document = builder.newDocument();
                    // Create root element
                    root = document.createElement("records");
                    document.appendChild(root);
                }
            }else{
                // Parse the XML file
                document = builder.parse(file);
                root = document.getDocumentElement();

            }
            createNode(document, transformer, root, record);
            System.out.println("Сохранение записи завершено");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    private void createNode(Document document, Transformer transformer, Node root, Record record) {
        if (document != null && transformer != null && root != null && record != null) {
            Element node = document.createElement(record.getType());

            var num = document.createElement("Num");
            int value = record.getNum();
            if(value == -1){
                value = currentNum;
                currentNum++;
            }
            num.appendChild(document.createTextNode(Integer.toString(value)));

            var text = document.createElement("Text");
            text.appendChild(document.createTextNode(record.getTextRecord()));

            var dataCreate = document.createElement("DataCreate");
            dataCreate.appendChild(document.createTextNode(record.getDateCreate()));

            var title = document.createElement("Title");
            title.appendChild(document.createTextNode(record.getTitle()));

            var tagsRecords = record.getTags();
            var tags = document.createElement("Tags");
            if(!tagsRecords.isEmpty()){
                for(var tagRecord : tagsRecords){
                    var tag = document.createElement("Tag");
                    tag.appendChild(document.createTextNode(tagRecord));
                    tags.appendChild(tag);
                }
            }

            node.appendChild(num);
            node.appendChild(title);
            node.appendChild(text);
            node.appendChild(dataCreate);
            node.appendChild(tags);


            root.appendChild(node);

            DOMSource source = new DOMSource(document);
            StreamResult result_ = new StreamResult(filePath);
            try {
                transformer.transform(source, result_);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                //throw new RuntimeException(e);
            }
        }
    }
    public List<Record> readRecords(){
        File file = new File(filePath);
        records.clear();
        try {
            if(file.exists() && builder!=null){
                Document document = builder.parse(file);
                var element = document.getDocumentElement();
                //System.out.println(element.getNodeName());
                var nodes = element.getChildNodes();
                for(int index = 0; index<nodes.getLength(); index++){
                    var node = nodes.item(index);
                    if(node.hasChildNodes()) {
                        var child = node.getChildNodes();

                        //System.out.println("INDEX: " + index + ", NODE: " + node.getNodeName() + " || " + node.hasChildNodes());
                        String text="", title="";
                        LocalDate dateCreate = LocalDate.now();
                        int num = 1;
                        for(int i=0; i<child.getLength(); i++){
                            var ch = child.item(i);
                            if(ch.getNodeType() == 1){
                                //System.out.println("________INDEX: " + i + ", NODE: " + ch.getNodeName()+" TYPE: "+ch.getNodeType()+" VALUE: "+ch.getTextContent());
                                if(ch.getNodeName().equals("Text")){
                                    text =  ch.getTextContent();
                                }
                                if(ch.getNodeName().equals("Title")){
                                    title =  ch.getTextContent();
                                }
                                if(ch.getNodeName().equals("DataCreate")){
                                    dateCreate = LocalDate.parse(ch.getTextContent());
                                }
                                if(ch.getNodeName().equals("Num")){
                                    //System.out.println("NUM: "+ch.getTextContent());
                                    num = Integer.parseInt(ch.getTextContent());
                                }
                            }
                        }
                        Record record = new Note();
                        record.createRecord(text, dateCreate, TypeRecord.Note, num);
                        record.setTitle(title);
                        records.add(record);
                    }
                }
            }else{
                System.out.println("Произошлыа ошибка при чтении файла");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //throw new RuntimeException(e);
        }
        return records;
    }
    //метод для определения номера заметки
    private int getNum(){
        int maxId = 1;
        if(!records.isEmpty()) {
            for (var rec : records) {
                if (maxId < rec.getNum()) {
                    maxId = rec.getNum();
                }
            }
            maxId++;
        }
        return maxId;
    }
}
