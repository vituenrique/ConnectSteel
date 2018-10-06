package com.example.jabas.connectsteel;

import java.util.Stack;
import java.util.ArrayList;

public class AllPaths {
    private boolean[] onPath;        // vertices in current path
    private Stack<Integer> path;     // the current path
    private int numberOfPaths;       // number of simple path
    private ArrayList<ArrayList<Integer>> paths;

    // show all simple paths from s to t - use DFS
    public AllPaths(Graph G, int s, int t) {
        onPath = new boolean[G.V()];
        path = new Stack<Integer>();
        paths = new ArrayList<ArrayList<Integer>>();
        dfs(G, s, t);
    }
    public ArrayList<ArrayList<Integer>> getPaths(){
        return paths;
    }


    // use DFS
    private void dfs(Graph G, int v, int t) {
        // add v to current path
        path.push(v);
        onPath[v] = true;

        // found path from s to t
        if (v == t) {
            processCurrentPath();
            numberOfPaths++;
        }

        // consider all neighbors that would continue path with repeating a node
        else {
            for (int w : G.adj(v)) {
                if (!onPath[w])
                    dfs(G, w, t);
            }
        }

        // done exploring from v, so remove from path
        path.pop();
        onPath[v] = false;
    }

    // this implementation just prints the path to standard output
    private void processCurrentPath() {

        ArrayList<Integer> aux = new ArrayList<Integer>();
        for (int v : path){
            aux.add(v);
        }

        if(!paths.contains(aux)) {
            paths.add(aux);
        }
    }

    // return number of simple paths between s and t
    public int numberOfPaths() {
        return numberOfPaths;
    }
}
