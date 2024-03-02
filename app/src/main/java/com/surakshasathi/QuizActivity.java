package com.surakshasathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.surakshasathi.databinding.ActivityQuizBinding;
import com.surakshasathi.databinding.ScoreDialogBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private static List<QuestionModel> questionModelList;
    private AlertDialog alertDialog;

    private static String time;

    private ActivityQuizBinding binding;
    ProgressBar pb;
    Button finishButton;

    private int currentQuestionIndex = 0;
    private String selectedAnswer = "";
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        finishButton = findViewById( R.id.finish_btn );
        questionModelList = new ArrayList<>();
        pb = findViewById( R.id.pb );




        getDataFromFirebase();

        time = "5";

        binding.btn0.setOnClickListener(this);
        binding.btn1.setOnClickListener(this);
        binding.btn2.setOnClickListener(this);
        binding.btn3.setOnClickListener(this);
        binding.nextBtn.setOnClickListener(this);



//        loadQuestions();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Code to be executed after the delay
                pb.setVisibility( View.GONE );
                startTimer();
            }
        }, 2000);





    }

    private void startTimer() {
        final long totalTimeInMillis = Integer.parseInt(time) * 60 * 1000L;
        new CountDownTimer(totalTimeInMillis, 1000L) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                long remainingSeconds = seconds % 60;
                binding.timerIndicatorTextview.setText(String.format("%02d:%02d", minutes, remainingSeconds));

            }

            @Override
            public void onFinish() {
                // Finish the quiz
                finishQuiz();
            }
        }.start();
    }

    private void loadQuestions() {
        selectedAnswer = "";

        if (currentQuestionIndex == questionModelList.size()) {

            finishQuiz();
            return;
        }

        binding.questionIndicatorTextview.setText("Question " + (currentQuestionIndex + 1) + "/" + questionModelList.size());
        binding.questionProgressIndicator.setProgress((int) ((float) currentQuestionIndex / questionModelList.size() * 100));
        binding.questionTextview.setText(questionModelList.get(currentQuestionIndex).getQuestion());
        binding.btn0.setText( questionModelList.get( currentQuestionIndex ).getOptions().get( 0 ) );
        binding.btn1.setText( questionModelList.get( currentQuestionIndex ).getOptions().get( 1 ) );
        binding.btn2.setText( questionModelList.get( currentQuestionIndex ).getOptions().get( 2 ) );
        binding.btn3.setText( questionModelList.get( currentQuestionIndex ).getOptions().get( 3 ) );
    }

    @Override
    public void onClick(View view) {
        binding.btn0.setBackgroundColor(getColor(R.color.gray));
        binding.btn1.setBackgroundColor(getColor(R.color.gray));
        binding.btn2.setBackgroundColor(getColor(R.color.gray));
        binding.btn3.setBackgroundColor(getColor(R.color.gray));

        Button clickedBtn = (Button) view;
        if (clickedBtn.getId() == R.id.next_btn) {
            // next button is clicked
            if (selectedAnswer.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please select an answer to continue", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedAnswer.equals(questionModelList.get(currentQuestionIndex).getCorrect())) {
                score++;
                Log.i("Score of quiz", String.valueOf(score));
            }
            currentQuestionIndex++;
            loadQuestions();
        } else {
            // options button is clicked
            selectedAnswer = clickedBtn.getText().toString();
            clickedBtn.setBackgroundColor(getColor(R.color.orange));
        }
    }



    private void finishQuiz() {
        int totalQuestions = questionModelList.size();
        int percentage = (int) (((float) score / totalQuestions) * 100);

        ScoreDialogBinding dialogBinding = ScoreDialogBinding.inflate(getLayoutInflater());
        dialogBinding.scoreProgressIndicator.setProgress(percentage);
        dialogBinding.scoreProgressText.setText(percentage + " %");

        if (percentage > 50) {
            dialogBinding.scoreTitle.setText("Congrats! You have passed");
            dialogBinding.scoreTitle.setTextColor(Color.BLUE);
        } else {
            dialogBinding.scoreTitle.setText("Oops! You have failed");
            dialogBinding.scoreTitle.setTextColor(Color.RED);
        }

        dialogBinding.scoreSubtitle.setText(score + " out of " + totalQuestions + " are correct");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogBinding.getRoot());
        builder.setCancelable(false);
        alertDialog = builder.create(); // Assign the created AlertDialog to the class variable
        alertDialog.show();

        // Get reference to the "Finish" button from the inflated view
        Button finishButton = dialogBinding.getRoot().findViewById(R.id.finish_btn);

        // Set OnClickListener for the "Finish" button
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the actions to perform when "Finish" button is clicked
                alertDialog.dismiss(); // Dismiss the AlertDialog
                // Add any additional actions you want to perform when the "Finish" button is clicked
                finish(); // Close the activity or navigate to another screen, if needed
            }
        });
    }


    private void getDataFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Quiz");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot questionSnapshot : dataSnapshot.getChildren()) {
                        QuestionModel questionModel = questionSnapshot.getValue(QuestionModel.class);
                        if (questionModel != null) {
                            questionModelList.add(questionModel);
                        }
                    }
                    // Now questionModelList should contain all QuestionModel instances
                    // Call loadQuestions here after fetching the data

                    Collections.shuffle(questionModelList);

                    // Pick the first five elements
                    questionModelList = questionModelList.subList(0, 10);
                    loadQuestions();
                } else {
                    // Handle the case where no data exists
                    Toast.makeText(QuizActivity.this, "No questions found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
                Toast.makeText(QuizActivity.this, "Error in fetching questions: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

