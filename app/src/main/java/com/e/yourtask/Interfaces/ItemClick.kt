package com.e.yourtask.Interfaces

import com.e.yourtask.Database.Entity.Note

interface ItemClick {
    fun OnItemDeleteClick(note: Note)
    fun OnItemUpdateClick(note: Note)
}
