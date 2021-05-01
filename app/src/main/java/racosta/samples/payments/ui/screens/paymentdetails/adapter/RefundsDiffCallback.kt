package racosta.samples.payments.ui.screens.paymentdetails.adapter

import androidx.recyclerview.widget.DiffUtil
import racosta.samples.core.model.Refund

class RefundsDiffCallback(
    private val newRefunds: List<Refund>,
    private val oldRefunds: List<Refund>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldRefunds.size
    }

    override fun getNewListSize(): Int {
        return newRefunds.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newRefunds[newItemPosition].id == oldRefunds[oldItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newRefunds[newItemPosition] == oldRefunds[oldItemPosition]
    }
}