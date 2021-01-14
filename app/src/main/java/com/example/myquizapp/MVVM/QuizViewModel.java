package com.example.myquizapp.MVVM;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myquizapp.Model.QuizModel;

import java.util.List;

public class QuizViewModel  extends ViewModel implements FirebaseRepository.OnFireStoreDataAdded {


    MutableLiveData<List<QuizModel>> quizModelListData = new MutableLiveData<>();


    FirebaseRepository firebaseRepo = new FirebaseRepository(this);

    public QuizViewModel(){
        firebaseRepo.getDataFromFireStore();
    }

    public LiveData<List<QuizModel>> getLiveDatafromFireStore() {
        return  quizModelListData;
    }

    @Override
    public void quizDataAdded(List<QuizModel> quizModelList) {
        quizModelListData.setValue(quizModelList);
    }

    @Override
    public void OnError(Exception e) {

    }
}
