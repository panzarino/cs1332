import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Zachary Panzarino
 * @userid zpanzarino3
 * @GTID 903305160
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When exploring a vertex, make sure to explore in the order that the
     * adjacency list returns the neighbors to you. Failure to do so may cause
     * you to lose points.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List},
     * {@code java.util.Queue}, and any classes that implement the
     * aforementioned interfaces, as long as it is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> breadthFirstSearch(Vertex<T> start,
                                            Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start cannot be null.");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null.");
        }
        Map<Vertex<T>, List<Edge<T>>> list = graph.getAdjList();
        if (!list.containsKey(start)) {
            throw new IllegalArgumentException("Start is not in graph.");
        }
        List<Vertex<T>> output = new LinkedList<Vertex<T>>();
        Queue<Vertex<T>> queue = new LinkedList<Vertex<T>>();
        output.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            for (Edge<T> edge : list.get(queue.remove())) {
                if (!output.contains(edge.getV())) {
                    queue.add(edge.getV());
                    output.add(edge.getV());
                }
            }
        }
        return output;
    }


    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When deciding which neighbors to visit next from a vertex, visit the
     * vertices in the order presented in that entry of the adjacency list.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * most if not all points for this method.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List}, and
     * any classes that implement the aforementioned interfaces, as long as it
     * is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
                                            Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start cannot be null.");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null.");
        }
        Map<Vertex<T>, List<Edge<T>>> list = graph.getAdjList();
        if (!list.containsKey(start)) {
            throw new IllegalArgumentException("Start is not in graph.");
        }
        List<Vertex<T>> output = new LinkedList<Vertex<T>>();
        Set<Vertex<T>> explored = new HashSet<Vertex<T>>();
        depthFirstSearchHelper(list, start, explored, output);
        return output;
    }

    /**
     * Recursive helper method for depth first search
     * @param list List of edges from graph
     * @param current Current vertex in recursive calls
     * @param explored Set of explored vertices
     * @param output Set of vertices to be returned
     * @param <T> the generic typing of the data
     */
    private static <T> void depthFirstSearchHelper(
        Map<Vertex<T>, List<Edge<T>>> list, Vertex<T> current,
        Set<Vertex<T>> explored, List<Vertex<T>> output
    ) {
        if (!explored.contains(current)) {
            output.add(current);
            explored.add(current);
            for (Edge<T> edge : list.get(current)) {
                depthFirstSearchHelper(list, edge.getV(), explored, output);
            }
        }
    }


    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing infinity)
     * if no path exists.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and {@code java.util.Set} and any class that
     * implements the aforementioned interfaces, as long as it's efficient.
     *
     * You should implement the version of Dijkstra's where you terminate the
     * algorithm once either all vertices have been visited or the PQ becomes
     * empty.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null, or if start
     *  doesn't exist in the graph.
     * @param <T> the generic typing of the data
     * @param start index representing which vertex to start at (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every other node
     *         in the graph
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                      Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start cannot be null.");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null.");
        }
        Map<Vertex<T>, List<Edge<T>>> list = graph.getAdjList();
        if (!list.containsKey(start)) {
            throw new IllegalArgumentException("Start is not in graph.");
        }
        Set<Vertex<T>> explored = new HashSet<Vertex<T>>();
        Map<Vertex<T>, Integer> output = new HashMap<Vertex<T>, Integer>();
        for (Vertex<T> vertex : list.keySet()) {
            output.put(vertex, Integer.MAX_VALUE);
        }
        PriorityQueue<Edge<T>> queue = new PriorityQueue<Edge<T>>();
        queue.add(new Edge<T>(start, start, 0));
        int distance = 0;
        while (!queue.isEmpty()) {
            Edge<T> edge = queue.poll();
            Vertex<T> vertex = edge.getV();
            if (!explored.contains(vertex)) {
                explored.add(vertex);
                if (output.get(edge.getU()) != Integer.MAX_VALUE) {
                    distance = output.get(edge.getU());
                }
                for (Edge<T> e : list.get(vertex)) {
                    if (
                        !explored.contains(e.getV())
                            && distance + edge.getWeight()
                            < output.get(edge.getV())
                    ) {
                        queue.add(e);
                    }
                }
                output.put(vertex, distance + edge.getWeight());
            }
        }
        return output;
    }


    /**
     * Runs Prim's algorithm on the given graph and return the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * opposite edge to the set as well. This is for testing purposes.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops into the MST.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface, as long as it's efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start cannot be null.");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null.");
        }
        Map<Vertex<T>, List<Edge<T>>> list = graph.getAdjList();
        if (!list.containsKey(start)) {
            throw new IllegalArgumentException("Start is not in graph.");
        }
        Set<Vertex<T>> explored = new HashSet<Vertex<T>>();
        explored.add(start);
        PriorityQueue<Edge<T>> queue = new PriorityQueue<Edge<T>>(
                graph.getEdges());
        Set<Edge<T>> output = new HashSet<Edge<T>>();
        boolean disconnected = false;
        while (!disconnected && explored.size() != graph.getVertices().size()) {
            Edge<T> toBeRemoved = null;
            boolean canBeChanged = true;
            for (Edge<T> edge : queue) {
                if (canBeChanged && explored.contains(edge.getU())
                        && !(explored.contains(edge.getU())
                        && explored.contains(edge.getV()))) {
                    explored.add(edge.getV());
                    output.add(edge);
                    output.add(new Edge<T>(edge.getV(), edge.getU(),
                            edge.getWeight()));
                    toBeRemoved = edge;
                    canBeChanged = false;
                } else if (canBeChanged && explored.contains(edge.getV())
                        && !(explored.contains(edge.getU())
                        && explored.contains(edge.getV()))) {
                    explored.add(edge.getU());
                    output.add(edge);
                    output.add(new Edge<T>(edge.getV(), edge.getU(),
                            edge.getWeight()));
                    toBeRemoved = edge;
                    canBeChanged = false;
                }
            }
            queue.remove(toBeRemoved);
            if (toBeRemoved == null) {
                disconnected = true;
            }
        }
        if (disconnected) {
            return null;
        } else {
            return output;
        }
    }
}