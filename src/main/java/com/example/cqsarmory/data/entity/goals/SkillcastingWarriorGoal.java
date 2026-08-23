package com.example.cqsarmory.data.entity.goals;

import io.redspace.ironsspellbooks.entity.mobs.goals.WarlockAttackGoal;
import io.redspace.skillcasting.data.SkillcastingData;
import io.redspace.skillcasting.data.cast.ActiveCast;
import io.redspace.skillcasting.data.cast.CastType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.PathfinderMob;

public class SkillcastingWarriorGoal extends WarlockAttackGoal {
    public SkillcastingWarriorGoal(PathfinderMob abstractSpellCastingMob, double pSpeedModifier, int minAttackInterval, int maxAttackInterval) {
        super(abstractSpellCastingMob, pSpeedModifier, minAttackInterval, maxAttackInterval);
    }

    @Override
    protected float meleeBias() {
        return 0;
    }

    @Override
    protected void doMovement(double distanceSquared) {
        if (target.isDeadOrDying()) {
            this.mob.getNavigation().stop();
        } else {
            var meleeRange = meleeRange();
            mob.lookAt(target, 30, 30);
            float strafeForwards;
            float speed = (float) movementSpeed();
            if (distanceSquared > meleeRange * meleeRange) {
                mob.setXxa(0); // manually override strafe control before we set navigation
                if (mob.tickCount % 5 == 0) {
                    this.mob.getNavigation().moveTo(this.target, meleeMoveSpeedModifier);
                }
            } else {
                this.mob.getNavigation().stop();
                strafeForwards = .5f * meleeMoveSpeedModifier * (4 * distanceSquared > meleeRange * meleeRange ? 1.5f : -1);
                //we do a little strafing
                if (++strafeTime > 25) {
                    if (mob.getRandom().nextDouble() < .1) {
                        strafingClockwise = !strafingClockwise;
                        strafeTime = 0;
                    }
                }
                float strafeDir = strafingClockwise ? 1f : -1f;
                mob.getMoveControl().strafe(strafeForwards, speed * strafeDir);
            }
            mob.getLookControl().setLookAt(target);
        }
    }

    @Override
    protected void handleAttackLogic(double distanceSquared) {
        if (!wantsToMelee || SkillcastingData.get(mob).isCasting()) {
            wizardAttackLogic(distanceSquared);
        } else if (--this.meleeAttackDelay <= 0) {
            this.mob.swing(InteractionHand.MAIN_HAND);
            doMeleeAction();
        }
    }

    protected void wizardAttackLogic(double distanceSquared) {
        if (seeTime < -50) {
            return;
        }
        if (isCasting()) {
            ActiveCast activeCast = SkillcastingData.get(mob).getActiveCast();
            if (activeCast != null && activeCast.context().skill().value().shouldAIStopCasting(activeCast.context(), mob, target)) {
                interruptCast();
                return;
            }
            maybeCancelContinuousCastOnDamage();
        } else if (!isActing() && --this.spellAttackDelay == 0) {
            resetSpellAttackTimer(distanceSquared);
            doSpellAction();
        } else if (this.spellAttackDelay < 0) {
            resetSpellAttackTimer(distanceSquared);
        }
    }

    private void maybeCancelContinuousCastOnDamage() {
        if (isCasting() && mob.getLastHurtByMobTimestamp() == mob.tickCount - 1 &&
                SkillcastingData.get(mob).getActiveCastType() == CastType.CONTINUOUS &&
                mob.getRandom().nextFloat() > mob.getHealth() / mob.getMaxHealth()) {
            interruptCast();
        }
    }
}
