package org.main.modules.object;

import java.util.LinkedList;

public class Theolog {
    String name;
    String surname;
    int age;

    LinkedList<String> neFacts = new LinkedList<>();
    LinkedList<Theologian> facts = new LinkedList<>();

    public Theolog() {
        neFacts.add("Все о Боге!");
    }

    public Theolog(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public boolean addFact(String fact) {
        this.neFacts.add(fact);
        return true;
    }

    public boolean isSporite(){
        if(this.neFacts == null || this.neFacts.size() == 0) {
            return false;
        }
        return true;
    }

    public LinkedList<String> getNeFacts() {
        return neFacts;
    }

    public LinkedList<Theologian> getFacts() {
        return this.facts;
    }

    public void setNeFacts(LinkedList<String> neFacts) {
        this.neFacts = neFacts;
    }

    public void addFacts(Theologian fact) {
        this.facts.add(fact);
    }

    public void setFacts(LinkedList<Theologian> facts) {
        this.facts = facts;
    }

    public boolean isSeriousMan(){
        if(this.neFacts != null && this.facts.size() >= 3) {
            return true;
        }else{
            return false;
        }
    }
}
