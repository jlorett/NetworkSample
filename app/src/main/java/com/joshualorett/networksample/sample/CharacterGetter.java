package com.joshualorett.networksample.sample;

/**
 * Get characters.
 */

public interface CharacterGetter {
    interface CharacterGetterListener {
        void onGetCharacter(Character[] characters);
        void onGetCharacterError(int statusCode);
    }

    void get(CharacterGetterListener listener);
}
