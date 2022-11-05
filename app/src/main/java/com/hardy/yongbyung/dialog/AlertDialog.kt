package com.hardy.yongbyung.dialog

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentAlertDialogBinding
import com.hardy.yongbyung.ui.base.BaseDialogFragment

class AlertDialog : BaseDialogFragment<FragmentAlertDialogBinding>(
    R.layout.fragment_alert_dialog
) {
    private val contentText: String? by lazy { arguments?.getString(CONTENT_TEXT_KEY) }
    private val negativeText: String? by lazy { arguments?.getString(NEGATIVE_TEXT_KEY) }
    private val positiveText: String? by lazy { arguments?.getString(POSITIVE_TEXT_KEY) }

    var listener: Listener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            content.text = contentText

            with(negativeButton) {
                if (negativeText != null) text = negativeText!!
                setOnClickListener {
                    listener?.onNegativeButtonClick()
                    dismiss()
                }
            }

            with(positiveButton) {
                if (positiveText != null) {
                    text = positiveText!!
                    visibility = View.VISIBLE
                }
                setOnClickListener {
                    listener?.onPositiveButtonClick()
                    dismiss()
                }
            }
        }
    }

    interface Listener {
        fun onNegativeButtonClick()
        fun onPositiveButtonClick()
    }

    companion object {
        fun newInstance(
            contentText: String,
            negativeText: String,
            positiveText: String? = null,
            onNegativeButtonClick: (() -> Unit)? = null,
            onPositiveButtonClick: (() -> Unit)? = null,
        ): AlertDialog {
            val fragment = AlertDialog()
            return fragment.apply {
                arguments = bundleOf(
                    CONTENT_TEXT_KEY to contentText,
                    NEGATIVE_TEXT_KEY to negativeText,
                    POSITIVE_TEXT_KEY to positiveText
                )

                listener = object : Listener {
                    override fun onNegativeButtonClick() {
                        onNegativeButtonClick?.invoke()
                    }

                    override fun onPositiveButtonClick() {
                        onPositiveButtonClick?.invoke()
                    }
                }
            }
        }

        private const val CONTENT_TEXT_KEY = "CONTENT_KEY"
        private const val NEGATIVE_TEXT_KEY = "NEGATIVE_TEXT_KEY"
        private const val POSITIVE_TEXT_KEY = "POSITIVE_TEXT_KEY"

        const val TAG = "MessageRoomMenuDialog"
    }
}