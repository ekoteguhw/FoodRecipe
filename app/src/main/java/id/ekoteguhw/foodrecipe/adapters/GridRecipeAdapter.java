package id.ekoteguhw.foodrecipe.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import id.ekoteguhw.foodrecipe.DetailActivity;
import id.ekoteguhw.foodrecipe.R;
import id.ekoteguhw.foodrecipe.models.Recipe;

public class GridRecipeAdapter extends RecyclerView.Adapter<GridRecipeAdapter.GridViewHolder> {

    private Context context;
    private ArrayList<Recipe> listRecipe;

    public GridRecipeAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Recipe> getListRecipe() {
        return listRecipe;
    }

    public void setListRecipe(ArrayList<Recipe> listRecipe) {
        this.listRecipe = listRecipe;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid_recipe, viewGroup, false);
        return new GridViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder gridViewHolder, final int position) {
        Glide.with(context)
                .load(getListRecipe().get(position).getPhoto())
                .apply(new RequestOptions().override(350, 550))
                .into(gridViewHolder.imgPhoto);

        gridViewHolder.gridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id", getListRecipe().get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getListRecipe().size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder{

        ImageView imgPhoto;
        LinearLayout gridView;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            gridView = itemView.findViewById(R.id.grid_view);
        }
    }
}
