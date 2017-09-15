package net.zdsoft.keelcnet.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.zdsoft.keelcnet.entity.BaseObject;

/**
 * 
 * @author Brave Tao
 * @since 2004-12-16
 * @version $Id: PaginatedListIterator.java,v 1.2 2006/12/11 09:17:50 liangxiao Exp $
 * @param <E>
 * @since
 */
public class PaginatedListIterator<E> extends BaseObject implements Iterator<E> {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 4750804219646049606L;
    
    private PaginatedList<E> list;
    private int i = 0;
    private Iterator<E> iterator;

    /**
     * @param list
     */
    public PaginatedListIterator(PaginatedList<E> list) {
        this.list = list;
    }

    /**
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
        return i < list.size();
    }

    /**
     * This method follows the rules of Iterator.next() except that it returns
     * null when requesting an element that it's not in the current page.
     * 
     * @see java.util.Iterator#next()
     */
    public E next() {
        if (i == list.getFirstElement()) {
            iterator = list.getList().iterator();
        }

        if ((i >= list.getFirstElement())
                && (i < (list.getFirstElement() + list.getMaxElements()))) {
            i++;

            return iterator.next();
        }

        if (hasNext()) {
            i++;

            return null;
        }
        else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Unsupported operation
     * 
     * @see java.util.Iterator#remove()
     * @throws UnsupportedOperationException
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
