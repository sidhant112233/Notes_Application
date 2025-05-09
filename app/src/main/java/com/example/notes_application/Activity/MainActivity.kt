package com.example.notes_application.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notes_application.Adapter.NotesAdapter
import com.example.notes_application.databinding.ActivityMainBinding
import com.example.notes_application.service.NoteDatabase
import com.example.notes_application.R
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.core.graphics.toColorInt


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, NoteAddActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val notesList = withContext(Dispatchers.IO) {
                NoteDatabase.getDatabase(this@MainActivity).noteDao().getAllNotes()

            }

            var adapter = NotesAdapter(this@MainActivity, notesList)
            binding.rvdata.adapter = adapter
        }
        val itemTouchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = (binding.rvdata.adapter as NotesAdapter).noteList[position]

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        // Show delete confirmation dialog
                        val builder = android.app.AlertDialog.Builder(this@MainActivity)
                        builder.setTitle("Delete Note")
                        builder.setMessage("Are you sure you want to delete this note?")
                        builder.setPositiveButton("Delete") { _, _ ->
                            lifecycleScope.launch(Dispatchers.IO) {
                                NoteDatabase.getDatabase(this@MainActivity).noteDao()
                                    .deleteNote(note)
                                withContext(Dispatchers.Main) {
                                    onResume()
                                }
                            }
                        }
                        builder.setNegativeButton("Cancel") { dialog, _ ->
                            dialog.dismiss()
                            binding.rvdata.adapter?.notifyItemChanged(position)
                        }
                        builder.setCancelable(false)
                        builder.show()
                    }

                    ItemTouchHelper.RIGHT -> {
                        // Show edit confirmation dialog
                        val builder = android.app.AlertDialog.Builder(this@MainActivity)
                        builder.setTitle("Edit Note")
                        builder.setMessage("Do you want to edit this note?")
                        builder.setPositiveButton("Edit") { _, _ ->
                            val intent = Intent(this@MainActivity, NoteAddActivity::class.java)
                            intent.putExtra("note_id", note.id)
                            intent.putExtra("title", note.title)
                            intent.putExtra("description", note.description)
                            intent.putExtra("Date", note.date)
                            startActivity(intent)
                        }
                        builder.setNegativeButton("Cancel") { dialog, _ ->
                            dialog.dismiss()
                            binding.rvdata.adapter?.notifyItemChanged(position)
                        }
                        builder.setCancelable(false)
                        builder.show()
                    }
                }
            }


            override fun onChildDraw(
                c: android.graphics.Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor("#DE3163".toColorInt())
                    .addSwipeLeftActionIcon(R.drawable.outline_delete_24)
                    .addSwipeRightBackgroundColor(android.graphics.Color.parseColor("#2196F3"))
                    .addSwipeRightActionIcon(R.drawable.outline_edit_document_24)
                    .create()
                    .decorate()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.rvdata)


    }
}