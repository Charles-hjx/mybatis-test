package com.hjx.springbootmybatis.mylist;

/**
 *
 * 手写一个ArrayList
 *
 * @Author: hjx
 * @Date: 2019/7/18
 * @Version 1.0
 */
public class MyArrayList {

    //非私有，以简化嵌套类访问
    // transient 表示在已经实现序列化的类中，不允许变量序列化
    transient Object[] elementData;

    // 默认容量
    private static final int DEFAULT_CAPACITY = 10;

    //用于空实例的 空数组实例
    private static final Object[] EMPTY_ELEMENTDATA={};

    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    // 实际ArrayList集合大小
    private int size;

    //构造方法
    public MyArrayList(int initialCpacity){
        if(initialCpacity > 0){
            this.elementData = new Object[initialCpacity];
        }else if(initialCpacity == 0){
            this.elementData = EMPTY_ELEMENTDATA;
        }else {
            throw new IllegalArgumentException("Illegal Capacity:"+initialCpacity);
        }
    }
    public MyArrayList(){
        this(DEFAULT_CAPACITY);
    }

    public void add(Object o){
        //判断容量 是否大于elementData
        ensureExplicitCapacity(size+1);

        //2. 使用下标进行赋值
        elementData[size++] = o;
    }

    private void ensureExplicitCapacity(int minCapacity) {
        if(size == elementData.length){
            //需要扩容 默认扩容1.5倍
            int oldCapacity = elementData.length;
            int newCapacity = oldCapacity +(oldCapacity >> 2);

            // 如果新容量 < 最小容量， 则将最小容量赋值给新容量
            // 如果 oldCapacity=1, 则 minCapacity=1+1=2   newCapacity=1+(1>>1)=1
            if(newCapacity < minCapacity ){
                newCapacity=minCapacity;
            }
            //创建新的数组
            Object[] objects = new Object[newCapacity];
            // 将数据复制给新数组
            System.arraycopy(elementData,0,objects,0,elementData.length);
            //修改引用
            elementData = objects;
        }
    }

    public Object get(int index){
        rangeCheck(index);
        return elementData[index];
    }

    private void rangeCheck(int index) {
        if(index >= size){
            throw new IndexOutOfBoundsException("下标越界");
        }
    }

    public Boolean remove(Object o){
        for(int index = 0; index < size ; index ++){
            if(o.equals(elementData[index])){
                remove(index);
                return true;
            }
        }
        return false;
    }

    public Object remove(int index){
        rangeCheck(index);
        Object oldValue = elementData[index];
        //利用 数据覆盖工具覆盖该值
        //需要移动的元素 数量
        int moveNum = size-index-1;
        if(moveNum>0){
            System.arraycopy(elementData,index+1,elementData,index,moveNum);
            elementData[--size] = null; // 断开 此处对象的 引用，让 GC来回收
        }
        return oldValue;
    }
}
