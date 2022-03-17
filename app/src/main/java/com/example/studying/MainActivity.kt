package com.example.studying

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.studying.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ButtontoContacts.setOnClickListener {
            if (savedInstanceState == null) {
                val fragment = ContactsFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.placeholder, fragment, null)
                    .commit()
            }
            it.visibility=View.GONE
        }
    }
}
