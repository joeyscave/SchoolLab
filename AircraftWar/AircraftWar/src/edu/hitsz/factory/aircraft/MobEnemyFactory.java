package edu.hitsz.factory.aircraft;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;

public class MobEnemyFactory implements AbstractAircraftFactory {
    @Override
    public AbstractAircraft create() {
        return new MobEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())) * 1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) * 1,
                0,
                Main.settings.enemySpeed,
                Main.settings.enemyBlood
        );
    }
}
