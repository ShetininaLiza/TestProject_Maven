package org.example.helper;

import org.example.model.Record;

import java.util.List;

public interface IWorkWithData {
    void writeRecord(Record record);
    List<Record> readRecords();
}
