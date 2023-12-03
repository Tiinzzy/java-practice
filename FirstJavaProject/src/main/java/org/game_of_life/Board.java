package org.game_of_life;

import netscape.javascript.JSObject;
import org.json.JSONArray;

public interface Board {
    void initialize(int count);

    void setCell(int row, int col);

    void display();

    int getNumberOfAliveCellAround(int row, int col);

    void evolve();

    JSONArray toJSON();
}
