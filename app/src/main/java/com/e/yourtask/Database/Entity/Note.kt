package com.e.yourtask.Database.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "note_table")

data class Note( var title: String="",var des :String="",var dataId:String="",@PrimaryKey(autoGenerate = true) var Id:Int?=null,var Notedate:String=""):Serializable{

}