package com.gitzblitz.recipefinderapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gitzblitz.recipefinderapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)


    btnSearch.setOnClickListener{

      var intent = Intent(this, RecipeListActivity::class.java)
      var ingredients = ingredientsEdt.text.toString().trim()
      var searchTerm = searchTermEdt.text.toString().trim()
      intent.putExtra("ingredients", ingredients)
      intent.putExtra("search", searchTerm)
      startActivity(intent)
    }
  }
}
