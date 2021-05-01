package racosta.samples.payments.ui.screens.paymentdetails.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import racosta.samples.commons.UiLogicFormatMapper
import racosta.samples.core.model.Refund
import racosta.samples.payments.R
import racosta.samples.payments.databinding.RefundsHistoryRowBinding

class RefundsAdapter : RecyclerView.Adapter<RefundsAdapter.RefundsViewHolder?>() {

    private var refunds: List<Refund> = emptyList()

    fun setRefunds(newRefunds: List<Refund>) {
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(RefundsDiffCallback(newRefunds, refunds))
        refunds = newRefunds
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RefundsViewHolder {
        return RefundsViewHolder(RefundsHistoryRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RefundsViewHolder, position: Int) {
        holder.bind(refunds[position])
    }

    override fun getItemCount(): Int {
        return refunds.size
    }

    class RefundsViewHolder(private val itemBinding: RefundsHistoryRowBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(refund: Refund) {
            itemBinding.tvRefundDate.text = UiLogicFormatMapper.getDateForUI(refund.timestamp)
            itemBinding.tvRefundAmount.text = UiLogicFormatMapper.getAmountForUI(refund.amount) + itemBinding.root.context.getString(R.string.euro_sign)
        }
    }
}