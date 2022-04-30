package edu.hitsz.factory.prop;

import edu.hitsz.prop.AbstractProp;

public interface AbstractPropFactory {
    public AbstractProp create(int x,int y);
}
