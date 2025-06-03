package com.example.lab6

import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    val languages = ArrayList<String>().apply {
        add("Java")
        add("Kotlin")
        add("Python")
        add("C++")
        add("JavaScript")
        add("Ruby")
        add("Swift")
        add("Go")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val popupBtn = findViewById<Button>(R.id.popup_menu_button)
        popupBtn.setOnClickListener {
            showPopupMenu()
        }

        val listLanguage = findViewById<ListView>(R.id.list_view)
        listLanguage.adapter = CustomAdapter(languages, this)
        registerForContextMenu(listLanguage)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.create_new -> {
                Toast.makeText(this, "Create New Selected", Toast.LENGTH_SHORT).show()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun showPopupMenu() {
        val popupBtn = findViewById<Button>(R.id.popup_menu_button)
        val popupMenu = android.widget.PopupMenu(this, popupBtn)

        popupMenu.inflate(R.menu.popup_menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_about -> {
                    Toast.makeText(this, "Action One Selected", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.action_help -> {
                    Toast.makeText(this, "Action Two Selected", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.action_about_context -> {
                Toast.makeText(
                    this,
                    "Context Action One Selected for item at position ${info.position}",
                    Toast.LENGTH_SHORT
                ).show()
                true
            }

            R.id.action_help_context -> {
                Toast.makeText(
                    this,
                    "Context Action Two Selected for item at position ${info.position}",
                    Toast.LENGTH_SHORT
                ).show()
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }
}