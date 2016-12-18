package com.joshualorett.networksample.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.joshualorett.networksample.R;
import com.joshualorett.networksample.network.StatusCodeTranslator;
import com.joshualorett.networksample.sample.CharacterGetter;
import com.joshualorett.networksample.sample.NetworkCharacterGetter;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView sampleMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sampleMessageView = (TextView) findViewById(R.id.sample_message);

        CharacterGetter characterGetter = null;
        try {
            characterGetter = new NetworkCharacterGetter.Factory().create(this);
        } catch (IOException e) {
            String error = String.format(Locale.getDefault(), "%s\n%s",
                    getString(R.string.get_character_error),
                    StatusCodeTranslator.getMessage(-1));

            sampleMessageView.setText(error);
        }

        characterGetter.get(new CharacterGetter.CharacterGetterListener() {
            @Override
            public void onGetCharacter(Character[] characters) {
                StringBuilder sb = new StringBuilder();

                for(Character character : characters) {
                    sb.append(character.getName());
                    sb.append("\n");
                }

                sampleMessageView.setText(sb.toString());
            }

            @Override
            public void onGetCharacterError(int statusCode) {
                String error = String.format(Locale.getDefault(), "%s\n%s",
                        getString(R.string.get_character_error),
                        StatusCodeTranslator.getMessage(statusCode));

                sampleMessageView.setText(error);
            }
        });
    }
}
