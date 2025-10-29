package com.docapost.signatureappmanager.ui.adapter

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.docapost.signatureappmanager.data.Signature
import com.docapost.signatureappmanager.databinding.ItemSignatureBinding

class SignatureAdapter(
    private val onDeleteClick: (Signature) -> Unit
) : ListAdapter<Signature, SignatureAdapter.SignatureViewHolder>(SignatureDiffCallback()) {

    inner class SignatureViewHolder(private val binding: ItemSignatureBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(signature: Signature) {
            binding.apply {
                tvDate.text = signature.getFormattedDate()
                tvName.text = signature.name

                // Décoder Base64 en Bitmap
                val decodedBytes = Base64.decode(signature.signatureData, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                ivSignature.setImageBitmap(bitmap)

                btnDelete.setOnClickListener {
                    onDeleteClick(signature)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignatureViewHolder {
        val binding = ItemSignatureBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SignatureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SignatureViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SignatureDiffCallback : DiffUtil.ItemCallback<Signature>() {
    override fun areItemsTheSame(oldItem: Signature, newItem: Signature) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Signature, newItem: Signature) =
        oldItem == newItem
}