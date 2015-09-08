package com.dasyel.appstudio_app_1;

import java.util.ArrayList;
import java.util.HashMap;

public class DictTreeNode {
    private DictTreeNode parent;
    private HashMap<Character, DictTreeNode> children;
    private ArrayList<String> words;
    private int depth;
    private ArrayList<Character> iterset;
    private int session_id;
    private int wildcards;
    private ArrayList<Character> letters;

    public DictTreeNode(){
        this.children = new HashMap<>();
        this.words = null;
        this.parent = null;
        this.depth = 0;
        this.session_id = 1;
        this.iterset = new ArrayList<>();
    }

    public DictTreeNode(DictTreeNode parent, Character key){
        this.parent = parent;
        this.children = new HashMap<>();
        this.words = new ArrayList<>();
        this.parent.addChild(key, this);
        this.session_id = 1;
        this.iterset = new ArrayList<>();
        this.depth = parent.getDepth() + 1;
    }

    public void addChild(Character key, DictTreeNode child){
        this.children.put(key, child);
        this.iterset.add(key);
    }

    public HashMap<Character, DictTreeNode> getChildren(){
        return this.children;
    }

    public DictTreeNode getChild(Character key){
        return this.children.get(key);
    }

    public DictTreeNode getParent(){
        return this.parent;
    }

    public ArrayList<String> getWords(){
        return this.words;
    }

    public int getWildcards(){
        return this.wildcards;
    }

    public ArrayList<Character> getLetters(){
        return this.letters;
    }

    public int getDepth(){
        return this.depth;
    }

    public int getSessionId(){
        return this.session_id;
    }

    public ArrayList<Character> getIterset(){
        return this.iterset;
    }

    public void addWord(String word){
        this.words.add(word);
    }

    public void setWildcards(int wildcards){
        this.wildcards = wildcards;
    }

    public void setLetters(ArrayList<Character> letters){
        this.letters = new ArrayList<>(letters);
    }

    public void checkSessionId(int session_id){
        if (this.session_id != session_id){
            this.iterset = new ArrayList<>();
            for(Character c: this.children.keySet()){
                this.iterset.add(c);
            }
            this.session_id = session_id;
        }
    }
}
