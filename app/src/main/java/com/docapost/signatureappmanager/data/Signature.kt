package com.docapost.signatureappmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "signatures")
class Signature(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val signatureData: String, //Base64 de l'image
    val createdDate : Long = System.currentTimeMillis(),
    val name : String = "Signature ${Date().time}"
) {
    fun getFormattedDate() : String{
        val date = Date(createdDate)
        return java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault()).format(date)
    }

}