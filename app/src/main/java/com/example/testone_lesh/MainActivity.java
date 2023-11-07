package com.example.testone_lesh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button insertButton;
    Button queryButton;
    Button updateButton;
    Button deleteButton;
    Button nextButton;
    Button previousButton;
    EditText name;
    EditText number;
    EditText type;
    EditText fandom;
    EditText on;
    EditText ultimate;
    EditText price;
    TextView idTv;
    TextView nameTv;
    TextView numTv;
    Cursor mCursor;

    View.OnClickListener updateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ContentValues mUpdateValues = new ContentValues();

            mUpdateValues.put(FunkoDBProvider.COLUMN_NAME, name.getText().toString().trim());
            mUpdateValues.put(FunkoDBProvider.COLUMN_NUMBER, number.getText().toString().trim());
            mUpdateValues.put(FunkoDBProvider.COLUMN_TYPE, type.getText().toString().trim());
            mUpdateValues.put(FunkoDBProvider.COLUMN_FANDOM, fandom.getText().toString().trim());
            mUpdateValues.put(FunkoDBProvider.COLUMN_ON, on.getText().toString().trim());
            mUpdateValues.put(FunkoDBProvider.COLUMN_ULTIMATE, ultimate.getText().toString().trim());
            mUpdateValues.put(FunkoDBProvider.COLUMN_PRICE, price.getText().toString().trim());

            String mSelectionClause = FunkoDBProvider.COLUMN_NAME + " = ? AND " +  FunkoDBProvider.COLUMN_NUMBER
                    + " = ? AND " + FunkoDBProvider.COLUMN_TYPE + " = ? AND " + FunkoDBProvider.COLUMN_FANDOM
                    + " = ? AND " + FunkoDBProvider.COLUMN_ON + " = ? AND " + FunkoDBProvider.COLUMN_ULTIMATE
                    + " = ? AND " + FunkoDBProvider.COLUMN_PRICE + " = ? ";

            String[] mSelectionArgs = { nameTv.getText().toString().trim(), numTv.getText().toString().trim() };

            int numRowsUpdates= getContentResolver().update(FunkoDBProvider.CONTENT_URI, mUpdateValues,
                    mSelectionClause, mSelectionArgs);

            clear();
        }
    };

    View.OnClickListener insertListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(FunkoDBProvider.COLUMN_NAME, name.getText().toString().trim());
            contentValues.put(FunkoDBProvider.COLUMN_NUMBER, number.getText().toString().trim());
            contentValues.put(FunkoDBProvider.COLUMN_TYPE, type.getText().toString().trim());
            contentValues.put(FunkoDBProvider.COLUMN_FANDOM, fandom.getText().toString().trim());
            contentValues.put(FunkoDBProvider.COLUMN_ON, on.getText().toString().trim());
            contentValues.put(FunkoDBProvider.COLUMN_ULTIMATE, ultimate.getText().toString().trim());
            contentValues.put(FunkoDBProvider.COLUMN_PRICE, price.getText().toString().trim());
            getContentResolver().insert(FunkoDBProvider.CONTENT_URI, contentValues);
            clear();
        }
    };

    View.OnClickListener queryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mCursor = getContentResolver().query(FunkoDBProvider.CONTENT_URI, null, null, null, null);
            if(mCursor != null){
                if(mCursor.getCount() > 0){
                    mCursor.moveToFirst();
                    setViews();
                }
            }
        }
    };

    View.OnClickListener previousListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mCursor != null){
                if(!mCursor.moveToPrevious()){
                    mCursor.moveToLast();
                }
                setViews();
            }
        }
    };

    View.OnClickListener nextListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mCursor != null){
                if(!mCursor.moveToNext()){
                    mCursor.moveToFirst();
                }
                setViews();
            }
        }
    };

    View.OnClickListener deleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String selectionClause = FunkoDBProvider.COLUMN_NAME + " = ? AND " + FunkoDBProvider.COLUMN_NUMBER + " = ?";
            String[] selectedArgs = {nameTv.getText().toString().trim(), numTv.getText().toString().trim()};
            getContentResolver().delete(FunkoDBProvider.CONTENT_URI, selectionClause, selectedArgs);
            clear();
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name_ET);
        number = findViewById(R.id.number_ET);
        type = findViewById(R.id.type_ET);
        fandom = findViewById(R.id.fandom_ET);
        on = findViewById(R.id.on_ET);
        ultimate = findViewById(R.id.ultimate_ET);
        price = findViewById(R.id.price_ET);

        insertButton = findViewById(R.id.insertButton);
        queryButton = findViewById(R.id.queryButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        nextButton = findViewById(R.id.nextButton);
        previousButton = findViewById(R.id.previousButton);

        idTv = findViewById(R.id.unique_id);
        nameTv = findViewById(R.id.fnameTextView);
        numTv = findViewById(R.id.lnameTextView);

        insertButton.setOnClickListener(insertListener);
        updateButton.setOnClickListener(updateListener);
        deleteButton.setOnClickListener(deleteListener);
        queryButton.setOnClickListener(queryListener);
        previousButton.setOnClickListener(previousListener);
        nextButton.setOnClickListener(nextListener);

    }

    private void setViews() {
        idTv.setText(mCursor.getString(0));
        String text1 = mCursor.getString(1) + " ";
        nameTv.setText(text1);
        numTv.setText(mCursor.getString(2));
    }

    private void clear() {
        name.setText("");
        number.setText("");
        type.setText("");
        fandom.setText("");
        on.setText("");
        ultimate.setText("");
        price.setText("");

        idTv.setText("");
        nameTv.setText("");
        numTv.setText("");

        mCursor = null;
    }
}