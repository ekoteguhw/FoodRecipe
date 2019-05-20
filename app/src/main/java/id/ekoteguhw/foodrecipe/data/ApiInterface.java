package id.ekoteguhw.foodrecipe.data;

import id.ekoteguhw.foodrecipe.models.Items;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("latest.php")
    Call<String> getRecipes();

    @GET("lookup.php")
    Call<String> getDetailRecipe(@Query("i") String id);
}
