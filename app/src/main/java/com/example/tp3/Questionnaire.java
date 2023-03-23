package com.example.tp3;

import java.util.ArrayList;
import java.util.List;

public class Questionnaire {

    /** Instance unique pré-initialisée */
    private static Questionnaire instance = new Questionnaire();
    private ArrayList<Question> questionList;
    private List<Double> listScore;

    private Questionnaire() {
        questionList = new ArrayList<>();
        listScore = new ArrayList<>();
        initializeQuestions();
    }

    /** Point d'accès pour l'instance unique du singleton */
    public static Questionnaire getInstance() {
        if (instance == null) {
            instance = new Questionnaire();
        }
        return instance;
    }

    public void initializeQuestions() {
        Question q1;
        Question q2;
        Question q3;
        Question q4;
        Question q5;
        Question q6;

        questionList.add(
                q1 = new Question("Quel est le plus grand pays du monde ?")
        );
        questionList.add(
                q2 = new Question("Quelle est la hauteur de la tour Eiffel ?")
        );
        questionList.add(
                q3 = new Question("Quelle est la capitale de la France ?")
        );
        questionList.add(
                q4 = new Question("Quel est le plus grand fleuve du monde ?")
        );
        questionList.add(
                q5 = new Question("Quel est l'autre nom de la ville de Paris ?")
        );
        // question a choix multiples
        questionList.add(
                q6 = new Question("Quels sont les animaux qui peuvent survivre sans boire pendant une très longue période ?")
        );

        q1.addResponse("Canada", false);
        q1.addResponse("Chine", false);
        q1.addResponse("Afrique", false);
        q1.addResponse("Russie", true);

        q2.addResponse("224 mètres", false);
        q2.addResponse("176 mètres", false);
        q2.addResponse("312 mètres", true);
        q2.addResponse("397 mètres", false);

        q3.addResponse("Paris", true);
        q3.addResponse("Lyon", false);
        q3.addResponse("Marseille", false);
        q3.addResponse("Toulouse", false);

        q4.addResponse("Le Nil", false);
        q4.addResponse("Le Danube", false);
        q4.addResponse("Le Mississippi", false);
        q4.addResponse("L'Amazone", true);

        q5.addResponse("La ville rose", false);
        q5.addResponse("La ville lumière", true);
        q5.addResponse("La ville bleue", false);
        q5.addResponse("La ville verte", false);

        q6.addResponse("Les éléphants", false);
        q6.addResponse("Le diable épineux", true);
        q6.addResponse("Les lions", false);
        q6.addResponse("Le rat kangourou", true);
    }

    public Question get(int i) {
        return questionList.get(i);
    }

    public int size() {
        return questionList.size();
    }

    public double getScore(int index) {
        return listScore.get(index);
    }

    public void setScore(int index, double score) {
        listScore.add(index, score);
    }
}
