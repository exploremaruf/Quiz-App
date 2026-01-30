package com.maruf.quizapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // UI Components
    private TextView tvQuestion, tvScore, tvCount;
    private Button btn1, btn2, btn3, btn4, btnNext;

    // Data Variables
    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Edge-to-Edge Padding Logic
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ১. UI আইডি গুলোর সাথে পরিচয় (Initialize)
        tvQuestion = findViewById(R.id.tv_question);
        tvScore = findViewById(R.id.tv_score);
        tvCount = findViewById(R.id.tv_question_count);
        btn1 = findViewById(R.id.btn_option1);
        btn2 = findViewById(R.id.btn_option2);
        btn3 = findViewById(R.id.btn_option3);
        btn4 = findViewById(R.id.btn_option4);
        btnNext = findViewById(R.id.btn_next);

        // ২. প্রশ্ন লোড করা
        loadQuestions();
        displayQuestion();

        // ৩. অপশন বাটন ক্লিক লজিক
        View.OnClickListener optionClickListener = v -> {
            Button clickedButton = (Button) v;
            checkAnswer(clickedButton);
        };

        btn1.setOnClickListener(optionClickListener);
        btn2.setOnClickListener(optionClickListener);
        btn3.setOnClickListener(optionClickListener);
        btn4.setOnClickListener(optionClickListener);

        // ৪. নেক্সট বাটন ক্লিক লজিক
        btnNext.setOnClickListener(v -> {
            currentQuestionIndex++;
            if (currentQuestionIndex < questionList.size()) {
                displayQuestion();
            } else {
                Toast.makeText(this, "Quiz Completed! Score: " + score, Toast.LENGTH_LONG).show();
                btnNext.setEnabled(false);
                btnNext.setText("Finished");
            }
        });
    }

    private void loadQuestions() {
        questionList = new ArrayList<>();
        // এখানে আপনি নিজের মতো প্রশ্ন যোগ করতে পারেন
        questionList.add(new Question("What is the capital of Bangladesh?", "Dhaka", "Sylhet", "Khulna", "Chittagong", 0));
        questionList.add(new Question("Which programming language is used for Android?", "Swift", "Java", "C#", "Python", 1));
        questionList.add(new Question("Who is the developer of Android?", "Apple", "Microsoft", "Google", "Nokia", 2));
    }

    private void displayQuestion() {
        Question q = questionList.get(currentQuestionIndex);
        tvQuestion.setText(q.getQuestionText());
        btn1.setText(q.getOption1());
        btn2.setText(q.getOption2());
        btn3.setText(q.getOption3());
        btn4.setText(q.getOption4());
        tvCount.setText("Question: " + (currentQuestionIndex + 1) + "/" + questionList.size());
    }

    private void checkAnswer(Button clickedButton) {
        int selectedIndex = -1;
        if (clickedButton.getId() == R.id.btn_option1) selectedIndex = 0;
        else if (clickedButton.getId() == R.id.btn_option2) selectedIndex = 1;
        else if (clickedButton.getId() == R.id.btn_option3) selectedIndex = 2;
        else if (clickedButton.getId() == R.id.btn_option4) selectedIndex = 3;

        if (selectedIndex == questionList.get(currentQuestionIndex).getCorrectAnswerIndex()) {
            score++;
            tvScore.setText("Score: " + score);
            Toast.makeText(this, "Correct Answer!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong Answer!", Toast.LENGTH_SHORT).show();
        }
    }
}