package rasoul.khalouie.dogsapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import rasoul.khalouie.dogsapp.R;

import rasoul.khalouie.dogsapp.databinding.LayoutItemDogBinding;
import rasoul.khalouie.dogsapp.model.DogBreed;
import rasoul.khalouie.dogsapp.view.ListFragmentDirections;

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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        LayoutItemDogBinding view = DataBindingUtil.inflate(inflater, R.layout.layout_item_dog, parent, false);
        return new DogsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogsListViewHolder holder, int position) {

        DogBreed dogBreed = dogsList.get(position);
        holder.itemDogBinding.setDog(dogBreed);
//        holder.txtDogName.setText(dogBreed.getDogBreed());
//        holder.txtDogLifeSpan.setText(dogBreed.getLifeSpan());
//
//        Util.loadImage(holder.itemDogBinding.imgDogImage, dogBreed.getImageUrl(), Util.getCircularProgressDrawable(holder.itemDogBinding.imgDogImage.getContext()));
//
//        holder.itemView.setOnClickListener(view -> {
//            ListFragmentDirections.DetailsAction action = ListFragmentDirections.detailsAction();
//            action.setDogUId(dogBreed.getUuid());
//            Navigation.findNavController(holder.itemView).navigate(action);
//        });
    }

    @Override
    public int getItemCount() {
        return dogsList.size();
    }

    public class DogsListViewHolder extends RecyclerView.ViewHolder {

        private LayoutItemDogBinding itemDogBinding;

        public DogsListViewHolder(@NonNull LayoutItemDogBinding itemView) {
            super(itemView.getRoot());
            this.itemDogBinding = itemView;

            itemDogBinding.getRoot().setOnClickListener(view -> {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {

                    DogBreed dogBreed = dogsList.get(position);
                    ListFragmentDirections.DetailsAction action = ListFragmentDirections.detailsAction();
                    action.setDogUId(dogBreed.getUuid());
                    Navigation.findNavController(view).navigate(action);

                }
            });
        }
    }
}
