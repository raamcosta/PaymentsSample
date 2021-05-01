package racosta.samples.payments.ui.screens.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import racosta.samples.payments.MainActivity

abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

    protected open val viewModel: BaseViewModel? = null

    protected val myActivity get() = requireActivity() as MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel?.let {
            it.navigateTo.observe(viewLifecycleOwner) { navAction ->
                if (navAction == null) return@observe
                it.onNavigationHandled()

                val outsideActionIntent: String? = myActivity.outsideActionIntent
                if (outsideActionIntent != null && outsideActionIntent == navAction.finishedAction) {
                    // We have finished the only task we were launched to do, set result and finish
                    myActivity.setResult(navAction.actionResult)
                    myActivity.finish()
                    return@observe
                }

                NavHostFragment.findNavController(this).navigate(navAction.navDirections)
            }
        }
    }

}