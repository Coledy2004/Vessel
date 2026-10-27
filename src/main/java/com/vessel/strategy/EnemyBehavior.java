package com.vessel.strategy;

import com.vessel.model.Enemy;
import com.vessel.model.Entity;


public interface EnemyBehavior {

    String act(Enemy self, Entity target);

    String describeIntent(Enemy self);
}
