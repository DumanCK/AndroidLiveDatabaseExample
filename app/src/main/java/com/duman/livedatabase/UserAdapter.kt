package com.duman.livedatabase

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class UserAdapter(val mCtx:Context, val layoutResId: Int, val userList: List<UserInfo>):
    ArrayAdapter<UserInfo>(mCtx, layoutResId, userList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)
        val view:View = layoutInflater.inflate(layoutResId, null)


        val textUser = view.findViewById<TextView>(R.id.tvNamaUser)
        val textViewUpdate = view.findViewById<TextView>(R.id.tvUpdateUser)
        val textViewDelete = view.findViewById<TextView>(R.id.tvDeletUser)

        val userItem = userList[position]

        textUser.text = userItem.nama

        textViewUpdate.setOnClickListener {
            showUpdateDialog(userItem)
        }

        textViewDelete.setOnClickListener {
            showDeleteDialog(userItem)
        }

        return view
    }

    private fun showUpdateDialog(userItem: UserInfo) {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Update User")

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.layout_update, null)

        val editTextNama = view.findViewById<EditText>(R.id.etUpdateNama)
        val editTextStatus = view.findViewById<EditText>(R.id.etUpdateStatus)

        editTextNama.setText(userItem.nama)
        editTextStatus.setText(userItem.status)

        builder.setView(view)
        builder.setPositiveButton("Update"){p0,p1->
            val dbUser = FirebaseDatabase.getInstance("https://live-database-efc5e-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("USERS")

            val nama = editTextNama.text.toString()
            val status = editTextStatus.text.toString()

            if(nama.isEmpty()){
                editTextNama.error = "Belum ada Nama"
                editTextNama.requestFocus()
                return@setPositiveButton
            }

            val uuser = UserInfo(userItem.id, nama, status)
            dbUser.child(userItem.id).setValue(uuser)

            Toast.makeText(mCtx,"Berhasil Update",Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Batal"){p0,p1->
            return@setNegativeButton
        }

        val alert = builder.create()
        alert.show()

    }

    private fun showDeleteDialog(userItem: UserInfo) {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Delete User")

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.layout_update, null)

        val editTextNama = view.findViewById<EditText>(R.id.etUpdateNama)
        val editTextStatus = view.findViewById<EditText>(R.id.etUpdateStatus)

        editTextNama.setText(userItem.nama)
        editTextStatus.setText(userItem.status)

        builder.setView(view)
        builder.setPositiveButton("Delete"){p0,p1->
            val dbUser = FirebaseDatabase.getInstance("https://live-database-efc5e-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("USERS")

            val nama = editTextNama.text.toString()
            //val status = editTextStatus.text.toString()

            if(nama.isEmpty()){
                editTextNama.error = "Belum ada yang Dihapus"
                editTextNama.requestFocus()
                return@setPositiveButton
            }

            //val uuser = UserInfo(userItem.id, nama, status)
            dbUser.child(userItem.id).removeValue()

            Toast.makeText(mCtx,"Berhasil Delete",Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Batal"){p0,p1->
            return@setNegativeButton
        }

        val alert = builder.create()
        alert.show()

    }
}