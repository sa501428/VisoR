package com.visor.model;

import java.util.ArrayList;

public class Filter {

    private final String filterShader;
    private final String id;
    private final ArrayList<UniformPair> myUnifromPairs = new ArrayList<UniformPair>();
    private int myProgram;

    public Filter(String id, String filterShader) {
        this.id = id;
        this.filterShader = filterShader;
    }

    public String getFilterShader() {
        return filterShader;
    }

    @Override
    public String toString() {
        return id;
    }

    public int getProgram() {
        return myProgram;
    }

    public void setProgram(int myProgram) {
        this.myProgram = myProgram;
    }

    public void addUniformPairs(ArrayList<UniformPair> uniformPair) {
        myUnifromPairs.addAll(uniformPair);
    }

    public ArrayList<UniformPair> getUniformPairs() {

        return myUnifromPairs;
    }
}
