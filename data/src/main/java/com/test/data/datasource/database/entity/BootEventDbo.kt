package com.test.data.datasource.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.data.datasource.database.entity.BootEventDbo.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME
)
data class BootEventDbo(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = 0,

    @ColumnInfo(name = DATE)
    val date: Long,
) {

    companion object {
        const val TABLE_NAME = "boot_events"
        const val ID = "id"
        const val DATE = "date"
    }
}
