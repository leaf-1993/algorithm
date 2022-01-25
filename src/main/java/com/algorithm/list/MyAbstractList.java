package com.algorithm.list;


/**
 * @author chenxiang
 * @create 2021-11-04 13:14
 */
public abstract class MyAbstractList<E> implements MyList<E> {
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
}
