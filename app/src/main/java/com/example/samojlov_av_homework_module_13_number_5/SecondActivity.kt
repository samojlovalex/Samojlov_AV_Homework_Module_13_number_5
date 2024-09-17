package com.example.samojlov_av_homework_module_13_number_5

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.samojlov_av_homework_module_13_number_5.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var toolbarSecond: androidx.appcompat.widget.Toolbar
    private lateinit var nameET: EditText
    private lateinit var postSP: Spinner
    private lateinit var phoneET: EditText
    private lateinit var saveBT: Button
    private lateinit var getBT: Button
    private lateinit var clearBT: Button
    private lateinit var nameBDTV: TextView
    private lateinit var postBDTV: TextView
    private lateinit var phoneBDTV: TextView

    private var post = ""
    private val listPosition = ListOfPositions()

    private val db = DBHelper(this, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()

        dbEnter()

        dbPrint()

        dbClear()
    }

    private fun dbClear() {
        clearBT.setOnClickListener {
            db.removeAll()
            nameBDTV.text = ""
            postBDTV.text = ""
            phoneBDTV.text = ""
        }
    }

    @SuppressLint("Range")
    private fun dbPrint() {
        getBT.setOnClickListener {
            val cursor = db.getInfo()
            if (cursor != null && cursor.moveToFirst()) {
                cursor.moveToFirst()
                nameBDTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)) + "\n")
                postBDTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_POST)) + "\n")
                phoneBDTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHONE)) + "\n")
            }
            while (cursor!!.moveToNext()) {
                nameBDTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)) + "\n")
                postBDTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_POST)) + "\n")
                phoneBDTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHONE)) + "\n")
            }
            cursor.close()
        }
    }

    private fun dbEnter() {
        saveBT.setOnClickListener {
            if (nameET.text.isEmpty() || phoneET.text.isEmpty() || post == getString(
                    R.string.post_first_item
                )
            ) return@setOnClickListener
            val name = nameET.text.toString()
            val phone = phoneET.text.toString()

            db.addName(name, post, phone)
            Toast.makeText(this, getString(R.string.addName_Toast, name), Toast.LENGTH_LONG).show()

            nameET.text.clear()
            phoneET.text.clear()
            post = getString(R.string.poct_clear_text)
            postSP.setSelection(0)
        }
    }

    private fun init() {
        toolbarSecond = binding.toolbarSecond
        setSupportActionBar(toolbarSecond)
        title = getString(R.string.toolbar_title)
        toolbarSecond.subtitle = getString(R.string.toolbar_subtitle)

        nameET = binding.nameET
        postSP = binding.postSP
        phoneET = binding.phoneET
        saveBT = binding.saveBT
        getBT = binding.getBT
        clearBT = binding.clearBT
        nameBDTV = binding.nameBDTV
        postBDTV = binding.postBDTV
        phoneBDTV = binding.phoneBDTV

        spinnerPersonEdit()

    }

    private fun spinnerPersonEdit() {
        val adapter = ArrayAdapter(this, R.layout.multiline_spinner_item, listPosition.list)
        adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item)
        postSP.adapter = adapter

        val itemSelectedListener: AdapterView.OnItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val item = parent?.getItemAtPosition(position) as String
                    post = item
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        postSP.onItemSelectedListener = itemSelectedListener
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    @SuppressLint("SetTextI18n", "ShowToast")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenu -> {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.toast_exit),
                    Toast.LENGTH_LONG
                ).show()
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}