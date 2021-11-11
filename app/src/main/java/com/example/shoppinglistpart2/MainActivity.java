package com.example.shoppinglistpart2;

import static com.example.shoppinglistpart2.ItemsListActivity.itemsListElement;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView itemsList;
    Button addButton;
    private boolean isListEmpty = true;
    private SharedPreferences sharedPreferences;
    private String fileName = "SharedPrefFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemsList = findViewById(R.id.itemsListTextView);
        addButton = findViewById(R.id.addButton);

        sharedPreferences = getSharedPreferences(fileName,MODE_PRIVATE);

        if(savedInstanceState!=null){
            isListEmpty = savedInstanceState.getBoolean("IS_LIST_EMPTY");
            itemsList.setText(savedInstanceState.getString("LIST_ITEMS"));
        }
        else{
            isListEmpty = sharedPreferences.getBoolean("IS_LIST_EMPTY",isListEmpty);
            itemsList.setText(sharedPreferences.getString("LIST_ITEMS",itemsList.getText().toString()));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("IS_LIST_EMPTY",isListEmpty);
        outState.putString("LIST_ITEMS",itemsList.getText().toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("IS_LIST_EMPTY",isListEmpty);
        editor.putString("LIST_ITEMS",itemsList.getText().toString());
        editor.apply();
    }

    ActivityResultLauncher addButtonActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode()!=RESULT_OK)   return;
                if(isListEmpty){
                    itemsList.setText("");
                    isListEmpty = false;
                }
                itemsList.append(result.getData().getStringExtra(itemsListElement)+"\n");

            }

    );

    public void addButtonClicked(View view) {
        Intent intent = new Intent(this,ItemsListActivity.class);
        //startActivityForResult(intent,1);
        addButtonActivityResultLauncher.launch(intent);
    }





    ActivityResultLauncher searchButtonActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode()!=RESULT_OK)   return;
                Intent searchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q="+result.getData().getStringExtra(itemsListElement)));
                startActivity(searchIntent);
            }
    );

    public void searchButtonClicked(View view) {
        Intent intent = new Intent(this, ItemsListActivity.class);
        //startActivityForResult(intent,2);
        searchButtonActivityResultLauncher.launch(intent);
    }














    /*

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(isListEmpty){
                itemsList.setText(data.getStringExtra(itemsListElement));
                isListEmpty = false;
            }
            else {
                itemsList.append("\n" + data.getStringExtra(itemsListElement));
            }
        }
        else if(requestCode == 2){
            Intent searchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q="+data.getStringExtra(itemsListElement)));
            startActivity(searchIntent);
        }
    }

     */


}