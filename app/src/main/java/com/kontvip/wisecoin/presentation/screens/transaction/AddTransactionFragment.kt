package com.kontvip.wisecoin.presentation.screens.transaction

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kontvip.wisecoin.R
import com.kontvip.wisecoin.databinding.FragmentAddTransactionBinding
import com.kontvip.wisecoin.presentation.core.BaseFragment
import com.kontvip.wisecoin.presentation.gone
import com.kontvip.wisecoin.presentation.onClick
import com.kontvip.wisecoin.presentation.view.DateTimePickerDialog
import com.kontvip.wisecoin.presentation.visible
import dagger.hilt.android.AndroidEntryPoint
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class AddTransactionFragment : BaseFragment<FragmentAddTransactionBinding>(),
    DateTimePickerDialog.DateTimePickListener, CategoryHintAdapter.OnItemSelected {

    private val viewModel by viewModels<AddTransactionViewModel>()
    private var isExpenses = true

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let { uri ->
                    transactionImageUrl = uri.toString()
                    Glide.with(this).load(uri).into(binding.transactionImageView)
                }
            }
        }

    private var transactionImageUrl = ""
    private var transactionTime = System.currentTimeMillis()
    private var selectedCategory = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CategoryHintAdapter(emptyList(), this)
        binding.categoryHintsRecyclerView.adapter = adapter
        binding.categoryHintsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.categoryHintsRecyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )

        viewModel.fetchAllCategories {
            adapter.addNewItems(it)
        }

        onDateTimePicked(transactionTime)

        binding.transactionImageView.onClick {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            galleryLauncher.launch(intent)
        }

        binding.setTimeButton.onClick {
            DateTimePickerDialog.Default().showDateTimePicker(requireContext(), this)
        }

        binding.categoryEditText.addTextChangedListener {
            selectedCategory = it?.toString() ?: ""
            adapter.filter(selectedCategory)
            binding.categoryHintsRecyclerView.visible()
        }

        KeyboardVisibilityEvent.setEventListener(requireActivity(), viewLifecycleOwner) {
            if (!it) {
                binding.categoryHintsRecyclerView.gone()
            }
        }

        val filter = InputFilter { source, _, _, _, dstart, _ ->
            if (source != null && !source.toString().all { it in "1234567890." }) {
                return@InputFilter ""
            }
            if (dstart == 0 && source.toString() == ".") {
                return@InputFilter ""
            }
            null
        }
        val filters = arrayOf(filter)
        binding.amountEditText.filters = filters

        binding.createButton.onClick {
            if (selectedCategory.isBlank()) {
                viewModel.showError(R.string.error_enter_category)
                return@onClick
            }

            var amount = binding.amountEditText.text?.toString()?.toDoubleOrNull()
            if (amount == null) {
                viewModel.showError(R.string.error_enter_valid_amount)
                return@onClick
            }
            if (isExpenses) {
                amount = -amount
            }
            viewModel.saveTransaction(
                transactionTime,
                binding.descriptionTextView.text?.toString() ?: "",
                selectedCategory,
                amount * 100,
                transactionImageUrl
            )
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.plusButton.onClick {
            binding.plusButton.setBackgroundResource(R.drawable.transaction_type_selected_bg)
            binding.minusButton.setBackgroundResource(R.drawable.black_border)
            isExpenses = false
        }
        binding.minusButton.onClick {
            binding.plusButton.setBackgroundResource(R.drawable.black_border)
            binding.minusButton.setBackgroundResource(R.drawable.transaction_type_selected_bg)
            isExpenses = true
        }
    }

    override fun onDateTimePicked(dateTimeMillis: Long) {
        transactionTime = dateTimeMillis
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
        val date = Date(dateTimeMillis)
        binding.dateTextView.text = sdf.format(date)
    }

    override fun onSelected(category: String) {
        binding.categoryEditText.setText(category)
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocus = requireActivity().currentFocus
        if (currentFocus != null) {
            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }
}