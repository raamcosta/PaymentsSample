package racosta.samples.payments.ui.screens.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import racosta.samples.payments.commons.NavigationAction

abstract class BaseViewModel : ViewModel() {

    private val _navigateTo = MutableLiveData<NavigationAction?>(null)

    val navigateTo: LiveData<NavigationAction?> = _navigateTo

    fun onNavigationHandled() {
        _navigateTo.value = null
    }

    protected fun navigate(navAction: NavigationAction) {
        _navigateTo.value = navAction
    }

}