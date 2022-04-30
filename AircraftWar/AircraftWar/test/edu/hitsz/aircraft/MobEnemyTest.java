package edu.hitsz.aircraft;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MobEnemyTest {

    private MobEnemy mobEnemy;

    @BeforeEach
    void setUp() {
        mobEnemy = new MobEnemy(0,0,0,0,100);
    }

    @AfterEach
    void tearDown() {
        mobEnemy=null;
    }

    @Test
    void setLocation() {
        mobEnemy.setLocation(5,5);
        assertEquals(5,mobEnemy.getLocationX());
        assertEquals(5,mobEnemy.getLocationY());
    }

    @Test
    void notValid() {
        mobEnemy.vanish();
        assertTrue(mobEnemy.notValid());
    }
}