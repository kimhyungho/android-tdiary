package com.hardy.yongbyung.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentSelectDateBinding
import com.hardy.yongbyung.ui.base.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class SelectDateDialog :
    BaseBottomSheetDialogFragment<FragmentSelectDateBinding>(
        R.layout.fragment_select_date
    ) {

    private val selectedDate: Long? by lazy { arguments?.getLong(DATE_KEY) }

    var listener: Listener? = null

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            with(calendarView) {
                minDate = Date().time
                date = selectedDate ?: Date().time
                setOnDateChangeListener { calendarView, year, month, day ->
                    val dateFormat = SimpleDateFormat("yyyy.M.d")
                    val stringDate = "$year.${month + 1}.$day"
                    val date = dateFormat.parse(stringDate)
                    date?.let { calendarView.date = date.time }
                }
            }

            confirmButton.setOnClickListener {
                val date = calendarView.date
                listener?.onConfirmButtonClick(date)
                dismiss()
            }
        }
    }

    interface Listener {
        fun onConfirmButtonClick(date: Long)
    }

    companion object {
        fun newInstance(
            selectedDate: Long?,
            onConfirmButtonClick: (Long) -> Unit
        ): SelectDateDialog {
            val dialog = SelectDateDialog().apply {
                arguments = bundleOf(
                    DATE_KEY to selectedDate
                )

                listener = object : Listener {
                    override fun onConfirmButtonClick(date: Long) {
                        onConfirmButtonClick.invoke(date)
                    }
                }
            }
            return dialog
        }

        private const val DATE_KEY = "DATE_KEY"

        const val TAG = "SelectDateDialog"
    }
}