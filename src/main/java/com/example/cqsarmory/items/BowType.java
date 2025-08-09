package com.example.cqsarmory.items;

public enum BowType {
    SHORTBOW,
    LONGBOW,
    RECURVE_BOW;

    public boolean isLarge() {
        return this == LONGBOW || this == RECURVE_BOW;
    }

    public String getArrowString() {
        return switch (this) {
            case LONGBOW -> "longbow";
            case SHORTBOW -> "shortbow";
            case RECURVE_BOW -> "recurve";
        };
    }
}
