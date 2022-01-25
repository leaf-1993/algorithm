package com.algorithm.list;

/**
 * @author chenxiang
 * @create 2021-11-04 13:17
 */
public interface MyList<E> {
    /**
     * 元素数量
     * @return
     */
    int size();

    /**
     * 是否为空
     * @return
     */
    boolean isEmpty();

    /**
     * 是否包含元素
     * @param element 元素
     * @return
     */
    boolean contains(E element);

    /**
     * 添加元素
     * @param element 元素
     */
    void add(E element);

    /**
     * 获取指定位置元素
     * @param index 索引
     * @return
     */
    E get(int index);

    /**
     * 指定索引位置获取元素并返回原元素
     * @param index 索引
     * @param element 元素
     * @return
     */
    E set(int index, E element);

    /**
     * 指定位置添加元素
     * @param index 索引
     * @param element 元素
     */
    void add(int index, E element);

    /**
     * 删除指定位置元素
     * @param index 索引
     * @return
     */
    E remove(int index);

    /**
     * 查询元素所在的索引位置
     * @param element 元素
     * @return
     */
    int indexOf(E element);

    /**
     * 清空元素
     */
    void clear();
}
