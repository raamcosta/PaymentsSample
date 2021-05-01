package racosta.samples.payments.ui.screens

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import org.koin.androidx.viewmodel.ext.android.viewModel
import racosta.samples.payments.R
import racosta.samples.payments.commons.ActionValues
import racosta.samples.payments.commons.viewBinding
import racosta.samples.payments.databinding.NewPaymentFragmentBinding
import racosta.samples.payments.ui.screens.base.BaseFragment
import racosta.samples.payments.ui.viewmodels.NewPaymentViewModel

class NewPaymentFragment : BaseFragment(R.layout.new_payment_fragment) {

    private val binding by viewBinding(NewPaymentFragmentBinding::bind)

    override val viewModel by viewModel<NewPaymentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUserEventListeners()
        setUpUiStateObservers()
        initFormFromArguments()
    }

    private fun initFormFromArguments() {
        val arguments: Bundle = arguments ?: return

        val holder: String = arguments.getString(ActionValues.MAKE_PAYMENT_EXTRA_HOLDER, "")
        binding.etCardHolder.setText(holder)

        val number: String = arguments.getString(ActionValues.MAKE_PAYMENT_EXTRA_NUMBER, "")
        binding.etCardNumber.setText(number.replace(" ", ""))

        val cvv: String = arguments.getString(ActionValues.MAKE_PAYMENT_EXTRA_CVV, "")
        binding.etCvv.setText(cvv)

        val month: String = arguments.getString(ActionValues.MAKE_PAYMENT_EXTRA_MONTH, "")
        binding.etExpDateMonth.setText(month)

        val year: String = arguments.getString(ActionValues.MAKE_PAYMENT_EXTRA_YEAR, "")
        binding.etExpDateYear.setText(year)

        val amount: String = arguments.getString(ActionValues.MAKE_PAYMENT_EXTRA_AMOUNT, "")
        binding.etAmount.setText(amount)

    }

    private fun setUpUiStateObservers() {
        viewModel.showExpDateInvalid().observe(viewLifecycleOwner) { shouldShow ->
            if (shouldShow) {
                binding.tvExpDateNotValid.visibility = View.VISIBLE
            } else {
                binding.tvExpDateNotValid.visibility = View.INVISIBLE
            }
        }

        viewModel.showCardNumberInvalid().observe(viewLifecycleOwner) { shouldShow ->
            if (shouldShow) {
                binding.tvCardNumberNotValid.visibility = View.VISIBLE
            } else {
                binding.tvCardNumberNotValid.visibility = View.INVISIBLE
            }
        }

        viewModel.showAmountInvalid().observe(viewLifecycleOwner) { shouldShow ->
            if (shouldShow) {
                binding.tvAmountNotValid.visibility = View.VISIBLE
            } else {
                binding.tvAmountNotValid.visibility = View.INVISIBLE
            }
        }

        viewModel.showCvvInvalid().observe(viewLifecycleOwner) { shouldShow ->
            if (shouldShow) {
                binding.tvCvvNotValid.visibility = View.VISIBLE
            } else {
                binding.tvCvvNotValid.visibility = View.INVISIBLE
            }
        }

        viewModel.confirmPaymentEnabled().observe(viewLifecycleOwner) { isEnabled ->
            binding.btnConfirmPayment.isEnabled = isEnabled
        }
    }

    private fun setUpUserEventListeners() {
        binding.etExpDateMonth.doAfterTextChanged {
            viewModel.onDateChanged(
                binding.etExpDateMonth.text.toString(),
                binding.etExpDateYear.text.toString()
            )
        }

        binding.etExpDateYear.doAfterTextChanged {
            viewModel.onDateChanged(
                binding.etExpDateMonth.text.toString(),
                binding.etExpDateYear.text.toString()
            )
        }

        binding.etCardNumber.doAfterTextChanged {
            viewModel.onCardNumberChanged(it.toString())
        }

        binding.etCvv.doAfterTextChanged {
            viewModel.onCvvChanged(it.toString())
        }

        binding.etAmount.doAfterTextChanged {
            viewModel.onAmountChanged(it.toString())
        }

        binding.etCardHolder.doAfterTextChanged {
            viewModel.onCardHolderChanged(it.toString())
        }

        binding.btnConfirmPayment.setOnClickListener { viewModel.onConfirmClicked() }
    }
}