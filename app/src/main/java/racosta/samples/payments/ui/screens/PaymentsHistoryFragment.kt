package racosta.samples.payments.ui.screens

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import racosta.samples.payments.commons.viewBinding
import racosta.samples.payments.ui.screens.adapter.PaymentsAdapter
import racosta.samples.payments.ui.viewmodels.PaymentsHistoryViewModel
import racosta.samples.payments.R
import racosta.samples.payments.databinding.PaymentsHistoryFragmentBinding
import racosta.samples.payments.ui.screens.base.BaseFragment

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PaymentsHistoryFragment : BaseFragment(R.layout.payments_history_fragment), PaymentsAdapter.OnPaymentClickListener {

    private val binding by viewBinding(PaymentsHistoryFragmentBinding::bind)
    override val viewModel by viewModel<PaymentsHistoryViewModel>()

    private val adapter = PaymentsAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        binding.rvPayments.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPayments.adapter = adapter

        setUpUserEventListeners()
        setUpUiStateObservers()
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_filter) {
            viewModel.onFilterClick()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpUiStateObservers() {
//        viewModel.getPayments().observe(viewLifecycleOwner) { payments ->
//            if (payments.isEmpty()) {
//                binding.tvScreenInfo.visibility = View.VISIBLE
//                binding.rvPayments.visibility = View.INVISIBLE
//            } else {
//                binding.tvScreenInfo.visibility = View.INVISIBLE
//                binding.rvPayments.visibility = View.VISIBLE
//            }
//            adapter.setPayments(payments)
//        }
//
//        viewModel.isFiltering().observe(viewLifecycleOwner) { isFiltering ->
//            if (isFiltering) {
//                myActivity.setAppBarTitle(R.string.payments_screen_refunded)
//            } else {
//                myActivity.setAppBarTitle(R.string.payments_screen_label)
//            }
//        }
    }

    private fun setUpUserEventListeners() {
        binding.fabNewPayment.setOnClickListener { viewModel.onNewPaymentClick() }
    }

//    override fun onPaymentClick(payment: PaymentWithRefunds) {
//        viewModel.onPaymentClick(payment)
//    }

}