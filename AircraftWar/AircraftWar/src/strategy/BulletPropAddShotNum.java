package strategy;

import edu.hitsz.aircraft.HeroAircraft;

public class BulletPropAddShotNum implements BulletPropAbstractStrategy{

    private int increaseBullet;

    public BulletPropAddShotNum(int increaseBullet) {
        this.increaseBullet = increaseBullet;
    }

    @Override
    public void active(HeroAircraft heroAircraft) {
        heroAircraft.setIncreaseShootNum(increaseBullet);
    }
}
