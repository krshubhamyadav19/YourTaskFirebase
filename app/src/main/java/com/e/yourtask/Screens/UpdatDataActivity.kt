package com.e.yourtask.Screens

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.e.yourtask.Database.Entity.Note
import com.e.yourtask.R
import kotlinx.android.synthetic.main.activity_updat_data.*



class UpdatDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_updat_data)


        var note = intent.getSerializableExtra("data") as Note

        etUpdateTitle.setText(note.title)
        etUpdateDes.setText(note.des)
       val key=note.dataId





        btnUpdate.setOnClickListener() {
            var model=Note()
            var upDateTitle=etUpdateTitle.text.toString()
            var updateDes=etUpdateDes.text.toString()

            model.title=upDateTitle
            model.des=updateDes

            mDatabase.child(key).setValue(model)

            val intent = Intent(this,MainScreen::class.java)
            startActivity(intent)

    }




    }
}
