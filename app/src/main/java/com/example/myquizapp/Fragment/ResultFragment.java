package com.example.myquizapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myquizapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ResultFragment extends Fragment implements View.OnClickListener {

    NavController navController;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    TextView correctanwers, wronganswers, percentage, missedanswers;
    ProgressBar progressBar;
    Button button;


    String quizid, currentUserId;



    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.resultfrag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        correctanwers = view.findViewById(R.id.results_correct_text);
        wronganswers = view.findViewById(R.id.results_wrong_text);
        percentage = view.findViewById(R.id.results_percent);
        progressBar = view.findViewById(R.id.results_progress);
        missedanswers = view.findViewById(R.id.results_missed_text);
        button = view.findViewById(R.id.results_home_btn);


        quizid = ResultFragmentArgs.fromBundle(getArguments()).getQuizid();
        currentUserId = ResultFragmentArgs.fromBundle(getArguments()).getUserId();


        button.setOnClickListener(this);


        firebaseFirestore.collection("QuizList")
                .document(quizid)
                .collection("Results")
                .document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                if (task.isSuccessful()) {


                    DocumentSnapshot snapshot = task.getResult();

                    Long answerofcorrect = snapshot.getLong("correct");
                    Long incorrectanswers = snapshot.getLong("wrong");
                    Long missedquestions = snapshot.getLong("missed");


                    Long total = answerofcorrect + incorrectanswers + missedquestions;
                    Long percent = (answerofcorrect*100)/total;




                    correctanwers.setText(answerofcorrect.toString());
                    wronganswers.setText(incorrectanswers.toString());
                    missedanswers.setText(missedquestions.toString());



                    percentage.setText(percent.toString() +"%");
                    progressBar.setProgress(percent.intValue());






                } else {

                    percentage.setText(task.getException().toString());
                }


            }
        });


    }

    @Override
    public void onClick(View v) {

        navController.navigate(R.id.action_resultFragment_to_listFragment);
    }
}