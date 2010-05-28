package edu.cglr.lngamesml.agents;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 19, 2010
 * Time: 5:58:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class GraphLink {

    double Capacity; // should be private
    double Weight;   // should be private for good practice
    String Word;
    int Id;

    public GraphLink(int id, double weight, double capacity) {
        this.Id = id; // This is defined in the outer class.
        this.Weight = weight;
        this.Capacity = capacity;
    }

    public GraphLink() { }

    public double getCapacity() {
        return Capacity;
    }

    public void setCapacity(double capacity) {
        Capacity = capacity;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getWord() {
        return Word;
    }

    public void setWord(String word) {
        Word = word;
    }

    public String toString() { // Always good for debugging
        return "E " + Id + " Word is: " + Word;
    }
}
