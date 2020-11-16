package rasoul.khalouie.dogsapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import rasoul.khalouie.dogsapp.R;
import rasoul.khalouie.dogsapp.model.DogBreed;

public class DogsListAdapter extends RecyclerView.Adapter<DogsListAdapter.DogsListViewHolder> {

    private ArrayList<DogBreed> dogsList;

    public DogsListAdapter(ArrayList<DogBreed> dogsList) {
        this.dogsList = dogsList;
    }

    public void updateDogsList(List<DogBreed> newDogsList) {
        this.dogsList.clear();
        this.dogsList.addAll(newDogsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DogsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dog, parent, false);
        return new DogsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogsListViewHolder holder, int position) {

        DogBreed dogBreed = dogsList.get(position);
        holder.txtDogName.setText(dogBreed.getDogBreed());
        holder.txtDogLifeSpan.setText(dogBreed.getLifeSpan());

    }

    @Override
    public int getItemCount() {
        return dogsList.size();
    }

    public class DogsListViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgDogImage;
        private TextView txtDogName, txtDogLifeSpan;

        public DogsListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgDogImage = itemView.findViewById(R.id.imgDogImage);
            this.txtDogName = itemView.findViewById(R.id.txtDogName);
            this.txtDogLifeSpan = itemView.findViewById(R.id.txtDogLifespan);
        }
    }
}
