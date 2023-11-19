package com.testowanieoprogramowaniaprojekt.entities;


public enum VoteType {
    POSITIVE(1), NEGATIVE(-1),
    ;

    private int type;

    VoteType(int type) {
    }

    public Integer getType() {
        return type;
    }
}