package com.hamoudi.j.classroomcommunity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class QuizzActivity extends AppCompatActivity {

    private List<Button> buttons = new ArrayList<>();
    private List<Question> questions;

    private ProgressBar progressBar;
    private TextView timeRemaining;

    private ImageView firstPlayerPhoto;
    private TextView firstPlayerName;
    private TextView firstPlayerScore;

    private int firstPlayerScoreGame;
    private int secondPlayerScoreGame;

    private TextView secondPlayerPhoto;
    private TextView secondPlayerName;
    private TextView secondPlayerScore;

    private TextView question;
    private Question randomQuestion;

    private int timeElapsed = 10;
    private int progress = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);

        ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
        findButtons(viewGroup, buttons);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        firstPlayerPhoto = (ImageView) findViewById(R.id.imgAdv1);
        firstPlayerName = (TextView) findViewById(R.id.nameAdv1);
        firstPlayerScore = (TextView) findViewById(R.id.scoreAdv1);

        secondPlayerPhoto = (TextView) findViewById(R.id.imgAdv2);
        secondPlayerName = (TextView) findViewById(R.id.nameAdv2);
        secondPlayerScore = (TextView) findViewById(R.id.scoreAdv2);

        question = (TextView) findViewById(R.id.question);

        timeRemaining = (TextView) findViewById(R.id.timeRemaining);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        secondPlayerPhoto.setText(bundle.getString("secondPlayerPhoto"));
        secondPlayerName.setText(bundle.getString("secondPlayer"));

        fetchQuestions();
        //pickAQuestion();

        new Thread(new Runnable() {
            public void run() {
                while (progress < 10) {
                    progress++;
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progress);
                            timeElapsed--;
                            timeRemaining.setText(Integer.toString(timeElapsed));
                            if(progress == 10) {
                            }
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    private void fetchQuestions() {
        Ion.with(getApplicationContext())
                .load(MainActivity.questionsUrl
                        +"/"+ MainActivity.QRCode)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> response) {
                        if(response.getHeaders().code() == 200)
                        {
                            String json = response.getResult();
                            questions = Question.getQuestionsFromJson(json);
                            Log.d("TAG", "Taille : " + questions.size());
                            pickAQuestion();

                        } else {
                            Toast.makeText(getApplicationContext(), "Erreur" , Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "Nope, erreur");
                        }

                    }

                });

    }

    public void pickAQuestion() {
        Random random = new Random();
        randomQuestion = questions.get(random.nextInt(questions.size()));
        ArrayList<Answer> answers = (ArrayList<Answer>) randomQuestion.getAnswers();
        question.setText(randomQuestion.getLabel());
        for(int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText(answers.get(i).getLabel());
        }
    }

    /**
     * Allows to check if the selected answer is right or wrong
     * @param view
     */
    public void checkAnswer(View view) {
        Button button = (Button) view;
        Toast.makeText(getApplicationContext(), ((Button) view).getText(), Toast.LENGTH_SHORT).show();
        if(button.getText().toString().equals(randomQuestion.getRightAnswer().getLabel())) {
            button.setBackgroundColor(getResources().getColor(R.color.green));
            for(Button answer : buttons) {
                if (!answer.getText().toString().equals(randomQuestion.getRightAnswer().getLabel())) {
                    answer.setAlpha((float) 0.10);
                }
                answer.setOnClickListener(null);
            }
            firstPlayerScoreGame += 5;
            firstPlayerScore.setText(String.valueOf(firstPlayerScoreGame));

        } else {
            button.setBackgroundResource(R.color.orange);
            for(Button answer : buttons) {
                if(answer.getText().toString().equals(randomQuestion.getRightAnswer().getLabel())) {
                    answer.setBackgroundResource(R.color.green);
                }
                answer.setOnClickListener(null);
            }
        }
        //pickAQuestion();*/

    }

    /**
     * Find answer buttons in the view
     * @param viewGroup View wich contains answer buttons to the question
     * @param buttons Answer buttons list
     */
    private void findButtons(ViewGroup viewGroup, List<Button> buttons) {
        for (int i = 0, N = viewGroup.getChildCount(); i < N; i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup) {
                findButtons((ViewGroup) child, buttons);
            } else if (child instanceof Button) {
                buttons.add((Button) child);
            }
        }
    }

}
