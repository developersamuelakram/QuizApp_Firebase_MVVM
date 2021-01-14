package com.example.myquizapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.example.myquizapp.MVVM.QuizViewModel;
import com.example.myquizapp.Model.QuizModel;
import com.example.myquizapp.MyAdapter.QuizAdapter;
import com.example.myquizapp.R;

import java.util.List;

public class ListFragment extends Fragment implements QuizAdapter.OnItemClicked {


    NavController navController;
    RecyclerView recyclerView;
    QuizAdapter mAdapter;
    ProgressBar progressBar;
    Animation fadein;
    Animation fadeout;
    QuizViewModel viewModel;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.listfragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        navController = Navigation.findNavController(view);
        recyclerView = view.findViewById(R.id.recyclerview);
        progressBar = view.findViewById(R.id.listprogressbar);

        fadein = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        fadeout= AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);

        mAdapter = new QuizAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);




    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(QuizViewModel.class);
        viewModel.getLiveDatafromFireStore().observe(getViewLifecycleOwner(), new Observer<List<QuizModel>>() {
            @Override
            public void onChanged(List<QuizModel> quizModels) {

                mAdapter.setQuizModelData(quizModels);
                mAdapter.notifyDataSetChanged();
                recyclerView.setAnimation(fadein);
                progressBar.setAnimation(fadeout);


            }
        });
    }

    @Override
    public void somethingClicked(int position) {

        ListFragmentDirections.ActionListFragmentToDetailFragment action = ListFragmentDirections.actionListFragmentToDetailFragment();
        action.setPosition(position);
        navController.navigate(action);

    }
}