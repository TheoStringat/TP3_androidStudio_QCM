package com.example.tp3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private CheckBox checkBoxNone;
    private Questionnaire qcm;
    private int questionNumber = 0;
    private Button validerChoix;
    private TextView numQuestion;
    private TextView contenuQuestion;
    private ArrayList<Double> tableauScores;
    private static ArrayList<ArrayList<Boolean>> listRepUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableauScores = new ArrayList<>();
        listRepUser = new ArrayList<>();

        qcm = Questionnaire.getInstance();

        checkBox1 = (CheckBox)findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox)findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox)findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox)findViewById(R.id.checkBox4);
        checkBoxNone = (CheckBox) findViewById(R.id.checkBoxNoChoice);
        validerChoix = (Button) findViewById(R.id.button_validation);
        numQuestion = (TextView)findViewById(R.id.num_question);
        contenuQuestion = (TextView)findViewById(R.id.contenu_question);

        updateQuestion(questionNumber);

        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Si une des 4 premières CheckBox est cochée, décoche la 5ème
                    checkBoxNone.setChecked(false);
                }
                // si aucune des 4 premières CheckBox n'est cochée, coche la 5ème
                if(!checkBox1.isChecked() && !checkBox2.isChecked() && !checkBox3.isChecked() && !checkBox4.isChecked()) {
                    checkBoxNone.setChecked(true);
                }
            }
        };

        checkBox1.setOnCheckedChangeListener(listener);
        checkBox2.setOnCheckedChangeListener(listener);
        checkBox3.setOnCheckedChangeListener(listener);
        checkBox4.setOnCheckedChangeListener(listener);

        checkBoxNone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Si la 5ème CheckBox est cochée, décoche les 4 premières
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                }
            }
        });

        validerChoix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean[] tabRes = new boolean[4];
                tabRes[0] = checkBox1.isChecked();
                tabRes[1] = checkBox2.isChecked();
                tabRes[2] = checkBox3.isChecked();
                tabRes[3] = checkBox4.isChecked();

                //on remplit la liste de réponse de l'utilisateur utilisée pour l'affichage des résultats
                ArrayList<Boolean> repUser = new ArrayList<>();
                repUser.add(checkBox1.isChecked());
                repUser.add(checkBox2.isChecked());
                repUser.add(checkBox3.isChecked());
                repUser.add(checkBox4.isChecked());
                listRepUser.add(repUser);

                // On stock le score de la question dans le tableau de score
                tableauScores.add(qcm.get(questionNumber).verify(tabRes));

                // On crée la boite de dialogue
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Voulez-vous passez à la question suivante ?");
                alert.setMessage("Score : " + qcm.get(questionNumber).verify(tabRes));
                alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //on met à jour le tableau de score
                        tableauScores.add(qcm.get(questionNumber).verify(tabRes));

                        // on remplit le score de la liste de score
                        qcm.setScore(questionNumber, qcm.get(questionNumber).verify(tabRes));

                        questionNumber ++;
                        updateQuestion(questionNumber);
                    }
                });
                alert.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alert.show();
            }
        });
    }

    public void updateQuestion(int num) {
        //si le nombre de question dépasse le nombre de question du questionnaire on lance l'activité recap
        if (num >= qcm.size()) {
            Intent intent = new Intent(MainActivity.this, RecapActivity.class);
            startActivity(intent);
            return;
        }
        numQuestion.setText("Question n°" + (num+1));
        contenuQuestion.setText(qcm.get(num).question);
        checkBox1.setText(qcm.get(num).propositions[0]);
        checkBox2.setText(qcm.get(num).propositions[1]);
        checkBox3.setText(qcm.get(num).propositions[2]);
        checkBox4.setText(qcm.get(num).propositions[3]);

        checkBoxNone.setChecked(true);
    }

    //getter tabRepUser
    public static ArrayList<ArrayList<Boolean>> getListRepUser() {
        return listRepUser;
    }
}
