package racosta.samples.payments.ui.screens.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import racosta.samples.payments.databinding.PaymentsHistoryRowBinding

class PaymentsAdapter(private val onPaymentClickListener: OnPaymentClickListener?) :
    RecyclerView.Adapter<PaymentsAdapter.PaymentsViewHolder>() {

//    private var payments: List<PaymentWithRefunds> = emptyList<PaymentWithRefunds>()
//
//    fun setPayments(newPayments: List<PaymentWithRefunds>) {
//        val diffResult = DiffUtil.calculateDiff(PaymentsDiffCallback(newPayments, payments))
//        payments = newPayments
//        diffResult.dispatchUpdatesTo(this)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentsViewHolder {
        val paymentsViewHolder = PaymentsViewHolder(
            PaymentsHistoryRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        paymentsViewHolder.itemView.setOnClickListener { view: View? ->
            val itemPosition = paymentsViewHolder.adapterPosition
//            val payment: PaymentWithRefunds = payments[itemPosition]
//            onPaymentClickListener?.onPaymentClick(payment)
        }
        return paymentsViewHolder
    }

    override fun onBindViewHolder(holder: PaymentsViewHolder, position: Int) {
//        holder.bind(payments[position])
    }

    override fun getItemCount(): Int {
//        return payments.size
        return 0
    }

    class PaymentsViewHolder(itemBinding: PaymentsHistoryRowBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private val itemBinding = itemBinding

//        @SuppressLint("SetTextI18n")
//        fun bind(paymentWithRefunds: PaymentWithRefunds) {
//            val payment: Payment = paymentWithRefunds.getPayment()
//            itemBinding.tvPaymentAmount.setText(UiLogicFormatMapper.getAmountForUI(payment.getAmount()) + itemBinding.root.context.getString(R.string.euro_sign))
//
//            itemBinding.tvPaymentCardHolder.setText(payment.getCardHolder())
//            itemBinding.tvPaymentCardNumber.setText(UiLogicFormatMapper.getCardNumberForUI(payment.getCardNumber()))
//            itemBinding.tvPaymentDate.setText(UiLogicFormatMapper.getDateForUI(payment.getTimestamp()))
//
//            if (paymentWithRefunds.getRefunds().isEmpty()) {
//                itemBinding.tvRefundedTotal.visibility = View.GONE
//            } else {
//                itemBinding.tvRefundedTotal.visibility = View.VISIBLE
//                itemBinding.tvRefundedTotal.text = "- " + UiLogicFormatMapper.getAmountForUI(paymentWithRefunds.getRefundedTotal()) + itemBinding.root.context.getString(R.string.euro_sign)
//            }
//        }
    }

    interface OnPaymentClickListener {
//        fun onPaymentClick(payment: PaymentWithRefunds)
    }
}