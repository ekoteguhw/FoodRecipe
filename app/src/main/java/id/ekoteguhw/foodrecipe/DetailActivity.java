package id.ekoteguhw.foodrecipe;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import id.ekoteguhw.foodrecipe.data.ApiClient;
import id.ekoteguhw.foodrecipe.data.ApiInterface;
import id.ekoteguhw.foodrecipe.models.Recipe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private TextView tvName, tvCategory, tvArea, tvTags, tvIngredients, tvInstructions;
    private ImageView ivPhoto;
    private ApiInterface apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setActionBarTitle("Detail Recipe");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvName = findViewById(R.id.tv_detail_name);
        tvCategory = findViewById(R.id.tv_detail_category);
        tvArea = findViewById(R.id.tv_detail_area);
        tvTags = findViewById(R.id.tv_detail_tags);
        tvIngredients = findViewById(R.id.tv_detail_ingredients);
        tvInstructions = findViewById(R.id.tv_detail_instructions);
        ivPhoto = findViewById(R.id.iv_detail_photo);

        Intent intent = getIntent();
        String id = intent.getExtras().getString("id");
        loadDataRecipe(id);
    }

    private void loadDataRecipe(String id) {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = apiService.getDetailRecipe(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String jsonResponse = response.body().toString();
                        convertData(jsonResponse);
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

    private void convertData(String data) {
        try {
            JSONObject obj = new JSONObject(data);
            if (!obj.optString("meals").isEmpty()) {
                JSONArray dataArray = obj.getJSONArray("meals");
                JSONObject dataObj = dataArray.getJSONObject(0);
                Recipe recipe = new Recipe();
                recipe.setId(dataObj.getString("idMeal"));
                recipe.setName(dataObj.getString("strMeal"));
                recipe.setArea(dataObj.getString("strArea"));
                recipe.setCategory(dataObj.getString("strCategory"));
                recipe.setPhoto(dataObj.getString("strMealThumb"));
                recipe.setTags(dataObj.getString("strTags"));
                recipe.setInstructions(dataObj.getString("strInstructions"));

                String[] arrIngs = new String[20];

                arrIngs[0] = dataObj.getString("strIngredient1") + " " + dataObj.getString("strMeasure1");
                arrIngs[1] = dataObj.getString("strIngredient2") + " " + dataObj.getString("strMeasure2");
                arrIngs[2] = dataObj.getString("strIngredient3") + " " + dataObj.getString("strMeasure3");
                arrIngs[3] = dataObj.getString("strIngredient4") + " " + dataObj.getString("strMeasure4");
                arrIngs[4] = dataObj.getString("strIngredient5") + " " + dataObj.getString("strMeasure5");
                arrIngs[5] = dataObj.getString("strIngredient6") + " " + dataObj.getString("strMeasure6");
                arrIngs[6] = dataObj.getString("strIngredient7") + " " + dataObj.getString("strMeasure7");
                arrIngs[7] = dataObj.getString("strIngredient8") + " " + dataObj.getString("strMeasure8");
                arrIngs[8] = dataObj.getString("strIngredient9") + " " + dataObj.getString("strMeasure9");
                arrIngs[9] = dataObj.getString("strIngredient10") + " " + dataObj.getString("strMeasure10");
                arrIngs[10] = dataObj.getString("strIngredient11") + " " + dataObj.getString("strMeasure11");
                arrIngs[11] = dataObj.getString("strIngredient12") + " " + dataObj.getString("strMeasure12");
                arrIngs[12] = dataObj.getString("strIngredient13") + " " + dataObj.getString("strMeasure13");
                arrIngs[13] = dataObj.getString("strIngredient14") + " " + dataObj.getString("strMeasure14");
                arrIngs[14] = dataObj.getString("strIngredient15") + " " + dataObj.getString("strMeasure15");
                arrIngs[15] = dataObj.getString("strIngredient16") + " " + dataObj.getString("strMeasure16");
                arrIngs[16] = dataObj.getString("strIngredient17") + " " + dataObj.getString("strMeasure17");
                arrIngs[17] = dataObj.getString("strIngredient18") + " " + dataObj.getString("strMeasure18");
                arrIngs[18] = dataObj.getString("strIngredient19") + " " + dataObj.getString("strMeasure19");
                arrIngs[19] = dataObj.getString("strIngredient20") + " " + dataObj.getString("strMeasure20");

                String newIngs = "";
                for (String s: arrIngs) {
                    if (!s.trim().isEmpty()) {
                        newIngs += "<li>\t" + s + "</li>";
                    }
                }

                tvName.setText(recipe.getName());
                Spanned category = Html.fromHtml("<b>Category:</b>  " + recipe.getCategory());
                Spanned area = Html.fromHtml("<b>Area:</b>  " + recipe.getArea());
                Spanned tags = Html.fromHtml("<b>Tags:</b>  " + recipe.getTags());
                Spanned ingredients = Html.fromHtml("<ul>" + newIngs + "</ul>");
                Spanned instructions = Html.fromHtml(recipe.getInstructions());
                tvCategory.setText(category);
                tvArea.setText(area);
                tvTags.setText(tags);
                tvIngredients.setText(ingredients);
                tvInstructions.setText(instructions);
                Glide.with(this).load(recipe.getPhoto())
                        .apply(new RequestOptions().placeholder(new ColorDrawable(Color.GRAY)))
                        .into(ivPhoto);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}
