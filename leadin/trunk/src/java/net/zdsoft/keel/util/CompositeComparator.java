/* 
 * @(#)CompositeComparator.java    Created on 2005-8-30
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: /project/keel/src/net/zdsoft/keel/util/CompositeComparator.java,v 1.2 2007/01/11 01:46:08 huangwj Exp $
 */

package net.zdsoft.keel.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 用来解决字段的组合排列问题的比较器.<br>
 * 运用了组合(Compositor)模式: 就是把一些具有不同功能的类组合起来成一个类或者数组.<br>
 * 然后通过调用某个方法来循环数组中的所有元素, 最后返回用户所需要的结果.
 * 
 * @author huangwj
 * @version $Revision: 1.2 $, $Date: 2007/01/11 01:46:08 $
 */
public class CompositeComparator<T> implements Comparator<T> {

    /**
     * 比较器列表, 越排在列表前面的比较器优先级越高.
     */
    private List<Comparator<T>> comparatorList = new LinkedList<Comparator<T>>();

    /**
     * 获取比较器列表.
     */
    public List<Comparator<T>> getComparatorList() {
        return comparatorList;
    }

    /**
     * 添加一个比较器到比较器列表中.
     */
    public void addComparator(Comparator<T> comparator) {
        if (comparator == null) {
            return;
        }

        comparatorList.add(comparator);
    }

    /**
     * 添加多个比较器到比较器列表中.
     */
    public void addComparators(Comparator<T>[] comparators) {
        if (comparators == null) {
            return;
        }

        for (int i = 0; i < comparators.length; i++) {
            comparatorList.add(comparators[i]);
        }
    }

    /**
     * 根据比较器列表中的比较器的优先级来对对象进行排序. 优先级高的比较器会先被使用.<br>
     * 如果两对象比较结果相等, 则会继续使用其次优先级的比较器进行比较.
     */
    public int compare(T o1, T o2) {
        for (Iterator<Comparator<T>> iterator = comparatorList.iterator(); iterator.hasNext();) {
            Comparator<T> comparator = (Comparator<T>) iterator.next();

            int result = comparator.compare(o1, o2);
            if (result != 0) {
                return result;
            }
        }

        return 0;
    }

}
