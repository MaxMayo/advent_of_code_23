package com.example.adventofcode2023.day_8;

import com.example.adventofcode2023.common.TestDriven;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.text.Format;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HauntedWasteland implements TestDriven<Integer> {

    String regex = "(?<nodeId>\\w{3}) = \\((?<leftId>\\w{3}), (?<rightId>\\w{3})\\)";
    Pattern pattern = Pattern.compile(regex);

    @Setter
    private class Node {
        private String nodeId;
        private String leftNodeId;
        private String rightNodeId;

        public Node leftNode;
        public Node rightNode;
        public final boolean endsWithZ;
        public final boolean endsWithA;

        public Node(String line) {
            var matcher = pattern.matcher(line);
            matcher.matches();
            nodeId = matcher.group("nodeId").trim();
            leftNodeId = matcher.group("leftId").trim();
            rightNodeId = matcher.group("rightId").trim();
            endsWithA = nodeId.charAt(2) == 'A';
            endsWithZ = nodeId.charAt(2) == 'Z';
        }

    }

    @SneakyThrows
    @Override
    public Integer runPartOne(BufferedReader bufferedReader) {

        String instructions = bufferedReader.readLine();
        bufferedReader.readLine();

        //construct all nodes
        Map<String, Node> nodeMapById = bufferedReader.lines()
                .map(Node::new)
                .map(node -> new AbstractMap.SimpleEntry<>(node.nodeId, node))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        //link all nodes
        nodeMapById.keySet().forEach(nodeId -> {
            Node node = nodeMapById.get(nodeId);
            Node leftNode = nodeMapById.get(node.leftNodeId);
            node.setLeftNode(leftNode);
            Node rightNode = nodeMapById.get(node.rightNodeId);
            node.setRightNode(rightNode);
        });

        int numSteps = 0;
        String targetNodeId = "ZZZ";
        String currentNodeId = "AAA";
        Node currentNode = nodeMapById.get(currentNodeId);
        Node nextNode = null;
        while (true) {
            for (int i = 0; i < instructions.length(); i++) {
                numSteps++;
                char instruction = instructions.charAt(i);
                nextNode = instruction == 'L' ? currentNode.leftNode : currentNode.rightNode;
                if (nextNode.nodeId.equals(targetNodeId)) {
                    return numSteps;
                } else {
                    currentNodeId = currentNode.nodeId;
                    currentNode = nextNode;
                }
            }

        }
    }


    @Override
    @SneakyThrows
    public Integer runPartTwo(BufferedReader bufferedReader) {
        String instructions = bufferedReader.readLine();
        bufferedReader.readLine();

        //construct all nodes
        Map<String, Node> nodeMapById = bufferedReader.lines()
                .map(Node::new)
                .map(node -> new AbstractMap.SimpleEntry<>(node.nodeId, node))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        //link all nodes
        nodeMapById.keySet().forEach(nodeId -> {
            Node node = nodeMapById.get(nodeId);
            Node leftNode = nodeMapById.get(node.leftNodeId);
            node.setLeftNode(leftNode);
            Node rightNode = nodeMapById.get(node.rightNodeId);
            node.setRightNode(rightNode);
        });

        int numSteps = 0;
        long numLongSteps = 0;

        List<Node> currentNodes = nodeMapById.values().stream().filter(node -> node.nodeId.charAt(2) == 'A').collect(Collectors.toList());
//        List<Node> nextNodes = new ArrayList<>(currentNodes.size());
//        Node nextNode;
//        int nodesEndingWithZ = 0;
//        int numNodes = currentNodes.size();
//        Node[] currentNodesArray = currentNodes.toArray(new Node[numNodes]);
//        Node currentNode;
//        int instructionsLength = instructions.length();
//        int j;
//        char instruction;

        // ints are too small. these print the result. bad for testing
//        return (int) workingButNotFastEnoughLoop(instructions, nodeMapById);
        return (int) unravellingForMaxSpeed(instructions, nodeMapById);

//        while (true) {
//            for (int i = 0; i < instructionsLength; i++) {
//                numSteps++;
//                numLongSteps++;
//                instruction = instructions.charAt(i);
//                for (j = 0; j < numNodes; j++) {
//                    currentNode = currentNodesArray[j];
//                    nextNode = instruction == 'L' ? currentNode.leftNode : currentNode.rightNode;
//                    currentNodesArray[j] = nextNode;
////                    currentNodes.set(j, nextNode);
//                    if (nextNode.endsWithZ) {
//                        nodesEndingWithZ++;
//                        if (nodesEndingWithZ >= 4) {
//                            System.out.flush();
//                            System.out.println(nodesEndingWithZ);
//                            System.out.println(numSteps);
//                            System.out.println(numLongSteps);
//                        }
//                    }
//                }
//                if (nodesEndingWithZ == numNodes) {
//                    System.out.println(numLongSteps);
//                    return numSteps;
//                } else {
////                    System.out.println(nodesEndingWithZ);
//                    nodesEndingWithZ = 0;
//                }
//            }
//        }
    }

    private long unravellingForMaxSpeed(String instructions, Map<String, Node> nodeMapById) {
        List<Node> currentNodes = nodeMapById.values().stream().filter(node -> node.nodeId.charAt(2) == 'A').toList();
        long numLongSteps = -1;
        int numNodes = currentNodes.size();
        Node[] currentNodesArray = currentNodes.toArray(new Node[numNodes]);
        Node currentNode;
        int instructionsLength = instructions.length();
        int i;
        char instruction;
        Node node1 = currentNodesArray[0];
        Node node2 = currentNodesArray[1];
        Node node3 = currentNodesArray[2];
        Node node4 = currentNodesArray[3];
        Node node5 = currentNodesArray[4];
        Node node6 = currentNodesArray[5];
        NumberFormat format = NumberFormat.getNumberInstance();
        char[] instructionCharArray = instructions.toCharArray();
        long numInstructionLoops = 0;
        char left = 'L';
        while (true) {
            numInstructionLoops++;
            int index = 0;
            while (index < instructionsLength) {
                numLongSteps += 1;
                instruction = instructionCharArray[index];
                index += 1;
                if (instruction == left) {
                    node1 = node1.leftNode;
                    node2 = node2.leftNode;
                    node3 = node3.leftNode;
                    node4 = node4.leftNode;
                    node5 = node5.leftNode;
                    node6 = node6.leftNode;
                } else {
                    node1 = node1.rightNode;
                    node2 = node2.rightNode;
                    node3 = node3.rightNode;
                    node4 = node4.rightNode;
                    node5 = node5.rightNode;
                    node6 = node6.rightNode;
                }
                if (node1.endsWithZ
                        && node2.endsWithZ
                        && node3.endsWithZ
                        && node4.endsWithZ
                        && node5.endsWithZ
                        && node6.endsWithZ) {
                    //found
                    System.out.println("found");
                    System.out.println("num long steps: " + numLongSteps);
                    return numLongSteps;
                }
//                i = i + 1;
//                numLongSteps = numLongSteps + 1L;
            }
            if (numInstructionLoops % 10_000_000L == 0L) {
                System.out.println(format.format(numLongSteps));
            }
        }
    }

    private long workingButNotFastEnoughLoop(String instructions, Map<String, Node> nodeMapById) {
        List<Node> currentNodes = nodeMapById.values().stream().filter(node -> node.nodeId.charAt(2) == 'A').toList();
        int numLongSteps = 0;
        Node nextNode;
        int nodesEndingWithZ = 0;
        int numNodes = currentNodes.size();
        Node[] currentNodesArray = currentNodes.toArray(new Node[numNodes]);
        Node currentNode;
        int instructionsLength = instructions.length();
        int j;
        char instruction;
        while (true) {
            for (int i = 0; i < instructionsLength; i++) {
                numLongSteps++;
                instruction = instructions.charAt(i);
                for (j = 0; j < numNodes; j++) {
                    currentNode = currentNodesArray[j];
                    nextNode = instruction == 'L' ? currentNode.leftNode : currentNode.rightNode;
                    currentNodesArray[j] = nextNode;
                    if (nextNode.endsWithZ) {
                        nodesEndingWithZ++;
                        if (nodesEndingWithZ >= 4) {
                            System.out.flush();
                            System.out.println(nodesEndingWithZ);
                            System.out.println(numLongSteps);
                        }
                    }
                }
                if (nodesEndingWithZ == numNodes) {
                    System.out.println(numLongSteps);
                    return numLongSteps;
                } else {
//                    System.out.println(nodesEndingWithZ);
                    nodesEndingWithZ = 0;
                }
            }
        }
    }
}

