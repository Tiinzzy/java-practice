package org.netflix.game_of_life;

import org.json.JSONObject;

public interface Board {
    void initialize(int count);

    void setCell(int row, int col);

    void display();

    int getNumberOfAliveCellAround(int row, int col);

    void evolve();

    JSONObject toJSON();

    boolean getCellState(int row, int column);
}