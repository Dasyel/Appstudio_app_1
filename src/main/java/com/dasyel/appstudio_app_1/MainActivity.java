package com.dasyel.appstudio_app_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;


public class MainActivity extends ActionBarActivity {
    public final static String WORD_LIST = "com.dasyel.appstudio_app_1.WORDLIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressBar mProgress = (ProgressBar) findViewById(R.id.progressBarFindWords);
        mProgress.setProgress(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void findWords(View button) {
        final EditText letterField = (EditText) findViewById(R.id.EditTextLetters);
        String letters = letterField.getText().toString();

        final EditText wildcardField = (EditText) findViewById(R.id.EditTextWildcards);
        String wildcards = wildcardField.getText().toString();

        final EditText sizeField = (EditText) findViewById(R.id.EditTextSize);
        String max_size = sizeField.getText().toString();

        final Spinner languageSpinner = (Spinner) findViewById(R.id.SpinnerLanguage);
        String language = languageSpinner.getSelectedItem().toString();

        ProgressBar mProgress = (ProgressBar) findViewById(R.id.progressBarFindWords);
        mProgress.setIndeterminate(true);

        new LongOperation(this).execute(language, letters, wildcards, max_size);
    }

    private ArrayList<String> getDictionary(String language){
        ArrayList<String> dictionary = new ArrayList<>();
        InputStream is;
        Resources res = getResources();
        if (language.equalsIgnoreCase("nederlands")){
            is = res.openRawResource(R.raw.dutch);
        } else {
            is = res.openRawResource(R.raw.english);
        }
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String thisLine;

        try {
            while ((thisLine = br.readLine()) != null) {
                dictionary.add(thisLine);
            }
        }

        catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return dictionary;
    }

    private class LongOperation extends AsyncTask<String, Integer, ArrayList<String>> {
        private Context context;

        public LongOperation(Context context){
            this.context = context;
        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            String language = params[0];
            String letters = params[1];
            int wildcards = Integer.parseInt(params[2]);
            int max_size = Integer.parseInt(params[3]);

            DictTree tree;
            if (language.equalsIgnoreCase("nederlands")){
                tree = new DictTree(getDictionary("nederlands"));
            } else {
                tree = new DictTree(getDictionary("english"));
            }

            ArrayList<String> wordList = tree.findWords(letters, wildcards, max_size);

            MyComparator comparator = new MyComparator();
            java.util.Collections.sort(wordList, comparator);
            ArrayList<String> results = new ArrayList<>();
            for (int i = 0; i < 500 && i < wordList.size(); i++){
                results.add(wordList.get(i));
            }
            return results;
        }

        @Override
        protected void onPostExecute(ArrayList<String> results) {
            Intent intent = new Intent(context, ShowWordlistActivity.class);
            intent.putStringArrayListExtra(WORD_LIST, results);
            context.startActivity(intent);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Integer... values) {
            ProgressBar mProgress = (ProgressBar) findViewById(R.id.progressBarFindWords);
            mProgress.setProgress(values[0]);
        }
    }
}
