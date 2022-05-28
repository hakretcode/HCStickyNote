package com.hakretcode.stickynote.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Note(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String?,
    val body: String
    ) {
    var selected: Boolean = false

    override fun equals(other: Any?): Boolean {
        return hashCode() == other.hashCode()
    }

    override fun hashCode(): Int =
        (id ?: 0) + (title?.hashCode() ?: 0) + body.hashCode()
}