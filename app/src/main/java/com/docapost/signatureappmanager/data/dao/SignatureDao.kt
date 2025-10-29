package com.docapost.signatureappmanager.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.docapost.signatureappmanager.data.Signature

@Dao
interface SignatureDao {
    @Insert
    suspend fun insert(signature: Signature) : Long

    //@Update
    //suspend fun update(signature: Signature)

    @Delete
    suspend fun delete(signature: Signature)

    @Query("SELECT * FROM signatures ORDER BY createdDate DESC")
    fun getAllSignatures() : LiveData<List<Signature>>

    @Query("SELECT * FROM signatures WHERE id = :id")
    suspend fun getSignatureById(id : Int) : Signature

    @Query("DELETE FROM signatures")
    suspend fun deleteAll()
}