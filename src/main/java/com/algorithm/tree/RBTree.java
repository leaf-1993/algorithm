package com.algorithm.tree;

/**
 * @author chenxiang
 * @create 2021-11-08 12:36
 * ①创建RBTree，定义颜色
 * ②创建RBNode
 * ③辅助方法定义：parentOf(node)，
 *              isRed(node)，
 *              setRed(node)，
 *              setBlack(node)，
 *              inOrderPrint(RBNode tree)
 * ④左旋方法定义：leftRotate(node)
 * ⑤右旋方法定义：rightRotate(node)
 * ⑥公开插入接口方法定义：insert(K key, V value);
 * ⑦内部插入接口方法定义：insert(RBNode node);
 * ⑧修正插入导致红黑树失衡的方法定义：insertFIxUp(RBNode node);
 * ⑨测试红黑树正确性
 */
public class RBTree<K extends Comparable<K>, V> {
    /**
     * 红色
     */
    private static final boolean RED = true;
    /**
     * 黑色
     */
    private static final boolean BLACK = false;

    /**
     * 根节点
     */
    private RBNode root;


    /**获取当前节点父节点
     * @param node 节点
     * @return
     */
    private RBNode parentOf(RBNode node){
        if(node != null){
            return node.parent;
        }
        return null;
    }

    /**
     * 是否为红色节点
     * @param node 节点
     * @return
     */
    private boolean isRed(RBNode node){
        if(node != null){
            return node.color;
        }
        return false;
    }

    /**
     * 设置为红色节点
     * @param node 节点
     */
    private void setRed(RBNode node){
        if(node != null){
            node.setColor(RED);
        }
    }

    /**
     * 是否为黑色节点
     * @param node 节点
     * @return
     */
    private boolean isBlack(RBNode node){
        if(node != null){
            return !node.color;
        }
        return false;
    }

    /**
     * 设置为黑色节点
     * @param node 节点
     */
    private void setBlack(RBNode node){
        if(node != null){
            node.setColor(BLACK);
        }
    }

    /**
     * 中序打印
     */
    public void inOrderPrint(){
        inOrderPrint(root);
    }

    private void inOrderPrint(RBNode node){
        if(node != null){
            // 先打印左边
            inOrderPrint(node.left);
            // 再打印中间
            System.out.println("key:"+node.key + ",value:" + node.value);
            // 最后打印右边
            inOrderPrint(node.right);
        }
    }

    /**公开的插入方法
     * @param key
     * @param value
     */
    public void insert(K key, V value){
        RBNode node = new RBNode();
        node.setKey(key);
        node.setValue(value);
        // 新节点一定是红色
        node.setColor(RED);

    }

    private void insert(RBNode node){
        RBNode parent = null;
        RBNode x = this.root;
        while (x != null){
            parent = x;
            int cmp = node.key.compareTo(x.key);
            if(cmp > 0 ){
                x = x.right;
            }else if(cmp == 0){
                x.setValue(node.getValue());
                return;
            }else{
                x = x.left;
            }
        }

        if(parent != null){
            final int cmp = node.key.compareTo(parent.key);
            if(cmp > 0){
                parent.right = node;
            }else{
                parent.left = node;
            }
        }else{
            this.root = node;
        }

    }

    /*
    * 插入后修复红黑树平衡的方法：
    *   |---情景1：红黑树为空树，将根节点染色为黑色
    *   |---情景2：插入节点的key已经存在，不需要处理（插入时已经处理）
    *   |---情景3：插入节点的父节点为黑色，无需平衡
    *
    *   情景4：需要处理
    *   |---情景4：插入节点的父节点为红色
    *       |---情景4.1：叔叔节点存在，并且为红色（父-叔 双红）
    *                   将父节点和叔叔节点染色成黑色，爷爷节点染成红色，并且再以爷爷节点为当前节点，进行下一轮处理
    *       |---情景4.2：叔叔节点不存在，或者为黑色，父节点为爷爷节点的左子树
    *           |---情景4.2.1：插入节点为其父节点的左子节点（LL情况）
    *                          将父节点染成黑色，将爷爷节点染成红色，然后以爷爷节点进行右旋
    *           |---情景4.2.2：插入节点为其父节点的右子节点（LR情况）
    *                          以父节点进行一次左旋，得到LL双红情景（4.2.1），然后指定父节点为当前节点进行下一轮处理
    *       |---情景4.3：叔叔节点不存在，或者为黑色，父节点为爷爷节点的右子树
    *           |---情景4.3.1：插入节点为其父节点的右子节点（RR情况）
    *                         将父节点染成黑色，爷爷节点染成红色，以爷爷节点进行左旋
    *           |---情景4.3.2：插入节点为其父节点的左子节点（RL情况）
    *                         以父节点进行右旋，得到RR情景（4.3.1），然后指定父节点进行下一轮处理
    * */
    private void insertFixUp(RBNode node){
        // 情景1
        this.root.setColor(BLACK);
        RBNode parent = parentOf(node);
        RBNode grand = parentOf(parent);
        // 插入节点的父节点为红色
        if(parent != null && isRed(parent)){
            // 如果父节点为红色，那么一定存在爷爷节点
            RBNode uncle = null;
            if(parent == grand.left){
                uncle = grand.right;
            }
        }
    }


    /**
     *
     *左旋方法
     * 左旋示意图：左旋x节点
     *      p                   p
     *      |                   |
     *      x                   y
     *     / \        ----->   / \
     *    lx  y               x   ry
     *       / \             / \
     *       ly ry          lx  ly
     *
     * 1、将x的右子节点指向y的左子节点（ly）,将y的左子节点的父节点更新为x
     * 2、将x的父节点（不为空时），更新y的父节点为x的父节点，并将x的父节点指定子树（当前x的子树位置），指定为y
     * 3、将x的父节点更新为y，将y的左子节点更新为x
     * @param x 旋转的节点
     */
    private void leftRotate(RBNode x){
        //
        RBNode y = x.right;
        //
        x.right = y.left;
        if(y.left != null){
            y.left.parent = x;
        }
        if(x.parent != null){
            y.parent = x.parent;
            if(x == x.parent.left){
                x.parent.left = y;
            }else{
                x.parent.right = y;
            }
        }else{
            root = y;
        }

        x.parent = y;
        y.left = x;

    }

    /**
     * 右旋转方法
     * 右旋示意图：右旋y节点
     *
     *      p                                 p
     *      |                                 |
     *      y                                 x
     *     / \                               / \
     *    x  ry      ------------->         lx  y
     *   / \                                   / \
     *  lx ly                                 ly  ry
     * @param y
     * 1、将y左子节点指向x的右子节点，并且更新x的右子节点的父节点为y
     * 2、当y的父节点不为空时，更新x的父节点为y的父节点，更新y的父节点的指定子节点（y当前的位置）为x
     * 3、更新y的父节点为x，更新x的右子节点为y
     */
    private void rightRotate(RBNode y){
        final RBNode x = y.left;
        y.left = x.right;
        if(x.right != null){
            x.right.parent = y;
        }
        if(y.parent != null){
            x.parent = y.parent;
            if(y.parent.left == y){
                y.parent.left = x;
            }else{
                y.parent.right = x;
            }
        }else{
            root = y;
        }
        // 3、更新y的父节点为x，更新x的右子节点为y

        y.parent = x;
        x.right = y;
    }

    /**
     * 红黑树节点
     * @param <K> key
     * @param <V> value
     */
    static class RBNode<K extends Comparable<K>, V> {
        /**
         * 父节点
         */
        private RBNode parent;
        /**
         * 左节点
         */
        private RBNode left;
        /**
         * 右节点
         */
        private RBNode right;
        /**
         * 颜色
         */
        private boolean color;
        /**
         * key
         */
        private K key;
        /**
         * 值
         */
        private V value;

        public RBNode(RBNode parent, RBNode left, RBNode right, boolean color, K key, V value) {
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.color = color;
            this.key = key;
            this.value = value;
        }

        public RBNode() {
        }

        public RBNode getParent() {
            return parent;
        }

        public void setParent(RBNode parent) {
            this.parent = parent;
        }

        public RBNode getLeft() {
            return left;
        }

        public void setLeft(RBNode left) {
            this.left = left;
        }

        public RBNode getRight() {
            return right;
        }

        public void setRight(RBNode right) {
            this.right = right;
        }

        public boolean isColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}
