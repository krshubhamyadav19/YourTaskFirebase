package com.e.yourtask.Screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.e.yourtask.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_mlogin.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up)



         var mAuth=FirebaseAuth.getInstance()
        var dataBase=FirebaseDatabase.getInstance()
         var myRef=dataBase.getReference("UserName")

        btnsignup.setOnClickListener(){

            var email = signupEmail?.text.toString()
            var password = signupPassword?.text.toString()
            var username=edi_name.text.toString()
            myRef.setValue(username)


            mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){task ->

                    if(task.isSuccessful){

                        Toast.makeText(this, "signup sucessfull", Toast.LENGTH_SHORT).show()
                        intent= Intent(this,LoginSignupActivity::class.java)
                        startActivity(intent)

//                    val user = mAuth!!.getCurrentUser()
//                    updateUI(user)
                    
                }else
                    {
                        Toast.makeText(this, "signIn: Fail!", Toast.LENGTH_SHORT).show()


                    }
                }






        }
    }
}
