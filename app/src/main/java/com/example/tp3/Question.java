package com.example.tp3;

public class Question {
    public String question;
    public String[] propositions = new String[4];
    private boolean[] solutions = new boolean[4];

    public Question(String question) {
        this.question = question;
    }

    // accesseur pour les solutions
    public boolean[] getSolutions() {
        return solutions;
    }

    public void addResponse(String prop, boolean verite) {
        for (int i = 0; i < propositions.length; i++) {
            if (propositions[i] == null) {
                propositions[i] = prop;
                solutions[i] = verite;
                return;
            }
        }
    }

    //enlever 0.25 a chaque reponse qui n'est pas correcte
    public double verify(boolean[] userAnswers) {
        if (userAnswers.length != solutions.length) {
            throw new IllegalArgumentException("Le tableau de réponses doit contenir " + solutions.length + " éléments.");
        }

        double result = 1;
        for (int i = 0; i < solutions.length; i++) {
            if (userAnswers[i] != solutions[i]) {
                result -= 0.25;
            }
        }
        return result;
    }
}

