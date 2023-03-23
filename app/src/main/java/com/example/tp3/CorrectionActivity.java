package com.example.tp3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CorrectionActivity extends AppCompatActivity {

    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private CheckBox checkBoxNone;
    private Questionnaire qcm;
    private int questionNumber = 0;
    private TextView numQuestion;
    private TextView contenuQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correction);

        qcm = Questionnaire.getInstance();
        checkBox1 = (CheckBox)findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox)findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox)findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox)findViewById(R.id.checkBox4);
        checkBoxNone = (CheckBox) findViewById(R.id.checkBoxNoChoice);
        numQuestion = (TextView)findViewById(R.id.num_question);
        contenuQuestion = (TextView)findViewById(R.id.contenu_question);

        // Création d'un Intent pour récupérer les données de l'activité de la question
        Intent intent = getIntent();
        questionNumber = intent.getIntExtra("questionNumber", 0);

        getChoicesQuestion(questionNumber);

        //mettre fond vert si bonne reponse
        CheckBox tabCheckBox[] = {checkBox1, checkBox2, checkBox3, checkBox4, checkBoxNone};
        int goodAnswer = 0;
        for (int i = 0; i < tabCheckBox.length - 1; i++) {
            if (qcm.get(questionNumber).getSolutions()[i] == true) {
                goodAnswer++;
                tabCheckBox[i].setBackgroundColor(Color.GREEN);
            }
            else {
                tabCheckBox[i].setBackgroundColor(Color.RED);
            }
        }
        if(goodAnswer == 0) {
            tabCheckBox[4].setBackgroundColor(Color.GREEN);
        }
        else {
            tabCheckBox[4].setBackgroundColor(Color.RED);
        }
        // cocher les reponses de l'utilisateur
        checkRepQuestion(questionNumber);
    }

    private void getChoicesQuestion(int num) {
        numQuestion.setText("Question n°" + (num+1));
        contenuQuestion.setText(qcm.get(num).question);
        checkBox1.setText(qcm.get(num).propositions[0]);
        checkBox2.setText(qcm.get(num).propositions[1]);
        checkBox3.setText(qcm.get(num).propositions[2]);
        checkBox4.setText(qcm.get(num).propositions[3]);
    }

    private void checkRepQuestion(int num) {
        ArrayList<ArrayList<Boolean>> listRepUser;
        listRepUser = MainActivity.getListRepUser();

        CheckBox tabCheckBox[] = {checkBox1, checkBox2, checkBox3, checkBox4};
        for (int i = 0; i < tabCheckBox.length; i++) {
            if (listRepUser.get(num).get(i) == true) {
                tabCheckBox[i].setChecked(true);
            }
        }
    }
}