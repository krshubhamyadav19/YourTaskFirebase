package com.e.yourtask.Screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import com.e.yourtask.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient

import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

import kotlinx.android.synthetic.main.activity_mlogin.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider




//Google Login Request Code
private val RC_SIGN_IN = 7
//Google Sign In Client
private lateinit var mGoogleSignInClient: GoogleSignInClient



class LoginActivity : AppCompatActivity() {

   var mAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(com.e.yourtask.R.layout.activity_mlogin)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)

        btngoogle.setOnClickListener(){
            signIn()
        }


   tv_forgot_pass.setOnClickListener()
   {

       intent= Intent(this,ForgotPasswordActivity::class.java)
       startActivity(intent)
       finish()
   }


//facebook login

        btnfacebook.setOnClickListener(){


        }

        //google login

        btngoogle.setOnClickListener()
        {
               signIn()

        }






        btnLogin.setOnClickListener()
        {

            var email = mloginUserEmail?.text.toString()
            var password = mloginPass?.text.toString()

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
//
                mAuth!!.signInWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener(this) { task ->
                        //                    mProgressBar!!.hide()
                        if (task.isSuccessful) {
                            // Sign in success, update UI with signed-in user's information

                            val intent=Intent(this,MainScreen::class.java)
                            startActivity(intent)

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }

            else {
                Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("Login", "Google sign in failed", e)
                // ...
            }

        }

    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("Login", "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Login", "signInWithCredential:success")
                    val user = mAuth.currentUser
                    val intent=Intent(this,MainScreen::class.java)
                    startActivity(intent)



                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Login", "signInWithCredential:failure", task.exception)
                    Toast.makeText(this,"Auth Failed",Toast.LENGTH_LONG).show()
                    updateUI(null)
                }

                // ...
            }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
       // updateUI(currentUser)



    }

    fun updateUI(user: FirebaseUser?){
        if(user != null){
            //Do your Stuff
            Toast.makeText(this,"Hello ${user.displayName}",Toast.LENGTH_LONG).show()
            val intent=Intent(this,MainScreen::class.java)
            startActivity(intent)
            finish()

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        intent=Intent(this,LoginSignupActivity::class.java)
    }

    }








//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = mAuth!!.getCurrentUser()
//        updateUI(currentUser)
//    }

    // Shared prfrence get data function

    /* fun inputData()
    {




            val mypref=getSharedPreferences("mydata", Context.MODE_PRIVATE)

            val editor = mypref.edit()

            editor.putString("username", mloginUserName.text.toString())
            editor.putString("password", mloginPass.text.toString())
            editor.apply()

            Toast.makeText(this, "data set", Toast.LENGTH_SHORT).show()


    }*/

    //shared prefrence data output by outputData fun

   /* private fun outputData()
    {
        val mypref=getSharedPreferences("mydata", Context.MODE_PRIVATE)

         username = mypref.getString("username"," ")
        val pass = mypref.getString("password"," ")

        mloginUserName.setText(username)
        mloginPass.setText(pass)

        Toast.makeText(this,"data loading", Toast.LENGTH_SHORT).show()

    }*/





