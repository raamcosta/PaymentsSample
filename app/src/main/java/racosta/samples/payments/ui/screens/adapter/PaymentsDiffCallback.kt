package racosta.samples.payments.ui.screens.adapter

import racosta.samples.core.model.PaymentWithRefunds
import androidx.recyclerview.widget.DiffUtil

class PaymentsDiffCallback(
    private val newPayments: List<PaymentWithRefunds>,
    private val oldPayments: List<PaymentWithRefunds>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldPayments.size

    override fun getNewListSize() = newPayments.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newPayments[newItemPosition].payment.id == oldPayments[oldItemPosition].payment.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newPayments[newItemPosition] == oldPayments[oldItemPosition]
    }
}