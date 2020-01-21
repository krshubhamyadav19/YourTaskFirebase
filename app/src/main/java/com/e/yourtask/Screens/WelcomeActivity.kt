package com.e.yourtask.Screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.e.yourtask.R
import kotlinx.android.synthetic.main.activity_main.*

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main)

        val animFadeOut = AnimationUtils.loadAnimation(applicationContext, android.R.anim.fade_out)
         imv_main.startAnimation(animFadeOut)

        btnStart.setOnClickListener()
        {
            val intent = Intent(this, LoginSignupActivity::class.java)
            startActivity(intent)
            finish()

        }
    }
}
