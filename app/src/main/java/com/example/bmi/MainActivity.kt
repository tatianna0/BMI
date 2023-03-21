package com.example.bmi
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    lateinit var heightET : EditText
    lateinit var weightET : EditText


    fun savePreferences(h: String, w: String) {
        val pref = getSharedPreferences("BMI", Context.MODE_PRIVATE)
        pref.edit().putString("height", h).apply()
        pref.edit().putString("weight", w).apply()
    }

    fun loadPreferences() {
        val pref = getSharedPreferences("BMI", Context.MODE_PRIVATE)
        heightET.setText(pref.getString("height", "0"))
        weightET.setText(pref.getString("weight", "0"))
    }

    fun about_BMI_Dialog() {

        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.about_button)
            .setMessage(R.string.about_msg)
            .setPositiveButton("OK") {dialog, which ->
                Toast.makeText(this@MainActivity, "OK", Toast.LENGTH_SHORT).show()
            }
            .show()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        heightET = findViewById(R.id.heightET)
        weightET = findViewById(R.id.weightET)

        val reportBtn : Button = findViewById<Button>(R.id.reportBtn)



        reportBtn.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//
//                val height = heightET.text.toString()
//                val weight = weightET.text.toString()
//                val intent = Intent(applicationContext, ReportActivity::class.java)
//
//                val bundle = Bundle()
//                bundle.putString("height", height)
//                bundle.putString("weight", weight)
//                intent.putExtras(bundle)
//                startActivity(intent)
//            }


            override fun onClick(v: View?) {

                val height = heightET.text.toString()
                val weight = weightET.text.toString()

                if (height == "" || weight == "" ||height == "0" || weight=="0") {
                    //Toast.makeText(this@MainActivity, R.string.bmi_warning, Toast.LENGTH_LONG).show()
                    Snackbar.make(findViewById(android.R.id.content),
                        R.string.bmi_warning, Snackbar.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(applicationContext, ReportActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString("height", height)
                    bundle.putString("weight", weight)
                    intent.putExtras(bundle)
                    startActivity(intent)
                    savePreferences(height, weight)
                }
            }





        })

        val aboutBtn : Button = findViewById<Button>(R.id.aboutBtn)

        aboutBtn.setOnClickListener{
            about_BMI_Dialog()
        }

        //图片点击监听
        val imageView: ImageView = findViewById<ImageView>(R.id.imageView)
        registerForContextMenu(imageView)



        imageView.setOnLongClickListener {
            openContextMenu(imageView)
            true
        }
    }

    // --- Option Menu ---
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_options, menu)
        return super.onCreateOptionsMenu(menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {

            R.id.menu_wiki -> {
                openWebPage("http://en.wikipedia.org/wiki/Body_mass_index",this)
                return true
            }
            R.id.menu_about -> {
                about_BMI_Dialog()
                return true
            }
            R.id.menu_exit -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }





    fun openWebPage(urls: String, context : Context) {
        val uris = Uri.parse(urls)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        context.startActivity(intents)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo:
    ContextMenu.ContextMenuInfo?) {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_context, menu)
        return super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuItemAbout -> {
                about_BMI_Dialog()
                return true
            }
            R.id.menuItemWebpage -> {
                openWebPage("http://en.wikipedia.org/wiki/Body_mass_index",this)
                return true
            }
            else -> return super.onContextItemSelected(item)
        }

    }




    override fun onStart() {
        super.onStart()
        loadPreferences()
    }
}