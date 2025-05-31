package com.example.cqsarmory.registry;

public record WeaponPower(float attackDamage, float attackSpeed, int durability) {

    public static final WeaponPower POWER_ONE = new WeaponPower(0, 0, 0);
    public static final WeaponPower POWER_TWO = new WeaponPower(2, 0.1f, 500);
    public static final WeaponPower POWER_THREE = new WeaponPower(4, 0.2f, 1000);
    public static final WeaponPower POWER_FOUR = new WeaponPower(6, 0.3f, 1500);
}
