package com.dasyel.appstudio_app_1;

import java.util.ArrayList;
import java.util.Arrays;

public class DictTree {
    private DictTreeNode root;

    public DictTree(ArrayList<String> wordlist){
        this.root = new DictTreeNode();
        for (String word: wordlist){
            DictTreeNode current = this.root;
            word = word.toLowerCase();
            char[] sortedChars = word.toCharArray();
            Arrays.sort(sortedChars);
            System.out.println();
            for (Character c: sortedChars){
                System.out.print(c);
                DictTreeNode nextChild = current.getChildren().get(c);
                if (nextChild != null){
                    current = nextChild;
                } else {
                    current = new DictTreeNode(current, c);
                }
            }
            current.addWord(word);
        }
    }

    public ArrayList<String> findWords(String letters, int wildcards, int max_length){
        ArrayList<String> results = new ArrayList<>();
        ArrayList<Character> letterArray = new ArrayList<Character>();
        letters = letters.toLowerCase();
        for (char c : letters.toCharArray()) {
            letterArray.add(c);
        }

        DictTreeNode current = this.root;
        int session_id = current.getSessionId() + 1;
        boolean done = false;
        ArrayList<DictTreeNode> queue = new ArrayList<>();

        while(!done) {
            current.checkSessionId(session_id);
            current.setLetters(letterArray);
            current.setWildcards(wildcards);
            if (current.getWords() != null && !current.getWords().isEmpty()) {
                for (String w: current.getWords()){
                    if (!results.contains(w)){
                        results.add(w);
                    }
                }
            }
            if (current.getIterset().size() > 0) {
                Character nextChild = current.getIterset().remove(0);
                if (current.getDepth() < max_length) {
                    if (!queue.contains(current)){
                        queue.add(current);
                    }
                    if (letterArray.contains(nextChild)) {
                        letterArray.remove(nextChild);
                        current = current.getChild(nextChild);
                    } else if (wildcards > 0) {
                        wildcards -= 1;
                        current = current.getChild(nextChild);
                    }
                }
            } else {
                if (!queue.isEmpty()) {
                    current = queue.remove(queue.size()-1);
                    letterArray = current.getLetters();
                    wildcards = current.getWildcards();
                } else {
                    done = true;
                }

            }
        }

        return results;
    }
}
