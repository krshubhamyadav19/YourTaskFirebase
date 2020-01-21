package com.e.yourtask.Adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.yourtask.Database.Entity.Note
import com.e.yourtask.Interfaces.ItemClick
import com.e.yourtask.R
import kotlinx.android.synthetic.main.list_layout.view.*

class CustomAdapter( val noteList: ArrayList<Note>,  val listener: ItemClick) : RecyclerView.Adapter<CustomAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val v=LayoutInflater.from(parent.context).inflate(R.layout.list_layout,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindIteams(noteList[position],holder)
     //#applying color to grid
        val color = (Math.random() * 16777215).toInt() or (0xFF shl 24)
        holder.itemView.lyt_content_view.setBackgroundColor(color)

    }
   inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)
    {

        fun bindIteams(note: Note, holder: ViewHolder)
        {

           itemView.tvListLayoutTitle.text=note.title
            itemView.tvListLayoutDes.text=note.des
            itemView.texttvListLayoutDate.text=note.Notedate

            holder.itemView.list_delete.setOnClickListener(){ listener.OnItemDeleteClick(note)  }
            holder.itemView.list_edit.setOnClickListener(){ listener.OnItemUpdateClick(note)  }

        }


    }
    fun onUpdate(data: Note)
    {
        noteList.add(data)
        notifyDataSetChanged()
    }
    fun onAddAll(data:List<Note>)
    {
        noteList.clear()
        noteList.addAll(data)
        notifyDataSetChanged()
    }
    fun removeData(data: Note)
    {

        var position =noteList.indexOf(data)
        noteList.removeAt(position)
        notifyItemRemoved(position)
    }

}