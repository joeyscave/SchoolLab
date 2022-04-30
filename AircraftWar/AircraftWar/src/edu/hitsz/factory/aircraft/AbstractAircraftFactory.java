package edu.hitsz.factory.aircraft;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.basic.AbstractFlyingObject;

public interface AbstractAircraftFactory {
    public AbstractAircraft create();
}
