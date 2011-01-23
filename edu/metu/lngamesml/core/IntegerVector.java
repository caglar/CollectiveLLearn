package edu.metu.lngamesml.core;

import edu.metu.lngamesml.utils.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Oct 28, 2010
 * Time: 6:29:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class IntegerVector {

    private static final long serialVersionUID = 1L;

    protected int[] array;

    public IntegerVector() {
        this.array = new int[0];
    }

    public IntegerVector(int[] toCopy) {
        this.array = new int[toCopy.length];
        System.arraycopy(toCopy, 0, this.array, 0, toCopy.length);
    }

    public IntegerVector(IntegerVector toCopy) {
        this(toCopy.getArrayRef());
    }

    public int numValues() {
        return this.array.length;
    }

    public void setValue(int i, int v) {
        if (i >= this.array.length) {
            setArrayLength(i + 1);
        }
        this.array[i] = v;
    }

    public void addToValue(int i, int v) {
        if (i >= this.array.length) {
            setArrayLength(i + 1);
        }
        this.array[i] += v;
    }

    public void addValues(IntegerVector toAdd) {
        addValues(toAdd.getArrayRef());
    }

    public void addValues(int[] toAdd) {
        if (toAdd.length > this.array.length) {
            setArrayLength(toAdd.length);
        }
        for (int i = 0; i < toAdd.length; i++) {
            this.array[i] += toAdd[i];
        }
    }

    public void subtractValues(IntegerVector toSubtract) {
        subtractValues(toSubtract.getArrayRef());
    }

    public void subtractValues(int[] toSubtract) {
        if (toSubtract.length > this.array.length) {
            setArrayLength(toSubtract.length);
        }
        for (int i = 0; i < toSubtract.length; i++) {
            this.array[i] -= toSubtract[i];
        }
    }

    public void addToValues(int toAdd) {
        for (int i = 0; i < this.array.length; i++) {
            this.array[i] = this.array[i] + toAdd;
        }
    }

    public void scaleValues(int multiplier) {
        for (int i = 0; i < this.array.length; i++) {
            this.array[i] = this.array[i] * multiplier;
        }
    }

    // returns 0.0 for values outside of range

    public int getValue(int i) {
        return ((i >= 0) && (i < this.array.length)) ? this.array[i] : 0;
    }

    public int sumOfValues() {
        int sum = 0;
        for (int element : this.array) {
            sum += element;
        }
        return sum;
    }

    public int maxIndex() {
        int max = 0;
        for (int i = 1; i < this.array.length; i++) {
            if ((max < 0) || (this.array[i] > this.array[max])) {
                max = i;
            }
        }
        return max;
    }

    public int maxValue() {
        return this.array[maxIndex()];
    }

    public void normalize() {
        scaleValues(1 / sumOfValues());
    }

    public int numNonZeroEntries() {
        int count = 0;
        for (double element : this.array) {
            if (element != 0.0) {
                count++;
            }
        }
        return count;
    }

    public int minWeight() {
        if (this.array.length > 0) {
            int min = this.array[0];
            for (int i = 1; i < this.array.length; i++) {
                if (this.array[i] < min) {
                    min = this.array[i];
                }
            }
            return min;
        }
        return 0;
    }

    public int[] getArrayCopy() {
        int[] aCopy = new int[this.array.length];
        System.arraycopy(this.array, 0, aCopy, 0, this.array.length);
        return aCopy;
    }

    public int[] getArrayRef() {
        return this.array;
    }

    protected void setArrayLength(int l) {
        int[] newArray = new int[l];
        int numToCopy = this.array.length;
        if (numToCopy > l) {
            numToCopy = l;
        }
        System.arraycopy(this.array, 0, newArray, 0, numToCopy);
        this.array = newArray;
    }

    public void getSingleLineDescription(StringBuilder out) {
        getSingleLineDescription(out, numValues());
    }

    public void getSingleLineDescription(StringBuilder out, int numValues) {
        out.append("{");
        for (int i = 0; i < numValues; i++) {
            if (i > 0) {
                out.append("|");
            }
            out.append(StringUtils.doubleToString(getValue(i), 3));
        }
        out.append("}");
    }

    public void getDescription(StringBuilder sb, int indent) {
        getSingleLineDescription(sb);
    }
}
