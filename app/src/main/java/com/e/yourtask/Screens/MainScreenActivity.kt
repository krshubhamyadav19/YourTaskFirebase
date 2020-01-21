package com.e.yourtask.Screens

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.yourtask.Adapters.CustomAdapter
import com.e.yourtask.Database.Entity.Note
import com.e.yourtask.Interfaces.ItemClick
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main_screen.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.widget.TextView
import com.bumptech.glide.Glide
import com.e.yourtask.R


val userList = ArrayList<Note>()
lateinit var storage: FirebaseStorage
lateinit var storageReference: StorageReference
lateinit var filePath: Uri
var currentUserName:String?=null

var PICK_IMAGE_REQUEST = 10
val mDatabase = FirebaseDatabase.getInstance().getReference("ToDoData")

class MainScreen : AppCompatActivity(), ItemClick {


    override fun OnItemDeleteClick(note: Note) {
        var position = userList.indexOf(note)
        Delete(userList[position].dataId)
        (recyclerView.adapter as CustomAdapter).removeData(note)


    }

    override fun OnItemUpdateClick(note: Note) {
        startActivity(Intent(this, UpdatDataActivity::class.java).putExtra("data", note))
        finish()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(com.e.yourtask.R.layout.activity_main_screen)

        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference

        val recyclerView = findViewById<RecyclerView>(com.e.yourtask.R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        val sdf_ = SimpleDateFormat("EEEE")
        val date = Date()
        val dayName = sdf_.format(date)
        tvDay.text = dayName




//        var strUser: String = intent.getStringExtra("username")
//        tvMainName.setText(strUser)

        // setting name of current user


        tvMainImage.setOnClickListener {
            var intent = Intent()

            intent.type = "image/*"

            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST
            )


            /*var  ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)*/

        }


        btnMainSignout.setOnClickListener {





              val dialog = Dialog(this)
                dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog .setCancelable(false)
                dialog .setContentView(R.layout.signoutdialoglayout)
                dialog.getWindow()?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
                val noBtn = dialog.findViewById(R.id.btnNo) as TextView
                val yesBtn = dialog.findViewById(R.id.btnYes) as TextView

                noBtn.setOnClickListener()
                {

                    Toast.makeText(this,"No Clicked",Toast.LENGTH_SHORT).show()
                    finish()
                }
                yesBtn.setOnClickListener()
                {

                    Toast.makeText(this,"Yes Clicked",Toast.LENGTH_SHORT).show()
                    var mAuth = FirebaseAuth.getInstance()
                    val currentUser = mAuth.currentUser
                    if (currentUser != null) {

                        mAuth.signOut()

                        Toast.makeText(this, "SignOut ${currentUser.displayName}", Toast.LENGTH_LONG).show()

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()

                    }
                    currentUser!!.delete()

                }

                dialog .show()



        }



        btnAddMain.setOnClickListener()
        {
            val intent = Intent(this, AddDataActivity::class.java)
            startActivityForResult(intent, 1)
            finish()
        }

    }


    private fun getdata() {

///  Fire base DataBase

        mDatabase.addValueEventListener(object : ValueEventListener {


            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                userList.clear()

                for (postSnapshot in dataSnapshot.children) {
                    //getting artist
                    val data = postSnapshot.getValue<Note>(Note::class.java)
                    //adding artist to the list
                    userList.add(data!!)
                }


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })

    }

    override fun onStart() {

        getdata()
        super.onStart()
        val adapter = CustomAdapter(userList, this)
        recyclerView.adapter = adapter

        /// setting a name of curent user name
        currentUserName()
        userData()
    }

    fun currentUserName()
    {

        var mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            currentUserName=currentUser.displayName
            txtUserName.setText(currentUserName)



        }




    }

    fun Delete(key: String) {

        mDatabase.child(key).removeValue()


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                    && data != null && data.data != null)
        ) {
            filePath = data.data!!

            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)

            tvMainImage.setImageBitmap(bitmap)

            var ref = storageReference.child("images/" + UUID.randomUUID().toString())

            ref.putFile(filePath)


        }

    }

    fun userData()
    {

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            for (profile in it.providerData) {
                // Id of the provider (ex: google.com)
                val providerId = profile.providerId
                // UID specific to the provider
                val uid = profile.uid
                val phone = profile.phoneNumber
                // Name, email address, and profile photo Url
                val name = profile.displayName
                val email = profile.email
                val photoUrl = profile.photoUrl
                val photoUrl1 = profile.photoUrl.toString()
                Glide.with(this).load(photoUrl).into(tvMainImage)

            }
        }
    }


        /*  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                var note = Note()

                note.title = data.getStringExtra("title")
                note.des = data.getStringExtra("des")
               note.Notedate = data.getStringExtra("tinedate")



                (recyclerView.adapter as CustomAdapter).onUpdate(note)

                Thread {

                    note.title = note.title.toString()
                    note.des = note.des.toString()
                    note.Notedate=note.Notedate.toString()
                    db.noteDao().insertAll(note)
                }.start()

            }


        }
        if (requestCode == 2 && resultCode == RESULT_OK) {

                Thread {

                  var data=  db.noteDao().getAll()
                    runOnUiThread {
                        (recyclerView.adapter as CustomAdapter).onAddAll(data)
                    }

                }.start()

            }*/



}




