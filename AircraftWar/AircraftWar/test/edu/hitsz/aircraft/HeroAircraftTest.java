package edu.hitsz.aircraft;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {

    private HeroAircraft heroAircraft;

    @BeforeEach
    void setUp() {
        heroAircraft = HeroAircraft.getHeroAircraft();
    }

    @AfterEach
    void tearDown() {
        heroAircraft = null;
    }

    @Test
    void crash() {
        MobEnemy mobEnemy = new MobEnemy(heroAircraft.getLocationX(), heroAircraft.getLocationY(), 0, 0, 100);
        assertTrue(heroAircraft.crash(mobEnemy));
    }

    @Test
    void getSpeedY() {
        assertEquals(0, heroAircraft.getSpeedY());
    }
}