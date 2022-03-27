package com.example.studying

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.studying.databinding.AuthBinding
import com.jakewharton.rxbinding4.widget.afterTextChangeEvents
import io.reactivex.rxjava3.core.Observable

import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Single
import org.reactivestreams.Subscriber
import java.util.*

class AuthFragment : Fragment(R.layout.auth) {
    lateinit var binding: AuthBinding
    lateinit var editTextLogin: EditText
    lateinit var editTextPassword: EditText
    lateinit var button: Button
    companion object {
        const val LOGIN = "LOGIN"
        const val PASSWORD = "PASSWORD"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AuthBinding.inflate(inflater, container, false)
        val root: View = binding.root
        editTextLogin = binding.textEditLogin
        editTextPassword = binding.textEditPassword
        button = binding.buttonAuth
        if(savedInstanceState!=null){
        editTextLogin.setText(savedInstanceState.getString(LOGIN))
        editTextPassword.setText(savedInstanceState.getString(PASSWORD))}
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            val searchFragment: Fragment = SearchFragment()
            val fragmentTransaction = requireActivity()
                .supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, searchFragment)
            fragmentTransaction.commit()
        }

        startStream()

    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LOGIN, editTextLogin.text.toString())
        outState.putString(PASSWORD,editTextPassword.text.toString())
        super.onSaveInstanceState(outState)
    }

    private fun startStream() {
        val isLoginValid =
            editTextLogin.afterTextChangeEvents().map { textview -> textview.view.text.length > 5 }
                .distinctUntilChanged()
        isLoginValid.map { a -> if (a) Color.BLACK else Color.RED }
            .subscribe { c -> editTextLogin.setTextColor(c) }
        val isPasswordValid = editTextPassword.afterTextChangeEvents()
            .map { textview -> textview.view.text.length > 5 }.distinctUntilChanged()
        isPasswordValid.map { a -> if (a) Color.BLACK else Color.RED }
            .subscribe { c -> editTextPassword.setTextColor(c) }
        val isSigneInPossible =
            Observable.combineLatest(isLoginValid, isPasswordValid) { l, p -> l && p }
                .distinctUntilChanged()
        isSigneInPossible.subscribe { b -> button.isEnabled = b }
        isSigneInPossible.subscribe { b -> button.isClickable = b }

    }
}