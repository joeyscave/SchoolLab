package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public class BloodProp extends AbstractProp {

    private int increaseHp;

    public BloodProp(int locationX, int locationY, int speedX, int speedY, int increaseHp) {
        super(locationX, locationY, speedX, speedY);
        this.increaseHp = increaseHp;
    }

    @Override
    public void active(HeroAircraft heroAircraft, List<AbstractAircraft> enemyAircraft, List<BaseBullet> enemyBullet) {
        heroAircraft.decreaseHp(-increaseHp);
        System.out.println("BloodPropActive!");
    }

    @Override
    public String getMusicPath() {
        return "src\\videos\\get_supply.wav";
    }
}
