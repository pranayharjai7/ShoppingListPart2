package com.example.shoppinglistpart2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ItemsListActivity extends AppCompatActivity {

    public static final String itemsListElement = "ITEMS_LIST_ELEMENT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
    }

    public void groceryItemsButtonClicked(View view) {
        Intent replyIntent = new Intent();
        replyIntent.putExtra(itemsListElement,((Button)view).getText().toString());
        setResult(RESULT_OK,replyIntent);
        finish();
    }

}