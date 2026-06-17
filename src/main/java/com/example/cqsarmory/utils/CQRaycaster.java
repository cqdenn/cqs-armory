package com.example.cqsarmory.utils;

import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public final class CQRaycaster {

    private final Level level;
    private final Entity originEntity;
    private Vec3 start;
    private Vec3 end;
    private boolean fromStart = false;
    private boolean checkForBlocks = false;
    private float bbInflation = 0;
    private Predicate<? super Entity> filter = Utils::canHitWithRaycast;

    public CQRaycaster(Level level, Entity originEntity) {
        this.level = level;
        this.originEntity = originEntity;
    }

    public static CQRaycaster begin(Level level, Entity originEntity) {
        return new CQRaycaster(level, originEntity);
    }

    public CQRaycaster start(Vec3 start) {
        this.start = start;
        return this;
    }

    public CQRaycaster end(Vec3 end) {
        this.end = end;
        return this;
    }

    public CQRaycaster range(float distance) {
        if (start == null) {
            start = originEntity.getEyePosition();
        }
        this.end = originEntity.getLookAngle().normalize().scale(distance).add(start);
        return this;
    }

    public CQRaycaster rangeFromAngle(float distance, Vec3 angle) {
        if (start == null) {
            start = originEntity.getEyePosition();
        }
        this.end = angle.normalize().scale(distance).add(start);
        return this;
    }

    public CQRaycaster rangeFromStart(float distance, Vec3 start, Vec3 angle) {
        this.start = start;
        this.fromStart = true;
        this.end = angle.normalize().scale(distance).add(start);
        return this;
    }

    public CQRaycaster checkForBlocks(boolean checkForBlocks) {
        this.checkForBlocks = checkForBlocks;
        return this;
    }

    public CQRaycaster bbInflation(float bbInflation) {
        this.bbInflation = bbInflation;
        return this;
    }

    public CQRaycaster filter(Predicate<? super Entity> filter) {
        this.filter = filter;
        return this;
    }

    public HitResult build() {
        return performRaycast().get(0);
    }

    public List<HitResult> buildList() {
        return performRaycast();
    }

    public List<HitResult> performRaycast() {
        Objects.requireNonNull(start, "Start must be set to perform raycast");
        Objects.requireNonNull(end, "End must be set to perform raycast");

        BlockHitResult blockHitResult = null;
        Vec3 rayEnd = end;

        if (checkForBlocks) {
            blockHitResult = level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, originEntity));
            rayEnd = blockHitResult.getLocation();
        }

        AABB startingAABB = fromStart ? new AABB(start, start).inflate(0.2, 1, 0.2) : originEntity.getBoundingBox();
        AABB range = startingAABB.expandTowards(rayEnd.subtract(start));
        List<HitResult> hits = new ArrayList<>();
        List<? extends Entity> entities = level.getEntities(originEntity, range, filter);

        for (Entity target : entities) {
            HitResult hit = Utils.checkEntityIntersecting(target, start, rayEnd, bbInflation);
            if (hit.getType() != HitResult.Type.MISS) {
                hits.add(hit);
            }
        }

        if (!hits.isEmpty()) {
            hits.sort(Comparator.comparingDouble(o -> o.getLocation().distanceToSqr(start)));
            if (blockHitResult != null) hits.add(blockHitResult);
            return hits;
        }
        if (checkForBlocks) {
            List<HitResult> blocks = new ArrayList<>();
            blocks.add(blockHitResult);
            return blocks;
        } else {
            List<HitResult> miss = new ArrayList<>();
            miss.add(BlockHitResult.miss(rayEnd, Direction.UP, BlockPos.containing(rayEnd)));
            return miss;
        }
    }
}
