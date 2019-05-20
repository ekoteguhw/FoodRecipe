package id.ekoteguhw.foodrecipe.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import id.ekoteguhw.foodrecipe.DetailActivity;
import id.ekoteguhw.foodrecipe.R;
import id.ekoteguhw.foodrecipe.models.Recipe;

public class ListRecipeAdapter extends RecyclerView.Adapter<ListRecipeAdapter.CategoryViewHolder> {

    private Context context;
    private ArrayList<Recipe> listRecipe;

    public ListRecipeAdapter(Context context) {
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
    public ListRecipeAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {

        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_recipe, viewGroup, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListRecipeAdapter.CategoryViewHolder categoryViewHolder, final int position) {
        categoryViewHolder.tvName.setText(getListRecipe().get(position).getName());
        categoryViewHolder.tvTags.setText(getListRecipe().get(position).getTags());
        Glide.with(context).load(getListRecipe().get(position).getPhoto())
                .apply(new RequestOptions().override(55,55))
                .into(categoryViewHolder.imgPhoto);

        categoryViewHolder.listView.setOnClickListener(new View.OnClickListener() {
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

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvTags;
        ImageView imgPhoto;
        RelativeLayout listView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_item_name);
            tvTags = itemView.findViewById(R.id.tv_item_tags);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            listView = itemView.findViewById(R.id.list_view);
        }
    }
}
