package com.beni.core.data.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.beni.core.util.ConstantVariable.ENTITY_NAME

@Entity(tableName = ENTITY_NAME)
data class PatientEntitiy(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "name")
    val name: String,
//
//    @ColumnInfo(name = "age")
//    val age: Int,

    @ColumnInfo(name = "systole")
    val systole: Int,

    @ColumnInfo(name = "diastole")
    val diastole: Int,
)
