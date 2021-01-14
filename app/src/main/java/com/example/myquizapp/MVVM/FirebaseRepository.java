package com.example.myquizapp.MVVM;

import androidx.annotation.NonNull;

import com.example.myquizapp.Model.QuizModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FirebaseRepository {


    OnFireStoreDataAdded fireStoreDataAdded;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    Query quizRef = firestore.collection("QuizList");


    public FirebaseRepository(OnFireStoreDataAdded fireStoreDataAdded) {
        this.fireStoreDataAdded = fireStoreDataAdded;
    }

    public void getDataFromFireStore() {

        quizRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    fireStoreDataAdded.quizDataAdded(task.getResult().toObjects(QuizModel.class));

                } else {

                    fireStoreDataAdded.OnError(task.getException());
                }
            }
        });



    }

    public interface OnFireStoreDataAdded {
        void quizDataAdded(List<QuizModel> quizModelList);
        void OnError(Exception e);

    }



}
