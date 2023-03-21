package com.example.bmi
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class ReportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        val resultTV : TextView = findViewById<TextView>(R.id.resultTV)
        val adviceTV : TextView = findViewById<TextView>(R.id.adviceTV)
        val resultImage : ImageView = findViewById<ImageView>(R.id.resultImage)
        val bundle: Bundle? = intent.extras
        val height = java.lang.Double.parseDouble(bundle?.getString("height")) / 100
        val weight = java.lang.Double.parseDouble(bundle?.getString("weight"))
        val bmi = weight / (height * height)

        val nf = DecimalFormat("0.00")

        resultTV.text = getString(R.string.bmi_result) + " " + nf.format(bmi)

        if (bmi > 25) {
            resultImage.setImageResource(R.drawable.bot_fat)
            adviceTV.setText(R.string.advice_heavy)
        } else if (bmi < 20) {
            resultImage.setImageResource(R.drawable.bot_thin)
            adviceTV.setText(R.string.advice_light)
        } else {
            resultImage.setImageResource(R.drawable.bot_fit)
            adviceTV.setText(R.string.advice_average)
        }
    }
}