package org.example.model

import java.util.Objects

sealed class TypeRecord {
    object No: TypeRecord()
    object Note: TypeRecord()
    object Member: TypeRecord()
}