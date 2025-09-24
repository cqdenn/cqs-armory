package com.example.cqsarmory.registry;

import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;

public record WeaponType(float attackDamage, float attackSpeed) {

    public static final WeaponType WARHAMMER = new WeaponType(10, -3.0f);
    public static final WeaponType GREATSWORD = new WeaponType(6, -2.8f);
    public static final WeaponType HALBERD = new WeaponType(5, -2.6f);
    public static final WeaponType SCYTHE = new WeaponType(5, -2.6f);
    public static final WeaponType MACE = new WeaponType(4, -2.4f);
    public static final WeaponType SPEAR = new WeaponType(4, -2.4f);
    public static final WeaponType RAPIER = new WeaponType(2, -1.0f);
    public static final WeaponType GREATAXE = new WeaponType(7, -2.7f);
    public static final WeaponType SHORTBOW = new WeaponType(4, 2f);
    public static final WeaponType BOW = new WeaponType(8, 1f);
    public static final WeaponType RECURVE = new WeaponType(8, 1f);
    public static final WeaponType LONGBOW = new WeaponType(12, 0.6f);
    public static final WeaponType STAFF = new WeaponType(5, -3f);
}
