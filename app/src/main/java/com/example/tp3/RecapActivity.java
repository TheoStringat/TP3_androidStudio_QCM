package com.example.tp3;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
public class RecapActivity extends AppCompatActivity {

    private Questionnaire qcm;
    private ArrayList<String> resultatsQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recap);

        resultatsQuestion = new ArrayList<>();
        qcm = Questionnaire.getInstance();

        // Récupération des résultats des questions
        putListeResultats();


        // Création d'un adapter ArrayAdapter<String> pour afficher les scores dans le listView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resultatsQuestion);

        // Récupération du listView et ajout de l'adapter
        ListView listView = (ListView) findViewById(R.id.listView_notes);
        listView.setAdapter(adapter);

        // Affichage de la note totale
        putNoteTotale();

        // Création d'un listener pour afficher la correction de la question sélectionnée
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent1 = new Intent(RecapActivity.this, CorrectionActivity.class);
            intent1.putExtra("questionNumber", position);
            startActivity(intent1);
        });
    }

    private void putListeResultats() {
        for (int i = 0; i < qcm.size(); i++) {
            resultatsQuestion.add("Question " + (i + 1) + " , score = " + qcm.getScore(i));
        }
    }

    private void putNoteTotale() {
        TextView noteTotale = (TextView) findViewById(R.id.textView_totalNotes);

        double moyenne = 0;
        double somme = 0;

        for (int i = 0; i < qcm.size(); i++) {
            somme += qcm.getScore(i);
        }

        moyenne = somme / qcm.size();
        noteTotale.setText("Note totale : " + moyenne * 20 + "/20");
    }
}
