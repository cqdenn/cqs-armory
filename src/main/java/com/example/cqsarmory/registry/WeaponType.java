package com.example.cqsarmory.registry;

import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;

public record WeaponType(float attackDamage, float attackSpeed) {

    public static final WeaponType WARHAMMER = new WeaponType(10, -3.2f);
    public static final WeaponType GREATSWORD = new WeaponType(6, -2.8f);
    public static final WeaponType HALBERD = new WeaponType(5, -2.6f);
    public static final WeaponType SCYTHE = new WeaponType(5, -2.6f);
    public static final WeaponType MACE = new WeaponType(4, -2.4f);
    public static final WeaponType SPEAR = new WeaponType(4, -2.4f);
    public static final WeaponType RAPIER = new WeaponType(2, -2.0f);
}
