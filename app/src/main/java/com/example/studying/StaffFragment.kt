package com.example.studying

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.studying.databinding.FragmentStaffBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class StaffFragment : Fragment() {
    companion object {
        const val SCROLL = "SCROLL"
    }
    lateinit var binding: FragmentStaffBinding
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var db: DataBase
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStaffBinding.inflate(layoutInflater,container,false)
        recyclerView = binding.recyclerView
        progressBar = binding.progressBar
        if(savedInstanceState!=null){
//            recyclerView.layoutManager?.onRestoreInstanceState(savedInstanceState.getParcelable(
//                SCROLL))
            println("OUTPUT"+savedInstanceState.getInt(SCROLL))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Room.databaseBuilder(
            requireContext(),
            DataBase::class.java, "staff.db"
        ).build()
        if (savedInstanceState==null) {
            val listStaff = mutableListOf<StaffEntity>(
                StaffEntity(1, "John"),
                StaffEntity(2, "Karl"),
                StaffEntity(3, "Luk"),
                StaffEntity(4, "Sir"),
                StaffEntity(5, "Clark"),
                StaffEntity(6, "Edmond"),
                StaffEntity(7, "Joe"),
                StaffEntity(8, "Peter"),
                StaffEntity(9, "Karl"),
                StaffEntity(10, "Luk"),
                StaffEntity(11, "Sir"),
                StaffEntity(12, "Clark"),
                StaffEntity(13, "Edmond"),
                StaffEntity(14, "Joe"),
                StaffEntity(15, "Peter")
            )

            db.positionStaffDao().insertAllStaffRx(listStaff)
                .andThen { println(Thread.currentThread().name + " Thread for Insert") }
                .subscribeOn(Schedulers.io())
                .subscribe()
        }
        db.positionStaffDao().getAllStaffRx()
            .doAfterSuccess { println(Thread.currentThread().name + " Thread for getAll") }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterSuccess { println(Thread.currentThread().name + " Thread for Ui") }
            .subscribe { emmiter ->
                progressBar.visibility = View.GONE
                setList(emmiter)
            }

    }
    private fun setList(list: List<StaffEntity>) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = RecyclerAdapterStaff(list)
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SCROLL,5)
        //outState.putParcelable(SCROLL, recyclerView.layoutManager?.onSaveInstanceState())
        super.onSaveInstanceState(outState)
    }
}