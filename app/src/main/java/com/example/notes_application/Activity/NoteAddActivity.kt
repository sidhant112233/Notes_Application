// NoteAddActivity.kt
package com.example.notes_application.Activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.notes_application.R
import com.example.notes_application.databinding.ActivityNoteAddBinding
import com.example.notes_application.service.NoteDatabase
import com.example.notes_application.service.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class NoteAddActivity : AppCompatActivity() {
    lateinit var binding: ActivityNoteAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        val tvDate = findViewById<TextView>(R.id.tvDate)

        tvDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "${selectedDay}/${selectedMonth + 1}/$selectedYear"
                tvDate.text = selectedDate
            }, year, month, day)

            datePicker.show()
        }
        val noteId = intent.getIntExtra("note_id", -1)
        val isEdit = noteId != -1

        if (isEdit) {
            binding.etTitle.setText(intent.getStringExtra("title"))
            binding.etDescription.setText(intent.getStringExtra("description"))
            binding.tvDate.setText(intent.getStringExtra("Date"))
        }



        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val description = binding.etDescription.text.toString()
            val date = tvDate.text.toString()

            if (title.isBlank() || description.isBlank()) {
                Toast.makeText(this, "Title and description cannot be empty", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val db = NoteDatabase.getDatabase(this)

            lifecycleScope.launch(Dispatchers.IO) {
                val noteDao = db.noteDao()

                if (isEdit) {
                    val updatedNote =
                        NoteEntity(id = noteId, title = title, description = description, date = date)
                    noteDao.updateNote(updatedNote)
                } else {
                    val newNote = NoteEntity(title = title, description = description, date = date)
                    noteDao.addNote(newNote)
                }

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@NoteAddActivity,
                        "Note Saved Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }
    }
}
