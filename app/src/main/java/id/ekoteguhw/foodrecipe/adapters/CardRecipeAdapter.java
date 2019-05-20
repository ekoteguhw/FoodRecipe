package id.ekoteguhw.foodrecipe.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import id.ekoteguhw.foodrecipe.DetailActivity;
import id.ekoteguhw.foodrecipe.R;
import id.ekoteguhw.foodrecipe.models.Recipe;

public class CardRecipeAdapter extends RecyclerView.Adapter<CardRecipeAdapter.CardViewHolder> {

    private Context context;
    private ArrayList<Recipe> listRecipe;

    public CardRecipeAdapter(Context context) {
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
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card_recipe, viewGroup, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, final int position) {
        Recipe recipe = getListRecipe().get(position);
        Glide.with(context)
                .load(recipe.getPhoto())
                .apply(new RequestOptions().override(350, 550))
                .into(cardViewHolder.imgPhoto);
        cardViewHolder.tvName.setText(recipe.getName());
        cardViewHolder.tvTags.setText(recipe.getTags());
        cardViewHolder.btnFavorite.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, "Favorite "+ getListRecipe().get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        }));
        cardViewHolder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, "Share "+ getListRecipe().get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        }));
        cardViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
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

    public class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName, tvTags;
        Button btnFavorite, btnShare;
        CardView cardView;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvTags = itemView.findViewById(R.id.tv_item_tags);
            btnFavorite = itemView.findViewById(R.id.btn_set_favorite);
            btnShare = itemView.findViewById(R.id.btn_set_share);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
