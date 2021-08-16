package com.sungshin.recyclear.vinyl

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sungshin.recyclear.databinding.FragmentVinylListBinding
import com.sungshin.recyclear.utils.FirebaseUtil
import com.sungshin.recyclear.vinyl.vinyllist.VinylListAdapter
import com.sungshin.recyclear.vinyl.vinyllist.VinylListInfo

class VinylListFragment : Fragment() {

    private var _binding: FragmentVinylListBinding? = null
    private val binding get() =_binding ?: error("View 를 참조하기 위해 binding 이 초기화 되지 않았습니다.")
    private val vinylListAdapter: VinylListAdapter by lazy{VinylListAdapter()}

    val firebaseDB = FirebaseUtil()
    val database = firebaseDB.database

    var datas= mutableListOf<VinylListInfo>()

    var hasVinyl: Boolean = false
    var hasVinyl2: Boolean = false
    var hasVinyl3: Boolean = false
    var hasVinyl4: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVinylListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerviewVinylImages.adapter = vinylListAdapter

        loadDatas()
    }

    private fun loadDatas(){
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (userSnapshot in dataSnapshot.children) {
                    if (userSnapshot.child("unchecked").hasChild("vinyl")) {
                        for (imageSnapshot in userSnapshot.child("unchecked").child("vinyl").children) {
                            if (imageSnapshot.hasChildren()) {
                                val date = imageSnapshot.child("date").getValue(String::class.java)
                                val imageFile = imageSnapshot.child("image").getValue(String::class.java)
                                val pred = imageSnapshot.child("pred").getValue(String::class.java)

                                if (date != null && imageFile != null && pred != null) {
                                    hasVinyl = true

                                    datas.apply {
                                        add(
                                            VinylListInfo(
                                                detect_image = imageFile,
                                                detect_percent = pred,
                                                detect_date = date
                                            )
                                        )
                                    }

                                    Log.d("FIREBASE", "date: $date / img: $imageFile / pred: $pred")
                                }
                                else{
                                    hasVinyl = false
                                }
                            }

                            else {
                                Log.d("FIREBASE", "not hasChildren")
                            }
                        }
                    }

                    if (userSnapshot.child("unchecked").hasChild("stick vinyl")) {
                        for (imageSnapshot in userSnapshot.child("unchecked").child("stick vinyl").children) {
                            if (imageSnapshot.hasChildren()) {
                                val date = imageSnapshot.child("date").getValue(String::class.java)
                                val imageFile = imageSnapshot.child("image").getValue(String::class.java)
                                val pred = imageSnapshot.child("pred").getValue(String::class.java)

                                if (date != null && imageFile != null && pred != null) {
                                    hasVinyl2 = true

                                    datas.apply {
                                        add(
                                            VinylListInfo(
                                                detect_image = imageFile,
                                                detect_percent = pred,
                                                detect_date = date
                                            )
                                        )
                                    }

                                    Log.d("FIREBASE", "date: $date / img: $imageFile / pred: $pred")
                                }
                                else{
                                    hasVinyl2 = false
                                }
                            }

                            else {
                                Log.d("FIREBASE", "not hasChildren")
                            }
                        }
                    }

                    if (userSnapshot.child("checked").hasChild("vinyl")) {
                        for (imageSnapshot in userSnapshot.child("checked").child("vinyl").children) {
                            if (imageSnapshot.hasChildren()) {
                                val date = imageSnapshot.child("date").getValue(String::class.java)
                                val imageFile = imageSnapshot.child("imageFile").getValue(String::class.java)
                                val pred = imageSnapshot.child("pred").getValue(String::class.java)

                                if (date != null && imageFile != null && pred != null) {
                                    hasVinyl3 = true

                                    datas.apply {
                                        add(
                                            VinylListInfo(
                                                detect_image = imageFile,
                                                detect_percent = pred,
                                                detect_date = date
                                            )
                                        )
                                    }

                                    Log.d("FIREBASE", "date: $date / img: $imageFile / pred: $pred")
                                }
                                else{
                                    hasVinyl3 = false
                                }
                            }

                            else {
                                Log.d("FIREBASE", "not hasChildren")
                            }
                        }
                    }

                    if (userSnapshot.child("checked").hasChild("stick vinyl")) {
                        for (imageSnapshot in userSnapshot.child("checked").child("stick vinyl").children) {
                            if (imageSnapshot.hasChildren()) {
                                val date = imageSnapshot.child("date").getValue(String::class.java)
                                val imageFile = imageSnapshot.child("imageFile").getValue(String::class.java)
                                val pred = imageSnapshot.child("pred").getValue(String::class.java)

                                if (date != null && imageFile != null && pred != null) {
                                    hasVinyl4 = true

                                    datas.apply {
                                        add(
                                            VinylListInfo(
                                                detect_image = imageFile,
                                                detect_percent = pred,
                                                detect_date = date
                                            )
                                        )
                                    }

                                    Log.d("FIREBASE", "date: $date / img: $imageFile / pred: $pred")
                                }
                                else{
                                    hasVinyl4 = false
                                }
                            }

                            else {
                                Log.d("FIREBASE", "not hasChildren")
                            }
                        }
                    }
                }

                vinylListAdapter.vinylList.addAll(
                    datas
                )

                vinylListAdapter.notifyDataSetChanged()

                if(hasVinyl || hasVinyl2 || hasVinyl3 || hasVinyl4){
                    binding.constraintlayoutVinylRecycler.visibility = View.VISIBLE
                    binding.constraintlayoutVinylEmpty.visibility = View.GONE
                }
                else if(!hasVinyl && !hasVinyl2 && !hasVinyl3 && !hasVinyl4){
                    binding.constraintlayoutVinylRecycler.visibility = View.GONE
                    binding.constraintlayoutVinylEmpty.visibility = View.VISIBLE
                }
            }


            override fun onCancelled(error: DatabaseError) {
                Log.d("FIREBASE", error.message)
            }
        }

        val classRef = database.reference.child("User")
        classRef.addValueEventListener(valueEventListener)
    }
}