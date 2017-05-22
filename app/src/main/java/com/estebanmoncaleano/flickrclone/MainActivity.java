package com.estebanmoncaleano.flickrclone;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.estebanmoncaleano.flickrclone.data.database.FlickrContract;
import com.estebanmoncaleano.flickrclone.utilties.view.FontHelper;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText searchValue;
    private View mainFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FontHelper.setCustomTypeface(findViewById(R.id.rl_main_view));

        mainFormView = findViewById(R.id.rl_main_view);
        searchValue = (EditText) findViewById(R.id.et_value_search);
        spinner = (Spinner) findViewById(R.id.sp_type_search);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.search_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void onSearch(View view) {
        if(spinner.getSelectedItemPosition() == 0)
            showMessage(getString(R.string.spinner_message_error));
        else if (searchValue.getText().toString().isEmpty())
            searchValue.setError(getString(R.string.edit_text_message_error));
        else if(spinner.getSelectedItemPosition() == 1)
            launchSearchPhoto(searchValue.getText().toString());
        else if (spinner.getSelectedItemPosition() == 2)
            launchSearchPeople(searchValue.getText().toString());
        else if (spinner.getSelectedItemPosition() == 3)
            launchSearchGroup(searchValue.getText().toString());
    }

    public void onShowRecent(View view) {
    }

    private void launchSearchPhoto(String searchPhoto) {
        Intent intent = new Intent(this, PhotoSearchActivity.class);
        intent.putExtra(FlickrContract.PhotoListEntry.TITLE, searchPhoto);
        startActivity(intent);
    }

    private void launchSearchPeople(String searchPeople) {
        /*
        Intent intent = new Intent(this, PeopleSearchActivity.class);
        intent.setData(Uri.withAppendedPath(FlickrContract.PeopleListEntry.CONTENT_URI, searchPeople));
        startActivity(intent);
         */
    }

    private void launchSearchGroup(String searchGroup) {
        /*
        Intent intent = new Intent(this, GroupSearchActivity.class);
        intent.setData(Uri.withAppendedPath(FlickrContract.GroupListEntry.CONTENT_URI, searchGroup));
        startActivity(intent);
         */
    }

    private void showMessage(String message) {
        Snackbar.make(mainFormView, message, Snackbar.LENGTH_LONG).show();
    }
}
