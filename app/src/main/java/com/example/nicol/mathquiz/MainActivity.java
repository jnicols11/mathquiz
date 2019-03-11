package com.example.nicol.mathquiz;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn_start, btn_ans0, btn_ans1, btn_ans2, btn_ans3;
    TextView tv_score, tv_question, tv_timer, tv_bottom;
    ProgressBar prog_timer;

    Game g = new Game();

    int secondsRemaining = 30;

    CountDownTimer timer = new CountDownTimer(30000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            secondsRemaining--;
            tv_timer.setText(Integer.toString(secondsRemaining) + " sec");
            prog_timer.setProgress(30 - secondsRemaining);
        }

        @Override
        public void onFinish() {
            btn_ans0.setEnabled(false);
            btn_ans1.setEnabled(false);
            btn_ans2.setEnabled(false);
            btn_ans3.setEnabled(false);
            tv_bottom.setText("Time is up! " + g.getNumberCorrect() + "/" + (g.getTotalQuestions() - 1));

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btn_start.setVisibility(View.VISIBLE);
                }
            }, 4000);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = findViewById(R.id.btn_start);
        btn_ans0 = findViewById(R.id.btn_ans0);
        btn_ans1 = findViewById(R.id.btn_ans1);
        btn_ans2 = findViewById(R.id.btn_ans2);
        btn_ans3 = findViewById(R.id.btn_ans3);

        tv_score = findViewById(R.id.tv_score);
        tv_question = findViewById(R.id.tv_question);
        tv_timer = findViewById(R.id.tv_timer);
        tv_bottom = findViewById(R.id.tv_bottom);

        prog_timer = findViewById(R.id.prog_timer);

        tv_timer.setText("0sec");
        tv_question.setText("");
        tv_bottom.setText("Press go");
        tv_score.setText("0pts");

        View.OnClickListener startButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button start_button = (Button) v;

                start_button.setVisibility(View.INVISIBLE);
                secondsRemaining = 30;
                tv_score.setText("0pts");
                g = new Game();
                nextTurn();
                timer.start();

            }
        };

        View.OnClickListener answerButtonClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Button buttonClicked = (Button) v;

                int answerSelected = Integer.parseInt(buttonClicked.getText().toString());

                g.checkAnswer(answerSelected);
                tv_score.setText(Integer.toString(g.getScore()));
                nextTurn();


            }
        };

        btn_start.setOnClickListener(startButtonClickListener);

        btn_ans0.setOnClickListener(answerButtonClickListener);
        btn_ans1.setOnClickListener(answerButtonClickListener);
        btn_ans2.setOnClickListener(answerButtonClickListener);
        btn_ans3.setOnClickListener(answerButtonClickListener);

    }

    private void nextTurn(){
        //create a new question
        g.makeNewQuestion();
        int[] answers = g.getCurrentQuestion().getAnswerArray();
        //set text on answer buttons
        btn_ans0.setText(Integer.toString(answers[0]));
        btn_ans1.setText(Integer.toString(answers[1]));
        btn_ans2.setText(Integer.toString(answers[2]));
        btn_ans3.setText(Integer.toString(answers[3]));

        btn_ans0.setEnabled(true);
        btn_ans1.setEnabled(true);
        btn_ans2.setEnabled(true);
        btn_ans3.setEnabled(true);

        tv_question.setText(g.getCurrentQuestion().getQuestionPhrase());
        //enable answer button
        tv_bottom.setText(g.getNumberCorrect() + "/" + (g.getTotalQuestions() - 1));
        //start the timer

    }
}
