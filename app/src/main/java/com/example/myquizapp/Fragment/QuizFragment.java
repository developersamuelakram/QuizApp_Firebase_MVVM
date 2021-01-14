package com.example.myquizapp.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myquizapp.Model.QuestionModel;
import com.example.myquizapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuizFragment extends Fragment implements View.OnClickListener{

    NavController navController;
    int position;
    String quizid;
    String currentUserId;
    String quizName;
    Long totalNumberOfQuestions= 0L;

    List<QuestionModel> allQuestionList = new ArrayList<>();
    List<QuestionModel> questionToAnswer = new ArrayList<>();

    Button optiona, optionb, optionc, nextButton;
    TextView questionText, quizTitleText, questionTimeProgressText, answerFeedbackText;
    ImageButton backbutton;


    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    int correctAnswer =0;
    int wrongAnswer =0;
    int missedAnswer =0;

    int currentQuestion = 0;
    

    boolean canAnswer = false;
    CountDownTimer countDownTimer;






    public QuizFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.quizfrag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        // UI initialization
        questionText = view.findViewById(R.id.quizquestion);
        quizTitleText = view.findViewById(R.id.quiztitlequiz);
        questionTimeProgressText = view.findViewById(R.id.timertext);
        answerFeedbackText = view.findViewById(R.id.quizanswerfeedback);
        progressBar = view.findViewById(R.id.quizProgressBar);

        optiona = view.findViewById(R.id.optiona);
        optionb = view.findViewById(R.id.optionb);
        optionc = view.findViewById(R.id.optionc);
        nextButton = view.findViewById(R.id.quiznext);
        backbutton = view.findViewById(R.id.cancelbutton);


        // updating the variables

        quizid = QuizFragmentArgs.fromBundle(getArguments()).getQuizid();
        totalNumberOfQuestions = QuizFragmentArgs.fromBundle(getArguments()).getTotalNumberOfQuestion();
        quizName = QuizFragmentArgs.fromBundle(getArguments()).getQuizname();


        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user!= null) {


            currentUserId = user.getUid();



        } else {


            // go back to homefragment
        }


        firebaseFirestore.collection("QuizList")
                .document(quizid).collection("Questions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    allQuestionList = task.getResult().toObjects(QuestionModel.class);
                    pickedQuestion();
                    LoadUi();

                } else {

                    quizTitleText.setText(task.getException().toString());
                }

            }
        });


        optiona.setOnClickListener(this);
        optionb.setOnClickListener(this);
        optionc.setOnClickListener(this);
        nextButton.setOnClickListener(this);




    }


    private void pickedQuestion() {

        for (int i=0; i<totalNumberOfQuestions; i++) {

            int randomNumber = getRandomInt(0, allQuestionList.size());
            questionToAnswer.add(allQuestionList.get(randomNumber));
            allQuestionList.remove(randomNumber);

        }
    }

    private int getRandomInt(int min, int max) {
        return ((int) (Math.random()*(max-min))) + min;

    }


    private void LoadUi() {

        quizTitleText.setText(quizName);
        questionText.setText("Loading Question");
        
        EnableOptions();
        LoadQuestion(1);


    }

    private void LoadQuestion(int questNum) {

        questionText.setText(questNum +". " + questionToAnswer.get(questNum -1).getQuestion());
        optiona.setText(questionToAnswer.get(questNum -1).getOptiona());
        optionb.setText(questionToAnswer.get(questNum -1).getOptionb());
        optionc.setText(questionToAnswer.get(questNum -1).getOptionc());

        canAnswer = true;
        currentQuestion = questNum;
        startTimer(questNum);




    }

    private void startTimer(int questNum) {

        Long timeToAnswer = questionToAnswer.get(questNum -1).getTimer();
        questionTimeProgressText.setText(timeToAnswer.toString());
        progressBar.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(timeToAnswer*1000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {

                questionTimeProgressText.setText(millisUntilFinished/1000 +"");
                Long percent = millisUntilFinished/(timeToAnswer*10);
                progressBar.setProgress(percent.intValue());

                
            }

            @Override
            public void onFinish() {
                
                canAnswer = false;
                answerFeedbackText.setText("Time Up Loser");
                answerFeedbackText.setTextColor(getResources().getColor(R.color.red));
                missedAnswer++;
                showNextButton();
                

            }
        };


        countDownTimer.start();



    }


    private void EnableOptions() {

        optiona.setVisibility(View.VISIBLE);
        optionb.setVisibility(View.VISIBLE);
        optionc.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.INVISIBLE);
        nextButton.setEnabled(false);

        optiona.setEnabled(true);
        optionb.setEnabled(true);
        optionc.setEnabled(true);



    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.optiona:
                verifyAnswer(optiona);
                break;

            case R.id.optionb:
                verifyAnswer(optionb);
                break;

            case R.id.optionc:
                verifyAnswer(optionc);
                break;


            case R.id.quiznext:

                // if you have completed the quiz


                if (currentQuestion == totalNumberOfQuestions) {

                    submitResult();

                } else {

                    currentQuestion++;
                    LoadQuestion(currentQuestion);
                    reseOptions();
                }



                break;





        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void reseOptions() {


        optiona.setBackground(getResources().getDrawable(R.drawable.outline_light_btn_bg, null));
        optionb.setBackground(getResources().getDrawable(R.drawable.outline_light_btn_bg, null));
        optionc.setBackground(getResources().getDrawable(R.drawable.outline_light_btn_bg, null));

        optiona.setTextColor(getResources().getColor(R.color.white, null));
        optionb.setTextColor(getResources().getColor(R.color.white, null));
        optionc.setTextColor(getResources().getColor(R.color.white, null));





        answerFeedbackText.setVisibility(View.INVISIBLE);
        nextButton.setVisibility(View.INVISIBLE);
        nextButton.setEnabled(false);
    }

    private void submitResult() {

        HashMap<String , Object>  hashMap = new HashMap<>();

        hashMap.put("correct", correctAnswer);
        hashMap.put("wrong", wrongAnswer);
        hashMap.put("missed", missedAnswer);

        firebaseFirestore.collection("QuizList")
                .document(quizid)
                .collection("Results")
                .document(currentUserId)
                .set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {



                    QuizFragmentDirections.ActionQuizFragmentToResultFragment
                            actions = QuizFragmentDirections.actionQuizFragmentToResultFragment();

                    actions.setQuizid(quizid);
                    actions.setUserId(currentUserId);
                    navController.navigate(actions);


                } else {

                    answerFeedbackText.setText(task.getException().toString());
                }

            }
        });





    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void verifyAnswer(Button selectedButton) {

        if (canAnswer) {

            selectedButton.setTextColor(getResources().getColor(R.color.white, null));


            if (questionToAnswer.get(currentQuestion -1).getAnswer().equals(selectedButton.getText())) {

                correctAnswer++;

                selectedButton.setBackgroundColor(getResources().getColor(R.color.pink, null));
                answerFeedbackText.setText("Answer is Correct");
                answerFeedbackText.setTextColor(getResources().getColor(R.color.white, null));


            } else {

                selectedButton.setBackgroundColor(getResources().getColor(R.color.red, null));

                wrongAnswer++;
                answerFeedbackText.setText("incorrect");
                answerFeedbackText.setTextColor(getResources().getColor(R.color.red, null));



            }

            canAnswer = false;

            countDownTimer.cancel();
            showNextButton();






        }




    }


    private void showNextButton() {

        // if you have completed the quiz
        if (currentQuestion == totalNumberOfQuestions) {
            nextButton.setText("Go to Results");


        }

        answerFeedbackText.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.VISIBLE);
        nextButton.setEnabled(true);

    }
}

