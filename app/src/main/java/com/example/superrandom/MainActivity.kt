package com.example.superrandom

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import java.security.MessageDigest
import java.time.format.DateTimeFormatter
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    val dateString = "200"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.superButton)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getNASAImage()
        setupButton(button)
    }
    private fun getNASAImage() {
        val client = AsyncHttpClient()
        val randomDay = Random.nextInt(1, 21) // 1 to 20
        val dateString = "2001-05-%02d".format(randomDay) // e.g., "2001-05-07"
        val demoKey = "JmzbsLQ46dpirO7Em8ZPtohwcbQdmkcAijioG347"

        client["https://api.nasa.gov/planetary/apod?date=$dateString&api_key=$demoKey", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                val nasaImageURL = json.jsonObject.getString("hdurl")
                val nasaTitle = json.jsonObject.getString("title")
                val nasaDesc = json.jsonObject.getString("explanation")

                val imageView = findViewById<ImageView>(R.id.superImage)
                val nasaName = findViewById<TextView>(R.id.superName)
                val nasaText = findViewById<TextView>(R.id.superDescription)

                Log.d("NASA", "response successful : $json")
                Log.d("nasaImageURL", "pet image URL set: $nasaImageURL")
                Log.d("nasaTitle", "pet image URL set: $nasaTitle")
                Log.d("nasaTitle", "pet image URL set: $nasaDesc")

                nasaName.text = nasaTitle
                nasaText.text = nasaDesc.substring(0,300) + "..."

                Glide.with(this@MainActivity)
                    .load(nasaImageURL)
                    .fitCenter()
                    .into(imageView)


            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("NASA Error", errorResponse)
            }
        }]
    }

    private fun setupButton(button: Button) {
        button.setOnClickListener {
            getNASAImage()
        }
    }

}