package org.jetbrains.assignment.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@AllArgsConstructor
public class Move {

    private String direction; // Assuming the direction is a String. Consider using an Enum instead.
    private int steps;

    // Getters and Setters

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    // Constructors, equals, hashCode, toString (Depending on what you need)
}