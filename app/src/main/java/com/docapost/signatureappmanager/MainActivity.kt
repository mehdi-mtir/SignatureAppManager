package com.docapost.signatureappmanager

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.docapost.signatureappmanager.data.Signature
import com.docapost.signatureappmanager.databinding.ActivityMainBinding
import com.docapost.signatureappmanager.ui.adapter.SignatureAdapter
import com.docapost.signatureappmanager.ui.viewmodel.SignatureViewModel
import java.io.ByteArrayOutputStream
import kotlin.getValue

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: SignatureViewModel by viewModels()
    private lateinit var adapter: SignatureAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
        observeSignatures()
    }

    private fun setupRecyclerView() {
        adapter = SignatureAdapter { signature ->
            viewModel.deleteSignature(signature)
            Toast.makeText(this, "Signature supprimée", Toast.LENGTH_SHORT).show()
        }

        binding.rvSignatures.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setupListeners() {
        binding.btnClear.setOnClickListener {
            binding.signaturePad.clearSignature()
            Toast.makeText(this, "Signature effacée", Toast.LENGTH_SHORT).show()
        }

        binding.btnSave.setOnClickListener {
            saveSignature()
        }
    }

    private fun saveSignature() {
        if (binding.signaturePad.isEmpty()) {
            Toast.makeText(this, "Veuillez dessiner une signature", Toast.LENGTH_SHORT).show()
            return
        }

        val bitmap = binding.signaturePad.getSignatureBitmap()
        val base64String = bitmapToBase64(bitmap)

        val signature = Signature(
            signatureData = base64String,
            createdDate = System.currentTimeMillis()
        )

        viewModel.saveSignature(signature)
        binding.signaturePad.clearSignature()
        Toast.makeText(this, "Signature enregistrée", Toast.LENGTH_SHORT).show()
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun observeSignatures() {
        viewModel.allSignatures.observe(this) { signatures ->
            adapter.submitList(signatures)
        }
    }
}