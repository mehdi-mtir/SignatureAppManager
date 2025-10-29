package com.docapost.signatureappmanager.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.docapost.signatureappmanager.data.AppDatabase
import com.docapost.signatureappmanager.data.Signature
import com.docapost.signatureappmanager.data.SignatureRepository
import kotlinx.coroutines.launch

class SignatureViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : SignatureRepository

    val allSignatures : LiveData<List<Signature>>

    init {
        val signatureDao = AppDatabase.getDatabase(application).signatureDao()
        repository = SignatureRepository(signatureDao)
        allSignatures = repository.allSignatures
    }

    fun saveSignature(signature: Signature) = viewModelScope.launch{
        repository.insert(signature)
    }

    fun deleteSignature(signature: Signature) = viewModelScope.launch {
        repository.delete(signature)
    }
}