package strategy;

import edu.hitsz.aircraft.HeroAircraft;

public interface BulletPropAbstractStrategy {
    /**
     * bulletProp道具产生效果
     * @param heroAircraft
     */
    public void active(HeroAircraft heroAircraft);
}
