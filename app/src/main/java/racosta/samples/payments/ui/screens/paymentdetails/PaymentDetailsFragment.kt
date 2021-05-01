package racosta.samples.payments.ui.screens.paymentdetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import racosta.samples.commons.UiLogicFormatMapper
import racosta.samples.core.model.Payment
import racosta.samples.core.model.PaymentWithRefunds
import racosta.samples.core.model.Refund
import racosta.samples.payments.R
import racosta.samples.payments.commons.viewBinding
import racosta.samples.payments.databinding.PaymentDetailsFragmentBinding
import racosta.samples.payments.ui.screens.base.BaseFragment
import racosta.samples.payments.ui.screens.paymentdetails.adapter.RefundsAdapter
import java.math.BigDecimal

class PaymentDetailsFragment : BaseFragment(R.layout.payment_details_fragment) {

    private val binding: PaymentDetailsFragmentBinding by viewBinding(PaymentDetailsFragmentBinding::bind)

    override val viewModel by viewModel<PaymentDetailsViewModel> { parametersOf(PaymentDetailsFragmentArgs.fromBundle(requireArguments()).paymentId) }

    private val adapter = RefundsAdapter()

    private var paymentDisplayed: Payment? = null
    private var refundsDisplayed: List<Refund>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvRefunds.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRefunds.adapter = adapter

        setUpUserEventListeners()
        setUpUiStateObservers()
    }

    @SuppressLint("SetTextI18n")
    private fun setUpUiStateObservers() {
        viewModel.paymentWithRefunds.observe(viewLifecycleOwner, { paymentWithRefunds: PaymentWithRefunds? ->
            if (paymentWithRefunds == null) return@observe

            val payment: Payment = paymentWithRefunds.payment

            if (payment != paymentDisplayed) {
                paymentDisplayed = payment
                binding.tvAmount.text = UiLogicFormatMapper.getAmountForUI(payment.amount) + getString(R.string.euro_sign)
                binding.tvCardHolder.text = payment.cardHolder
                binding.tvCardNumber.text = UiLogicFormatMapper.getCardNumberForUI(payment.cardNumber)
                binding.tvDate.text = UiLogicFormatMapper.getDateForUI(payment.timestamp)
            }

            val refunds: List<Refund> = paymentWithRefunds.refunds

            if ((refundsDisplayed == null || refundsDisplayed!!.isEmpty()) != refunds.isEmpty()) {
                val refundViewsVisibility = if (refunds.isEmpty()) View.GONE else View.VISIBLE
                binding.tvRefundedTotal.visibility = refundViewsVisibility
                binding.tvTotalRefundsLabel.visibility = refundViewsVisibility
                binding.tvTotalNotRefundedLabel.visibility = refundViewsVisibility
                binding.tvNotRefundedTotal.visibility = refundViewsVisibility
                binding.tvRefundsHistoryLabel.visibility = refundViewsVisibility
                binding.rvRefunds.visibility = refundViewsVisibility
            }

            if (refunds != refundsDisplayed) {
                adapter.setRefunds(refunds)
                refundsDisplayed = refunds
            }
        })

        viewModel.refundedTotal.observe(viewLifecycleOwner, { refundedTotal: BigDecimal ->
            binding.tvRefundedTotal.text = UiLogicFormatMapper.getAmountForUI(refundedTotal) + getString(R.string.euro_sign)
        })

        viewModel.totalNotRefunded.observe(viewLifecycleOwner, { totalNotRefunded: BigDecimal? ->
            if (totalNotRefunded == null) return@observe
            if (totalNotRefunded.compareTo(BigDecimal.ZERO) == 0) {
                binding.tvAmountToRefundLabel.visibility = View.GONE
                binding.etAmountToRefund.visibility = View.GONE
                binding.tvEuroSign.visibility = View.GONE
                binding.btnSubmitRefund.visibility = View.GONE
            }
            binding.tvNotRefundedTotal.text = UiLogicFormatMapper.getAmountForUI(totalNotRefunded) + getString(R.string.euro_sign)
        })

        viewModel.refundAmountInvalidState.observe(viewLifecycleOwner, { refundAmountInvalidState: PaymentDetailsViewModel.RefundAmountInvalidState ->
            when (refundAmountInvalidState) {
                PaymentDetailsViewModel.RefundAmountInvalidState.NONE -> binding.tvAmountNotValid.visibility =
                    View.INVISIBLE

                PaymentDetailsViewModel.RefundAmountInvalidState.INVALID -> {
                    binding.tvAmountNotValid.visibility = View.VISIBLE
                    binding.tvAmountNotValid.setText(R.string.amount_not_valid)
                }

                PaymentDetailsViewModel.RefundAmountInvalidState.INVALID_MAX_REACHED -> {
                    binding.tvAmountNotValid.visibility = View.VISIBLE
                    binding.tvAmountNotValid.setText(R.string.amount_exceeds_max_valid)
                }

            }
        })

        viewModel.refundEnabled.observe(viewLifecycleOwner, { isEnabled: Boolean ->
            binding.btnSubmitRefund.isEnabled = isEnabled
        })
    }

    private fun setUpUserEventListeners() {
        binding.etAmountToRefund.doAfterTextChanged {
            viewModel.onAmountToRefundChanged(it.toString())
        }

        binding.btnSubmitRefund.setOnClickListener {
            viewModel.onRefundSubmitClicked()
            binding.etAmountToRefund.setText("")
        }
    }
}