package com.example.fundamentals;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class CreatePostActivity extends AppCompatActivity {

    private static final String TAG = "CreatePostActivity";

    // vars
    private Application mContext;

    // widgets
    TextInputLayout titleInputLayout, usernameInputLayout, descriptionInputLayout, picturePathInputLayout;
    TextInputEditText titleEditText, usernameEditText, descriptionEditText, picturePathEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        mContext = getApplication();

        MaterialButton btnSave, btnCancel;

        titleInputLayout = (TextInputLayout) findViewById(R.id.titleInputLayout);
        usernameInputLayout = (TextInputLayout) findViewById(R.id.usernameInputLayout);
        descriptionInputLayout = (TextInputLayout) findViewById(R.id.descriptionInputLayout);
        picturePathInputLayout = (TextInputLayout) findViewById(R.id.picturePathInputLayout);

        titleEditText = (TextInputEditText) findViewById(R.id.titleEditText);
        usernameEditText = (TextInputEditText) findViewById(R.id.usernameEditText);
        descriptionEditText = (TextInputEditText) findViewById(R.id.descriptionEditText);
        picturePathEditText = (TextInputEditText) findViewById(R.id.picturePathEditText);

        btnSave = (MaterialButton) findViewById(R.id.btnSave);
        btnCancel = (MaterialButton) findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Clicked on cancel button");
                // TODO: destroy activity and go back to main activity.  Nothing gets saved
                Intent intent = new Intent(CreatePostActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Clicked on save button");
                if (allFieldsFilledOut()) {
                    // TODO:  send the post to the database
                    Toast.makeText(CreatePostActivity.this, "All fields filled out.  Ready to send to DB", Toast.LENGTH_LONG).show();
                } else {
                    Log.d(TAG, "onClick: Not all fields were filled out");
                    Toast.makeText(CreatePostActivity.this, "Please fill out all fields correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Boolean allFieldsFilledOut() {

        String title = titleEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String picturePath = picturePathEditText.getText().toString();

        Boolean allFieldsFilledOut = true;

        if (title.equals("")) {
            titleInputLayout.setError("Please Enter a Title");
            allFieldsFilledOut = false;
        } else {
            titleInputLayout.setError(null);
        }

        if (username.equals("")) {
            usernameInputLayout.setError("Please Enter a username");
            allFieldsFilledOut = false;
        } else {
            usernameInputLayout.setError(null);
        }

        if (description.equals("")) {
            descriptionInputLayout.setError("Please Enter a description");
            allFieldsFilledOut = false;
        } else {
            descriptionInputLayout.setError(null);
        }

        if (picturePath.equals("")) {
            picturePathInputLayout.setError("Please Enter a picture path");
            allFieldsFilledOut = false;
        } else {
            picturePathInputLayout.setError(null);
        }


        return allFieldsFilledOut;
    }
}
