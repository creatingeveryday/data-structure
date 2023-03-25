package algorithm;

import java.util.Arrays;

public class Dijkstra {

    public static void main(String[] args) {
        System.out.println("start");

        Graph graph = new Graph(6);
        graph.settingCost(0, 1, 7);
        graph.settingCost(0, 2, 9);
        graph.settingCost(0, 5, 14);
        graph.settingCost(1, 2, 10);
        graph.settingCost(1, 3, 15);
        graph.settingCost(2, 3, 11);
        graph.settingCost(2, 5, 2);
        graph.settingCost(3, 4, 7);
        graph.settingCost(4, 5, 9);

        graph.startDijkstra(0);
    }
}


class Graph {

    private int NodeCount;
    private int[][] maps;

    public Graph(int nodeCount) {
        this.NodeCount = nodeCount;
        this.maps = new int[nodeCount][nodeCount];

        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                maps[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    public void settingCost(int start, int end, int cost) {
        maps[start][end] = cost;
        maps[end][start] = cost;
    }

    public void printAllGraph(int[][] maps) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < maps.length; i++) {
            for (int j = 0; j < maps.length; j++) {
                if (maps[i][j] == Integer.MAX_VALUE) {
                    sb.append("∞  ");

                } else {
                    sb.append(maps[i][j] + "  ");
                }
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }


    public void startDijkstra(int startNode) {
        printAllGraph(maps);


        // 0단계: 초기화
        System.out.println("0단계 초기화");

        int linkedDistance[] = new int[NodeCount]; // 연결된 노드의 비용
        int distance[] = new int[NodeCount];// 시작 노드부터 각 노드까지의 총 최소비용거리
        boolean[] check = new boolean[NodeCount];// 해당 노드를 방문했는지 체크
        String[] shortestRoute = new String[NodeCount]; //해당 노드까지 최단 경로


        // distance 초기화
        for (int i = 0; i < NodeCount; i++) {
            distance[i] = Integer.MAX_VALUE;
            linkedDistance[i] = Integer.MAX_VALUE;
        }

        // 시작 노드 초기화
        distance[startNode] = 0;
        check[startNode] = true;
        shortestRoute[startNode] = "자기자신";

        printDistanceArray(distance);
        System.out.println(Arrays.toString(check));
        System.out.println("최단 경로: " + Arrays.toString(shortestRoute));


        // 1단계: 연결 노드의 최소 비용 갱신
        System.out.println("\n 1단계: 연결 노드의 최소 비용 갱신");

        for (int i = 0; i < NodeCount; i++) {
            if (!check[i] && maps[startNode][i] != Integer.MAX_VALUE) {
                linkedDistance[i] = maps[startNode][i];
                distance[i] = maps[startNode][i];
                shortestRoute[i] = startNode + "=>" + i;
            }
        }

        printDistanceArray(distance);
        System.out.println(Arrays.toString(check));
        System.out.println("최단 경로: " + Arrays.toString(shortestRoute));

        // 2단계: 최소비용 경로 계산
        System.out.println("\n 2단계: 최소비용 경로 계산");

        for (int a = 0; a < NodeCount - 1; a++) {

            int minDistance = Integer.MAX_VALUE;
            int minDistanceNodeIndex = -1;

            // 방문하지 않은 노드 중 최소비용을 갖는 노드 탐색
            for (int i = 0; i < NodeCount; i++) {
                if (!check[i]) {
                    if (distance[i] < minDistance) {
                        minDistance = distance[i];
                        minDistanceNodeIndex = i;
                    }
                }
            }

            // 해당 노드를 거쳐서 특정한 노드로 가는 경우를 고려하여 최소비용을 갱신
            check[minDistanceNodeIndex] = true;
            for (int i = 0; i < NodeCount; i++) {
                if (!check[i] && maps[minDistanceNodeIndex][i] != Integer.MAX_VALUE) {

                    if (distance[minDistanceNodeIndex] + maps[minDistanceNodeIndex][i] < distance[i]) {

                        distance[i] = distance[minDistanceNodeIndex] + maps[minDistanceNodeIndex][i];
                        shortestRoute[i] = startNode + "=>" + minDistanceNodeIndex + "=>" + i;
                    }
                }
            }

            printDistanceArray(distance);
            System.out.println("최단 경로: " + Arrays.toString(shortestRoute));
            System.out.println("============================================");
        }

    }


    private void printDistanceArray(int[] distance) {
        // 결과값 출력
        for (int i = 0; i < NodeCount; i++) {
            if (distance[i] == Integer.MAX_VALUE) System.out.print("∞ ");
            else System.out.print(distance[i] + " ");
        }
        System.out.println("");
    }
}
