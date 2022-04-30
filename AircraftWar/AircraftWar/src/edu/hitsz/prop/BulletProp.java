package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import strategy.BulletPropContext;

import java.util.List;

public class BulletProp extends AbstractProp {

    /**
     * "add shotNum" or "scattering"
     */
    private String type;

    public BulletProp(int locationX, int locationY, int speedX, int speedY,String type) {
        super(locationX, locationY, speedX, speedY);
        this.type=type;
    }

    @Override
    public void active(HeroAircraft heroAircraft, List<AbstractAircraft> enemyAircraft, List<BaseBullet> enemyBullet) {
        BulletPropContext bulletPropContext=new BulletPropContext(type);
        bulletPropContext.active(heroAircraft);
        System.out.println("BulletPropActive!");
    }

    @Override
    public String getMusicPath() {
        return "src\\videos\\get_supply.wav";
    }

}
