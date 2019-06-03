package com.androidcourse.reminderlevel4kotlin.ui.addreminder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidcourse.reminderlevel4kotlin.R
import com.androidcourse.reminderlevel4kotlin.database.ReminderRepository
import com.androidcourse.reminderlevel4kotlin.model.Reminder
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddActivity : AppCompatActivity() {

    private lateinit var reminderRepository: ReminderRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)

        reminderRepository = ReminderRepository(this)
        initViews()
    }

    private fun initViews() {
        fab.setOnClickListener { onSaveClick() }

        // Used for creating a back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun onSaveClick() {
        if (etAddReminder.text.toString().isNotBlank()) {
            CoroutineScope(Dispatchers.Main).launch {
                val reminder = Reminder(etAddReminder.text.toString())
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_REMINDER, reminder)
                withContext(Dispatchers.IO) {
                    reminderRepository.insertReminder(reminder)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        } else {
            Toast.makeText(this, getString(R.string.msg_empty_reminder), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> { // Used to identify when the user has clicked the back button
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_REMINDER = "EXTRA_REMINDER"
    }

}


