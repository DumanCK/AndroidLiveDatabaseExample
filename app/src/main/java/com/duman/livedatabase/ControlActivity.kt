package com.duman.livedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.duman.livedatabase.databinding.ActivityControlBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ControlActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var ref : DatabaseReference
    private lateinit var binding: ActivityControlBinding
    private lateinit var userlist : MutableList<UserInfo>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityControlBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        userlist = mutableListOf()
        database = FirebaseDatabase.getInstance("https://live-database-efc5e-default-rtdb.asia-southeast1.firebasedatabase.app/")
        ref = database.getReference("USERS")

        binding.btnSSigned.setOnClickListener {
            saveUser()
        }

        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot!!.exists()){
                    userlist.clear()
                    for(u in snapshot.children){
                        val euser = u.getValue(UserInfo::class.java)
                        userlist.add(euser!!)
                    }
                    val adapter = UserAdapter(this@ControlActivity,R.layout.users,userlist)
                    binding.lvUser.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun saveUser() {
        val nama = binding.etNama.text.toString()
        val status = binding.etStatus.text.toString()

        if(nama.isEmpty()){
            Toast.makeText(this,"Belum ada namanya",Toast.LENGTH_SHORT).show()
            return
        }

        val userId = ref.push().key
        val pengguna = userId?.let { UserInfo(it, nama, status) }

        if (userId != null) {
            ref.child(userId).setValue(pengguna).addOnCompleteListener{
                Toast.makeText(this,"Pengguna berhasil disimpan",Toast.LENGTH_SHORT).show()
            }
        }
    }
}