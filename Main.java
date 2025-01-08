/**

 
 */
import java.util.*;

class Graph 
{
    private final Map<String, List<Edge>> adjacencyList;

    public Graph() 
    {
        this.adjacencyList = new HashMap<>();
    }

    public void addEdge(String source, String destination, double cost, double time, double distance) 
    {
        // Check if the source node already exists in the adjacency list, otherwise initialize it
        this.adjacencyList.putIfAbsent(source, new ArrayList<>());

        // Check if the edge already exists to prevent duplicates
        boolean edgeExists = this.adjacencyList.get(source).stream()
            .anyMatch(edge -> edge.getDestination().equals(destination) && 
                              edge.getCost() == cost &&
                              edge.getTime() == time &&
                              edge.getDistance() == distance);

        if (!edgeExists) 
        {
            // Add a new edge from source to destination
            this.adjacencyList.get(source).add(new Edge(destination, cost, time, distance));
        }
    }


    // Calculates the details (total cost, time, and distance) for all provided paths
    public void calculatePathDetails(List<List<String>> paths) 
    {
    	System.out.println("\n========================================================================================================================================================================================================");
        System.out.println("                                                           Path Details Calculation By Train");
        System.out.println("========================================================================================================================================================================================================\n");
   

        for (int i = 0; i < paths.size(); i++) 
        {
            List<String> path = paths.get(i);
            double totalCost = 0.0;
            double totalTime = 0.0;
            double totalDistance = 0.0;

            // Display the path index and its nodes
            System.out.println("Path " + (i + 1) + ": " + String.join(" -> ", path));

            for (int j = 0; j < path.size() - 1; j++) 
            {
                String current = path.get(j);
                String next = path.get(j + 1);
                
                // Find the edge connecting the current node to the next node
                Edge edge = adjacencyList.getOrDefault(current, new ArrayList<>()).stream()
                        .filter(e -> e.getDestination().equals(next))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Invalid path segment: " + current + " -> " + next));

                // Accumulate total cost, time, and distance
                totalCost += edge.getCost();
                totalTime += edge.getTime();
                totalDistance += edge.getDistance();
            }
            
            // Display the accumulated details for the current path
            System.out.printf("  Total Cost: RM%.2f\n", totalCost);
            System.out.printf("  Total Time: %.0f mins\n", totalTime);
            System.out.printf("  Total Distance: %.2f km\n\n", totalDistance);
        }
    }

    // Finds the shortest path from startNode to endNode step by step
    public void findExactShortestPath(String startNode, String endNode) 
    {
        Set<String> visited = new HashSet<>(); // Keeps track of visited nodes
        List<String> path = new ArrayList<>(); // Stores the final path
        String current = startNode; // Start from the initial node
        path.add(current);

        System.out.println("\n========================================================================================================================================================================================================");
        System.out.println("                                                           Shortest Pathfinding Process by Train");
        System.out.println("========================================================================================================================================================================================================\n");
   
        System.out.println("Starting Pathfinding from " + startNode + " to " + endNode);

        while (!current.equals(endNode)) 
        {
            visited.add(current); // Mark the current node as visited
            List<Edge> edges = adjacencyList.getOrDefault(current, new ArrayList<>());

            final String currentCopy = current; // Make current effectively final for lambda expressions

            // Display all possible routes from the current node
            System.out.println("\nFrom " + current + ", checking possible routes:");
            edges.forEach(edge -> 
            {
                System.out.println(currentCopy + " -> " + edge.getDestination() + " (" + edge.getDistance() + " km)");
            });

            // Find the shortest unvisited edge
            Edge shortestEdge = edges.stream()
                    .filter(edge -> !visited.contains(edge.getDestination()))
                    .min(Comparator.comparingDouble(Edge::getDistance))
                    .orElse(null);

            if (shortestEdge == null) 
            {
                System.out.println("No unvisited paths available from " + current);
                break;
            }

            current = shortestEdge.getDestination(); // Move to the next node
            path.add(current); // Add the node to the path

            // Display the selected path after choosing the next node
            System.out.println("\nSelected Path: " + path);
        }

        // Display the final path
        System.out.println("\n========================================================================================================================================================================================================");
        System.out.println("                                                           Shortest Pathfinding Display By Train ");
        System.out.println("========================================================================================================================================================================================================");
        System.out.println("\nFinal Path: " + String.join(" -> ", path));
    }
}

class Edge 
{
    private final String destination; // Destination node of the edge
    private final double cost; // Cost of traveling this edge
    private final double time; // Time taken to travel this edge
    private final double distance; // Distance of the edge
    
    // Constructor to initialize edge attributes
    public Edge(String destination, double cost, double time, double distance) 
    {
        this.destination = destination;
        this.cost = cost;
        this.time = time;
        this.distance = distance;
    }

    public String getDestination() 
    {
        return destination;
    }

    public double getCost() 
    {
        return cost;
    }

    public double getTime() 
    {
        return time;
    }

    public double getDistance() 
    {
        return distance;
    }
}

public class Main
{
    public static void main(String[] args) 
    {
        Graph graph = new Graph();

        // Add edges for Path 1
        graph.addEdge("Meidaimae St", "Shimo-Kitazawa St", 4, 4, 3);
        graph.addEdge("Shimo-Kitazawa St", "Shinjuku St", 4, 6, 5.1);
        graph.addEdge("Shinjuku St", "Kaminakazato St", 16, 24, 9.8);
        graph.addEdge("Kaminakazato St", "Oji St", 2, 2, 1);
        graph.addEdge("Oji St", "Urawa St", 13, 23, 15.5);
        graph.addEdge("Urawa St", "Omiya St", 5, 7, 6.5);
        graph.addEdge("Omiya St", "Okegawa St", 10, 12, 11.8);
        graph.addEdge("Okegawa St", "Okabe St", 25, 42, 38.4);
        graph.addEdge("Okabe St", "Takasaki St", 18, 28, 24.6);

        // Add edges for Path 2
        graph.addEdge("Meidaimae St", "Shibuya St", 5, 7, 5.3);
        graph.addEdge("Shibuya St", "Shinbashi St", 7, 14, 6);
        graph.addEdge("Shinbashi St", "Ueno St", 7, 31, 3);
        graph.addEdge("Ueno St", "Oku St", 8, 11, 5.7);
        graph.addEdge("Oku St", "Urawa St", 12, 18, 25);
        graph.addEdge("Urawa St", "Omiya St", 5, 7, 6.5);
        graph.addEdge("Omiya St", "Okegawa St", 10, 12, 11.8);
        graph.addEdge("Okegawa St", "Okabe St", 25, 42, 38.4);
        graph.addEdge("Okabe St", "Takasaki St", 18, 28, 24.6);

        // Add edges for Path 3
        graph.addEdge("Meidaimae St", "Shinjuku St", 5, 9, 7.6);
        graph.addEdge("Shinjuku St", "Akabane St", 10, 14, 15);
        graph.addEdge("Akabane St", "Koga St", 35, 56, 66);
        graph.addEdge("Koga St", "Itakura-Toyodamae St", 5, 7, 16);
        graph.addEdge("Itakura-Toyodamae St", "Sano St", 14, 19, 19.1);
        graph.addEdge("Sano St", "Ashikaga St", 9, 14, 13);
        graph.addEdge("Ashikaga St", "Kiryu St", 6, 24, 6.3);
        graph.addEdge("Kiryu St", "Midori, Gunma St", 9, 13, 7.3);
        graph.addEdge("Midori, Gunma St", "Takasaki St", 28, 99, 37);

        // Add edges for Path 4
        graph.addEdge("Meidaimae St", "Shinjuku St", 5, 9, 7.6);
        graph.addEdge("Shinjuku St", "Ikebukaro St", 30, 6, 11.6);
        graph.addEdge("Ikebukaro St", "Yokoze St", 22, 78, 106);
        graph.addEdge("Yokoze St", "Chichibu St", 5, 9, 1.6);
        graph.addEdge("Chichibu St", "Tamamura St", 73, 177, 56);
        graph.addEdge("Tamamura St", "Honjo St", 5, 7, 8.5);
        graph.addEdge("Honjo St", "Kamisato St", 5, 4, 5.4);
        graph.addEdge("Kamisato St", "Yorii St", 25, 66, 25);
        graph.addEdge("Yorii St", "Takasaki St", 19, 45, 42);

        // Add edges for Path 5
        graph.addEdge("Meidaimae St", "Tokyo St", 4, 9, 4);
        graph.addEdge("Tokyo St", "Nerima St", 6, 22, 8.1);
        graph.addEdge("Nerima St", "Ikebukuro St", 6, 14, 7.1);
        graph.addEdge("Ikebukuro St", "Kumagaya St", 35, 68, 57.3);
        graph.addEdge("Kumagaya St", "Fukaya St", 38, 8, 15);
        graph.addEdge("Fukaya St", "Yorii St", 50, 68, 13);
        graph.addEdge("Yorii St", "Kamikawa St", 11, 23, 16);
        graph.addEdge("Kamikawa St", "Takasaki St", 13, 22, 16.5);

        // Define all possible paths
        List<List<String>> paths = new ArrayList<>();
        
        // This list represents predefined paths to calculate their details.
        paths.add(Arrays.asList("Meidaimae St", "Shimo-Kitazawa St", "Shinjuku St", "Kaminakazato St", "Oji St", "Urawa St", "Omiya St", "Okegawa St", "Okabe St", "Takasaki St"));
        paths.add(Arrays.asList("Meidaimae St", "Shibuya St", "Shinbashi St", "Ueno St", "Oku St", "Urawa St", "Omiya St", "Okegawa St", "Okabe St", "Takasaki St"));
        paths.add(Arrays.asList("Meidaimae St", "Shinjuku St", "Akabane St", "Koga St", "Itakura-Toyodamae St", "Sano St", "Ashikaga St", "Kiryu St", "Midori, Gunma St", "Takasaki St"));
        paths.add(Arrays.asList("Meidaimae St", "Shinjuku St", "Ikebukaro St", "Yokoze St", "Chichibu St", "Tamamura St", "Honjo St", "Kamisato St", "Yorii St", "Takasaki St"));
        paths.add(Arrays.asList("Meidaimae St", "Tokyo St", "Nerima St", "Ikebukuro St", "Kumagaya St", "Fukaya St", "Yorii St", "Kamikawa St", "Takasaki St"));

        // Calculate and display details for all paths
        graph.calculatePathDetails(paths);

        // Find the shortest path using dijktstra alogorithm
        graph.findExactShortestPath("Meidaimae St", "Takasaki St");
    }
}