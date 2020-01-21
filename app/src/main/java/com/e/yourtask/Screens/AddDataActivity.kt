package com.e.yourtask.Screens

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.e.yourtask.Database.Entity.Note
import com.e.yourtask.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_data.*
import java.util.*

class AddDataActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_data)

        et_adddata_date.setOnTouchListener()

        { view: View, motionEvent: MotionEvent ->
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                et_adddata_date.setText("""$dayOfMonth - ${monthOfYear + 1} - $year""")

            }, year, month, day)
            dpd.show()

            return@setOnTouchListener true

        }





        sub.setOnClickListener()
        {
            var Fdatabase=FirebaseDatabase.getInstance()
            var myref=Fdatabase.getReference("ToDoData")
            var model=Note()


       /*     var e=et_adddata_date.text.toString()
             val intent =Intent()
            intent.putExtra("title",et_adddata_title.text.toString())
            intent.putExtra("des",et_adddata_des.text.toString())
            intent.putExtra("tinedate",e)*/
            var date=et_adddata_date.text.toString()
            var title=et_adddata_title.text.toString()
            var dse=et_adddata_des.text.toString()
            model.title=title
            model.des=dse
            model.Notedate=date
            myref.push().setValue(model)

           val intent =Intent(this,MainScreen::class.java)
            startActivity(intent)

//            myref.child(title).push().setValue(dse)
//            myref.child(title).push().setValue(e)



           /* setResult( Activity.RESULT_OK,intent)
            finish()*/

        }

    }
}
