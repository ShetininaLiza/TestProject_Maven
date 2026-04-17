package org.example.helper;

import java.util.List;

public interface IWorkWithData {
    void writeRecord(Record record);
    List<Record> readRecords();
}
