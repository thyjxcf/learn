package net.zdsoft.keelcnet.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.zdsoft.keelcnet.entity.BaseObject;

/**
 * <p>
 * Class that represents a paginated List, with elements from firstElement and
 * at maximum maxElements elements from the full list of size elements.
 * </p>
 * <p>
 * Note that firstElement starts at 0
 * </p>
 * <p>
 * Any attempt to access other than the current page will cause an error.
 * </p>
 * <p>
 * This is a read only implementation and many of the <code>List</code>
 * methods are not implemented.
 * </p>
 * 
 * @author Brave Tao
 * @since 2004-12-16
 * @version $Id: PaginatedList.java,v 1.2 2006/12/11 09:17:50 liangxiao Exp $
 * @since
 */
public class PaginatedList<E> extends BaseObject implements List<E> {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1931571897559710417L;
    
    private List<E> list;
    private int firstElement;
    private int maxElements;
    private int size;

    public PaginatedList() {
    }

    public PaginatedList(List<E> list, int firstElement, int maxElements, int size) {
        this.list = list;
        this.firstElement = firstElement;
        this.maxElements = maxElements;
        this.size = size;
    }

    /**
     * Get list with the elements of this page
     * 
     * @return
     */
    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

    /**
     * First element of this page, starting at 0
     * 
     * @return
     */
    public int getFirstElement() {
        return firstElement;
    }

    public void setFirstElement(int firstElement) {
        this.firstElement = firstElement;
    }

    /**
     * Max number of elements in the page
     * 
     * @return
     */
    public int getMaxElements() {
        return maxElements;
    }

    public void setMaxElements(int maxElements) {
        this.maxElements = maxElements;
    }

    /**
     * Set the number of elements in all the pages
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Number of elements in this page
     * 
     * @return
     */
    public int getPageSize() {
        return list.size();
    }

    /**
     * Calculate the page number, starting at 0
     * 
     * @return
     */
    public int getPageNumber() {
        return getFirstElement() / getMaxElements();
    }

    /**
     * Calculate the last page number, starting at 0
     * 
     * @return
     */
    public int getLastPageNumber() {
        return (size() - 1) / getMaxElements();
    }

    // ---------------------------- List operations ----------------------------

    /**
     * Unsupported operation
     * 
     * @see java.util.Collection#iterator()
     * @throws UnsupportedOperationException
     */
    public Iterator<E> iterator() {
        return new PaginatedListIterator<E>(this);
    }

    /**
     * Number of elements in all the pages
     * 
     * @see java.util.Collection#size()
     */
    public int size() {
        return size;
    }

    /**
     * Number of elements in all the pages
     * 
     * @see size()
     */
    public int getSize() {
        return size();
    }

    // ---------------------- Unsupported List operations ----------------------

    /**
     * Unsupported operation
     * 
     * @see java.util.List#add(int, java.lang.Object)
     * @throws UnsupportedOperationException
     */
    public void add(int arg0, Object arg1) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation
     * 
     * @see java.util.Collection#add(java.lang.Object)
     * @throws UnsupportedOperationException
     */
    public boolean add(Object arg0) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation
     * 
     * @see java.util.Collection#addAll(java.util.Collection)
     * @throws UnsupportedOperationException
     */
    public boolean addAll(Collection<? extends E> arg0) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation
     * 
     * @see java.util.List#addAll(int, java.util.Collection)
     * @throws UnsupportedOperationException
     */
    public boolean addAll(int arg0, Collection<? extends E> arg1) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation
     * 
     * @see java.util.Collection#clear()
     * @throws UnsupportedOperationException
     */
    public void clear() {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation
     * 
     * @see java.util.Collection#contains(java.lang.Object)
     * @throws UnsupportedOperationException
     */
    public boolean contains(Object arg0) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation
     * 
     * @see java.util.Collection#containsAll(java.util.Collection)
     * @throws UnsupportedOperationException
     */
    public boolean containsAll(Collection<?> arg0) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation
     * 
     * @see java.util.List#get(int)
     * @throws UnsupportedOperationException
     */
    public E get(int arg0) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation
     * 
     * @see java.util.List#indexOf(java.lang.Object)
     * @throws UnsupportedOperationException
     */
    public int indexOf(Object arg0) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation
     * 
     * @see java.util.Collection#isEmpty()
     * @throws UnsupportedOperationException
     */
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation
     * 
     * @see java.util.List#lastIndexOf(java.lang.Object)
     * @throws UnsupportedOperationException
     */
    public int lastIndexOf(Object arg0) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation
     * 
     * @see java.util.List#listIterator()
     * @throws UnsupportedOperationException
     */
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation
     * 
     * @see java.util.List#listIterator(int)
     * @throws UnsupportedOperationException
     */
    public ListIterator<E> listIterator(int arg0) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation
     * 
     * @see java.util.List#remove(int)
     * @throws UnsupportedOperationException
     */
    public E remove(int arg0) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation
     * 
     * @see java.util.Collection#remove(java.lang.Object)
     * @throws UnsupportedOperationException
     */
    public boolean remove(Object arg0) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation
     * 
     * @see java.util.Collection#removeAll(java.util.Collection)
     * @throws UnsupportedOperationException
     */
    public boolean removeAll(Collection<?> arg0) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation
     * 
     * @see java.util.Collection#retainAll(java.util.Collection)
     * @throws UnsupportedOperationException
     */
    public boolean retainAll(Collection<?> arg0) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation
     * 
     * @see java.util.List#set(int, java.lang.Object)
     * @throws UnsupportedOperationException
     */
    public E set(int arg0, Object arg1) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation
     * 
     * @see java.util.List#subList(int, int)
     * @throws UnsupportedOperationException
     */
    public List<E> subList(int arg0, int arg1) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation
     * 
     * @see java.util.Collection#toArray()
     * @throws UnsupportedOperationException
     */
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation
     * 
     * @see java.util.Collection#toArray(java.lang.Object[])
     * @throws UnsupportedOperationException
     */
    public <T> T[] toArray(T[] arg0) {
        throw new UnsupportedOperationException();
    }
}
