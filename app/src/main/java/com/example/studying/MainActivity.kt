package com.example.studying


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var authFragment=AuthFragment()
    private val AUTH_FRAGMENT_TAG = "myfragmenttag"
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        if (savedInstanceState != null ) {
            authFragment =
                supportFragmentManager.findFragmentByTag(AUTH_FRAGMENT_TAG) as AuthFragment
        }
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, authFragment, AUTH_FRAGMENT_TAG)
            .commit()


    }

}