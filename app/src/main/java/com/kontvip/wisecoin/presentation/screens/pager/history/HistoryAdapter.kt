package com.kontvip.wisecoin.presentation.screens.pager.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kontvip.wisecoin.R
import com.kontvip.wisecoin.data.model.MCC
import com.kontvip.wisecoin.databinding.CategoryItemBinding
import com.kontvip.wisecoin.domain.core.PaymentUiState
import com.kontvip.wisecoin.domain.display.PaymentDisplay
import com.kontvip.wisecoin.domain.model.Payments
import com.kontvip.wisecoin.presentation.model.CategoryItem

class HistoryAdapter(
    private val payments: Payments
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(payments[position])
    }

    override fun getItemCount(): Int = payments.size

    inner class ViewHolder(
        private val binding: CategoryItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: PaymentUiState) {
            category.display(object : PaymentDisplay {
                override fun displayTime(time: Long) {
                    //TODO("Not yet implemented")
                }

                override fun displayDescription(description: String) {
                    binding.percentageTextView.text = description
                }

                override fun displayMcc(mcc: MCC) {
                    //TODO("Not yet implemented")
                }

                override fun displayAmount(amount: Int) {
                    binding.costTextView.text = amount.toString()
                }

            })
        }
    }

}