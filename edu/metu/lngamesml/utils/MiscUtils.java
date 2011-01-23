package edu.metu.lngamesml.utils;

import weka.core.Utils;

import java.io.*;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Oct 22, 2010
 * Time: 5:47:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class MiscUtils {
    public static int[] removeIntElement(int[] source, int rem) {
        int del = 0;
        for (int i = 0; i < source.length; i++) {
            if (source[i] == rem) {
                del = i;
                break;
            }
        }
        int result[] = new int[source.length - 1];
        System.arraycopy(source, 0, result, 0, del);
        if (source.length != del) {
            System.arraycopy(source, del + 1, result, del, source.length - del - 1);
        }
        return result;
    }


    public static int chooseRandomIndexBasedOnWeights(double[] weights,
                                                      Random random) {
        double probSum = Utils.sum(weights);
        double val = random.nextDouble() * probSum;
        int index = 0;
        double sum = 0.0;
        while ((sum <= val) && (index < weights.length)) {
            sum += weights[index++];
        }
        return index - 1;
    }

    public static int poisson(double c, Random random) {
        int x = 0;
        double t = 0.0;
        while (true) {
            t -= Math.log(random.nextDouble()) / c;
            if (t > 1.0) {
                return x;
            }
            x++;
        }
    }

    public static int getAgreementsInComitee(int votes[], int vote) {
        int agreements = 0;
        for (int currentVote : votes) {
            if (currentVote == vote) {
                agreements++;
            }
        }
        return agreements;
    }

    //Borrowed from: http://www.javaworld.com/javaworld/javatips/jw-javatip76.html?page=2
    // returns a deep copy of an object

    public static Object deepCopy(Object oldObj) throws Exception {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            ByteArrayOutputStream bos =
                    new ByteArrayOutputStream(); // A
            oos = new ObjectOutputStream(bos); // B
            // serialize and pass the object
            oos.writeObject(oldObj);   // C
            oos.flush();               // D
            ByteArrayInputStream bin =
                    new ByteArrayInputStream(bos.toByteArray()); // E
            ois = new ObjectInputStream(bin);                  // F
            // return the new object
            return ois.readObject(); // G
        }
        catch (Exception e) {
            System.out.println("Exception in ObjectCloner = " + e);
            throw (e);
        }
        finally {
            oos.close();
            ois.close();
        }
    }

    public static String getStackTraceString(Exception ex) {
        StringWriter stackTraceWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stackTraceWriter));
        return "*** STACK TRACE ***\n" + stackTraceWriter.toString();
    }

}
