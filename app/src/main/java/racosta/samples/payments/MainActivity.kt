package racosta.samples.payments

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import racosta.samples.payments.commons.ActionValues
import racosta.samples.payments.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    var outsideActionIntent: String? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        val intent = intent
        if (ActionValues.MAKE_PAYMENT == intent.action) {
            outsideActionIntent = ActionValues.MAKE_PAYMENT

//            navController.navigate(
//                R.id.NewPaymentFragment,
//                intent.extras,
//                NavOptions.Builder().setPopUpTo(R.id.PaymentsHistoryFragment, true).build()
//            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
        return (NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp())
    }

    fun setAppBarTitle(@StringRes newTitle: Int) {
        supportActionBar!!.setTitle(newTitle)
    }
}