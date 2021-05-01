package racosta.samples.payments.ui.screens.paymentshistory.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import racosta.samples.commons.UiLogicFormatMapper
import racosta.samples.core.model.Payment
import racosta.samples.core.model.PaymentWithRefunds
import racosta.samples.payments.R
import racosta.samples.payments.databinding.PaymentsHistoryRowBinding

class PaymentsAdapter(private val onPaymentClickListener: OnPaymentClickListener?) :
    RecyclerView.Adapter<PaymentsAdapter.PaymentsViewHolder>() {

    private var payments: List<PaymentWithRefunds> = emptyList()

    fun setPayments(newPayments: List<PaymentWithRefunds>) {
        val diffResult = DiffUtil.calculateDiff(PaymentsDiffCallback(newPayments, payments))
        payments = newPayments
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentsViewHolder {
        val paymentsViewHolder = PaymentsViewHolder(
            PaymentsHistoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

        paymentsViewHolder.itemView.setOnClickListener {
            val itemPosition = paymentsViewHolder.adapterPosition
            val payment: PaymentWithRefunds = payments[itemPosition]
            onPaymentClickListener?.onPaymentClick(payment)
        }
        return paymentsViewHolder
    }

    override fun onBindViewHolder(holder: PaymentsViewHolder, position: Int) {
        holder.bind(payments[position])
    }

    override fun getItemCount(): Int {
        return payments.size
    }

    class PaymentsViewHolder(private val itemBinding: PaymentsHistoryRowBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(paymentWithRefunds: PaymentWithRefunds) {
            val payment: Payment = paymentWithRefunds.payment
            itemBinding.tvPaymentAmount.text = UiLogicFormatMapper.getAmountForUI(payment.amount) + itemBinding.root.context.getString(R.string.euro_sign)

            itemBinding.tvPaymentCardHolder.text = payment.cardHolder
            itemBinding.tvPaymentCardNumber.text = UiLogicFormatMapper.getCardNumberForUI(payment.cardNumber)
            itemBinding.tvPaymentDate.text = UiLogicFormatMapper.getDateForUI(payment.timestamp)

            if (paymentWithRefunds.refunds.isEmpty()) {
                itemBinding.tvRefundedTotal.visibility = View.GONE
            } else {
                itemBinding.tvRefundedTotal.visibility = View.VISIBLE
                itemBinding.tvRefundedTotal.text = "- " + UiLogicFormatMapper.getAmountForUI(paymentWithRefunds.refundedTotal) + itemBinding.root.context.getString(R.string.euro_sign)
            }
        }
    }

    interface OnPaymentClickListener {
        fun onPaymentClick(payment: PaymentWithRefunds)
    }
}