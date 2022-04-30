package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.List;

public abstract class AbstractProp extends AbstractFlyingObject {

    public AbstractProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }


    /**
     * 道具生效
     */
    public void active(HeroAircraft heroAircraft, List<AbstractAircraft> enemyAircraft, List<BaseBullet> enemyBullet) {
    }

    public String getMusicPath(){
        return null;
    }
}
