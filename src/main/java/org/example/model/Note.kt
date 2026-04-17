package org.example.model

import java.time.LocalDate

class Note : Record() {
    override fun createRecord(text : String, dateCreate: LocalDate) {
        super.createRecord(text, dateCreate, TypeRecord.Note, -1);
    }
}