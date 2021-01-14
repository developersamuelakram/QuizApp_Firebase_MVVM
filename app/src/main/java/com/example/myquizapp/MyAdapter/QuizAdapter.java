package com.example.myquizapp.MyAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.example.myquizapp.Model.QuizModel;
import com.example.myquizapp.R;

import java.util.List;


public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.MyHolder> {

    List<QuizModel> quizModelList;

    OnItemClicked itemClicked;

    public void setQuizModelData(List<QuizModel> quizModelData) {
        this.quizModelList = quizModelData;
    }

    public QuizAdapter(OnItemClicked itemClicked) {

        this.itemClicked = itemClicked;
    }



    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.startlayout, parent, false);
        return new MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {


        holder.quiztitle.setText(quizModelList.get(position).getQuizname());
        holder.desc.setText(quizModelList.get(position).getDesc());
        holder.level.setText(quizModelList.get(position).getLevel());

        Glide.with(holder.itemView.getContext()).load(quizModelList.get(position).getImage())
                .placeholder(R.drawable.placeholder_image).centerCrop().into(holder.imageView);

    }

    @Override
    public int getItemCount() {

        if (quizModelList == null) {

            return 0;
        } else {

            return  quizModelList.size();
        }


    }

    class MyHolder extends ViewHolder implements View.OnClickListener {



        TextView desc, quiztitle, level;
        ImageView imageView;
        Button button;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            desc = itemView.findViewById(R.id.listquizdesc);
            quiztitle = itemView.findViewById(R.id.layoutquiztitle);
            level = itemView.findViewById(R.id.listlevel);
            imageView = itemView.findViewById(R.id.listimage);
            button = itemView.findViewById(R.id.takequizBtn);

            button.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            itemClicked.somethingClicked(getAdapterPosition());
        }



    }

    public interface OnItemClicked {


        void somethingClicked(int position);

    }
}
