package edu.hitsz.factory.prop;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BulletProp;

public class BulletPropFactory implements AbstractPropFactory{
    @Override
    public AbstractProp create(int x, int y) {
        return new BulletProp(x,y,0,3,"scattering");
    }
}
