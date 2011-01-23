package edu.metu.lngamesml.core;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 12/30/10
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */

public interface BaseCategoryObject extends Serializable, Cloneable{
    /**
     * Returns a multi-line String with key=value pairs.
     * @return a String representation of this class.
     */
    public String toString();

    /**
     * Compares object equality. When using Hibernate, the primary key should
     * not be a part of this comparison.
     * @param o object to compare to
     * @return true/false based on equality tests
     */
    public boolean equals(Object o);

    /**
     * When you override equals, you should override hashCode. See "Why are
     * equals() and hashCode() importation" for more information:
     * http://www.hibernate.org/109.html
     * @return hashCode
     */
    public int hashCode();

    public Object clone() throws CloneNotSupportedException;
}
