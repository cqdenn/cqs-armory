package com.example.cqsarmory.registry;

public record WeaponPower(float attackDamage, float attackSpeed, float drawspeed, int durability, int power) {

    public static final WeaponPower POWER_ONE = new WeaponPower(0, 0, 0, 0, 1);
    public static final WeaponPower POWER_TWO = new WeaponPower(2, 0.1f, 0.5f, 500, 2);
    public static final WeaponPower POWER_THREE = new WeaponPower(4, 0.2f, 1f, 1000, 3);
    public static final WeaponPower POWER_FOUR = new WeaponPower(6, 0.3f, 1.5f, 1500, 4);
}
