package com.example.studying

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.database.getStringOrNull
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studying.databinding.ContactsBinding

const val MY_PERMISSION_REQUEST_CONTACTS = 0

class ContactsFragment : Fragment() {
    lateinit var binding: ContactsBinding
    lateinit var contactModelList: MutableList<ContactModel>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ContactsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactModelList = mutableListOf()
        binding.buttonRequestContacts.setOnClickListener {
            val permission =
                ActivityCompat.checkSelfPermission(view.context, Manifest.permission.READ_CONTACTS)
            if (permission == PackageManager.PERMISSION_GRANTED) {
                getContacts()
            } else {
                getContactsPermission()
            }
        }
    }

    public fun getContacts() {
        val resolver = requireActivity().contentResolver
        val phones =
            resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        while (phones!!.moveToNext()) {
            val name =
                phones.getStringOrNull(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val contactModel = ContactModel()
            contactModel.setNames(name)
            contactModelList.add(contactModel)
            Log.d("name>>", name + "  ")
        }
        phones.close()
        val layoutManager = LinearLayoutManager(requireContext())
        val adapter = RecyclerAdapter(contactModelList)
        binding.recyclerViewContacts.layoutManager = layoutManager
        binding.recyclerViewContacts.adapter = adapter
    }

    fun getContactsPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Ahtung!")
            alertDialogBuilder.setMessage("Ну очень нужно")
            alertDialogBuilder.setPositiveButton(android.R.string.yes) { dialog, which ->
                requestPermissions(
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    MY_PERMISSION_REQUEST_CONTACTS
                )
            }
            alertDialogBuilder.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(
                    context,
                    "Как хочешь!", Toast.LENGTH_SHORT
                ).show()
            }
            alertDialogBuilder.show()

        } else {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                MY_PERMISSION_REQUEST_CONTACTS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_PERMISSION_REQUEST_CONTACTS) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getContacts()
            }
        }
    }
}