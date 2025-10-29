package com.docapost.signatureappmanager.data

import androidx.lifecycle.LiveData
import com.docapost.signatureappmanager.data.dao.SignatureDao

class SignatureRepository(private  val  signatureDao: SignatureDao) {
    val allSignatures : LiveData<List<Signature>> = signatureDao.getAllSignatures()

    suspend fun insert(signature: Signature) : Long{
        return  signatureDao.insert(signature)
    }

    suspend fun delete(signature: Signature){
        signatureDao.delete(signature)
    }

    suspend fun getSignatureById(id : Int) : Signature?{
        return signatureDao.getSignatureById(id)
    }
}