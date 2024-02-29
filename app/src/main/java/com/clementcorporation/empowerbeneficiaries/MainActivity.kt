package com.clementcorporation.empowerbeneficiaries

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.clementcorporation.empowerbeneficiaries.ui.presentation.MainFragment
import com.clementcorporation.empowerbeneficiaries.util.Constants.DEFAULT_BACKSTACK_COUNT

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.apply {
            title = getString(R.string.main_activity_title)
            setIcon(R.drawable.empower_logo)
            setDisplayUseLogoEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        supportFragmentManager.beginTransaction()
            .addToBackStack(TAG)
            .add(R.id.container, MainFragment())
            .commit()
        onBackPressedDispatcher.addCallback(this,
            object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    //close app if on MainFragment, otherwise return to MainFragment onBackPressed
                    if (supportFragmentManager.backStackEntryCount == DEFAULT_BACKSTACK_COUNT) finish()
                    else supportFragmentManager.popBackStack()
                }
            }
        )
    }
}