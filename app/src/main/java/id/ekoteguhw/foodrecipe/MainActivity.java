package id.ekoteguhw.foodrecipe;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import id.ekoteguhw.foodrecipe.adapters.CardRecipeAdapter;
import id.ekoteguhw.foodrecipe.adapters.GridRecipeAdapter;
import id.ekoteguhw.foodrecipe.adapters.ListRecipeAdapter;
import id.ekoteguhw.foodrecipe.data.ApiClient;
import id.ekoteguhw.foodrecipe.data.ApiInterface;
import id.ekoteguhw.foodrecipe.models.Recipe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvCategory;
    private ArrayList<Recipe> listRecipe;
    private ApiInterface apiService;
    final String STATE_TITLE = "state_string";
    final String STATE_LIST = "state_list";
    final String STATE_MODE = "state_mode";
    String titleOn = "";
    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvCategory = findViewById(R.id.rv_category);
        rvCategory.setHasFixedSize(true);

        listRecipe = new ArrayList<>();

        if (savedInstanceState == null) {
            setActionBarTitle("FoodRecipe - ListView");
            titleOn = "FoodRecipe - ListView";
            loadDataRecipe("list");
            mode = R.id.action_list;
        } else {
            String stateTitle = savedInstanceState.getString(STATE_TITLE);
            int stateMode = savedInstanceState.getInt(STATE_MODE);
            setActionBarTitle(stateTitle);
            setMode(stateMode);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_TITLE, getSupportActionBar().getTitle().toString());
        outState.putParcelableArrayList(STATE_LIST, listRecipe);
        outState.putInt(STATE_MODE, mode);
    }

    private void loadDataRecipe(final String status) {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = apiService.getRecipes();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String jsonResponse = response.body().toString();
                        convertData(jsonResponse, status);
                    } else {
                        Log.i("RESULT", "EMPTY");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    void convertData(String response, String status) {
        try {
            JSONObject obj = new JSONObject(response);
            if (!obj.optString("meals").isEmpty()) {
                JSONArray dataArray = obj.getJSONArray("meals");
                for (int i=0; i< dataArray.length(); i++) {
                    Recipe recipe = new Recipe();
                    JSONObject dataObj = dataArray.getJSONObject(i);

                    recipe.setId(dataObj.getString("idMeal"));
                    recipe.setName(dataObj.getString("strMeal"));
                    recipe.setArea(dataObj.getString("strArea"));
                    recipe.setCategory(dataObj.getString("strCategory"));
                    recipe.setPhoto(dataObj.getString("strMealThumb"));
                    recipe.setTags(dataObj.getString("strTags"));
                    recipe.setInstructions(dataObj.getString("strInstructions"));
                    Log.i("Per data ke-" + i, recipe.toString());
                    listRecipe.add(recipe);
                }

                switch (status) {
                    case "list":
                        showRecyclerList();
                        break;
                    case "grid":
                        showRecyclerGrid();
                        break;
                    case "card":
                        showRecyclerCard();
                        break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    public void setMode(int selectedMode) {
        String title = null;
        switch (selectedMode) {
            case R.id.action_list:
                title = "FoodRecipe - ListView";
                loadDataRecipe("list");
                break;

            case R.id.action_grid:
                title = "FoodRecipe - GridView";
                loadDataRecipe("grid");
                break;

            case R.id.action_cardview:
                title = "FoodRecipe - CardView";
                loadDataRecipe("card");
                break;
            case R.id.action_about:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Application has been built using Java, implement RecyclerView (List, Grid, Card), CircleImageView, Glide, Retrofit and data from API from TheMealDB.").setTitle("About FoodRecipe");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                title = titleOn;
                break;
        }
        titleOn = title;
        mode = selectedMode;
        setActionBarTitle(title);
    }

    private void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    private void showRecyclerList(){
        rvCategory.setLayoutManager(new LinearLayoutManager(this));
        ListRecipeAdapter listRecipeAdapter = new ListRecipeAdapter(this);
        listRecipeAdapter.setListRecipe(listRecipe);
        rvCategory.setAdapter(listRecipeAdapter);
    }

    private void showRecyclerGrid(){
        rvCategory.setLayoutManager(new GridLayoutManager(this, 2));
        GridRecipeAdapter gridRecipeAdapter = new GridRecipeAdapter(this);
        gridRecipeAdapter.setListRecipe(listRecipe);
        rvCategory.setAdapter(gridRecipeAdapter);
    }

    private void showRecyclerCard(){
        rvCategory.setLayoutManager(new LinearLayoutManager(this));
        CardRecipeAdapter cardRecipeAdapter = new CardRecipeAdapter(this);
        cardRecipeAdapter.setListRecipe(listRecipe);
        rvCategory.setAdapter(cardRecipeAdapter);
    }
}
