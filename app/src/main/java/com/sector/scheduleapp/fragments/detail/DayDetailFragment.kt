package com.sector.scheduleapp.fragments.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.sector.scheduleapp.databinding.FragmentDayDetailBinding
import com.sector.scheduleapp.objects.Day

class DayDetailFragment : Fragment() {
    private var _binding: FragmentDayDetailBinding? = null
    private val binding get() = _binding!!

    private var ref: DatabaseReference? = null
    private lateinit var adapter: DayDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDayDetailBinding.inflate(inflater, container, false)

        setupRecyclerView()
        initDatabase()
        readFromDatabase()

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = DayDetailAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun initDatabase() {
        FirebaseApp.initializeApp(requireContext())

        val institute = "ИМИТ"
        val group = "МОСб-202"
        val typeOfWeek = "Числитель"
        val dayOfWeek = "Вторник"

        ref = FirebaseDatabase.getInstance()
            .getReference(institute)
            .child(group)
            .child(typeOfWeek)
            .child(dayOfWeek)
    }

    private fun readFromDatabase() {
        ref?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    val list = ArrayList<Day>()

                    for (daySnapshot in snapshot.children) {
                        val day = daySnapshot.getValue(Day::class.java)

                        list.add(day!!)
                    }

                    adapter.submitList(list)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}