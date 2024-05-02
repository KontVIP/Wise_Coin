package com.kontvip.wisecoin.presentation.screens.pager.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kontvip.wisecoin.R
import com.kontvip.wisecoin.databinding.CategoryItemBinding
import com.kontvip.wisecoin.domain.core.PaymentUiState
import com.kontvip.wisecoin.domain.display.PaymentDisplay
import com.kontvip.wisecoin.presentation.model.PaymentUi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryAdapter(
    private val payments: List<PaymentUi>
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
                    val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
                    val date = Date(time)
                    binding.dateTextView.text = sdf.format(date)
                }

                override fun displayDescription(description: String) {
                    binding.percentageTextView.text = description
                }

                override fun displayCategory(category: String) {
                    binding.categoryNameTextView.text = category
                }

                override fun displayAmount(amount: Int) {
                    binding.costTextView.text = (amount.toFloat() / 100).toString()
                }

                override fun displayImage(image: String) {
                    if (image.isNotBlank()) {
                        Glide.with(binding.root).load(image).into(binding.categoryIconImageView)
                    } else {
                        binding.categoryIconImageView.setImageResource(R.drawable.question_icon)
                    }
                }
            })
        }
    }

}