package com.gitzblitz.recipefinderapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.gitzblitz.recipefinderapp.R
import com.gitzblitz.recipefinderapp.data.RecipeListAdapter
import com.gitzblitz.recipefinderapp.model.BASE_URL
import com.gitzblitz.recipefinderapp.model.QUERY
import com.gitzblitz.recipefinderapp.model.Recipe
import kotlinx.android.synthetic.main.activity_recipe_list.*
import org.json.JSONException
import org.json.JSONObject

class RecipeListActivity : AppCompatActivity() {

  var volleyRequest: RequestQueue? = null
  var recipeList: ArrayList<Recipe>? = null
  var recipeAdapter: RecipeListAdapter? = null
  var layoutManager: RecyclerView.LayoutManager? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_recipe_list)

    var url: String? = null
    var extras = intent.extras

    val ingredients = extras.get("ingredients")
    val search = extras.get("search")
    if (extras != null && !ingredients.equals("") && !search.equals("")) {

      var tempUrl = BASE_URL + ingredients + QUERY + search
      url = tempUrl

    } else {
      url = "http://www.recipepuppy.com/api"
    }
//    var urlString = "http://www.recipepuppy.com/api"

    volleyRequest = Volley.newRequestQueue(this)
    recipeList = ArrayList()

    getRecipe(url)
  }

  fun getRecipe(url: String): Unit {
    val recipeRequest = JsonObjectRequest(Request.Method.GET, url,
        Response.Listener { response: JSONObject ->
          try {
            val resultArray = response.getJSONArray("results")
            for (i in 0..resultArray.length() - 1) {
              var recipeObj = resultArray.getJSONObject(i)
              var title = recipeObj.getString("title")
              var link = recipeObj.getString("href")
              var thumbnail = recipeObj.getString("thumbnail")
              var ingredients = recipeObj.getString("ingredients")

              Log.d("RESULTS ===>", title)
              var recipe = Recipe()
              recipe.title = title
              recipe.link = link
              recipe.thumbnail = thumbnail
              recipe.ingredients = "Ingredients: $ingredients"

              recipeList!!.add(recipe)
              recipeAdapter = RecipeListAdapter(recipeList!!, this)
              layoutManager = LinearLayoutManager(this)
              recipeRecyclerView.layoutManager = layoutManager
              recipeRecyclerView.adapter = recipeAdapter
            }
            recipeAdapter!!.notifyDataSetChanged()

          } catch (e: JSONException) {
            e.printStackTrace()
          }

        },
        Response.ErrorListener { error: VolleyError? ->
          try {
            Log.d("Error", error.toString())
          } catch (e: JSONException) {
            e.printStackTrace()
          }

        })
    volleyRequest!!.add(recipeRequest)
  }
}
