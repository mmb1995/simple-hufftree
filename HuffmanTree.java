// Matthew Blumenschein
// Section AP
// Ryan Timothy Feng
// Assignment 8
// Creates a HuffmanTree object that can be used to encode given text files and compress them
// based on the huffman algorithim. User can also provided a compressed file to be decoded back
// into its original state.

import java.util.*;
import java.io.*;

public class HuffmanTree {
   private HuffmanNode overallRoot; // root of the HuffmanTree
   
   // Creates a new HuffManTree that contains the given counts
   // Pre: counts is a valid array of counts
   public HuffmanTree(int[] counts) {
      Queue<HuffmanNode> characterCounts = new PriorityQueue<HuffmanNode>();
      for(int i = 0; i < counts.length; i++) {
         if(counts[i] != 0) {
            int frequency = counts[i];
            char symbol = (char)i;
            HuffmanNode newNode = new HuffmanNode(frequency, symbol);
            characterCounts.add(newNode);
         }
      }
      characterCounts.add(new HuffmanNode(1, (char)counts.length));
      buildTree(characterCounts);
      overallRoot = characterCounts.peek();
   }
   
   // writes an encoded file to the given PrintStream object
   public void write(PrintStream output) {
      List<String> encoding = new ArrayList<String>();
      write(output, overallRoot, encoding);
   }
   
   // Transverses through the given root, writing an encoded file to the given PrintStream object
   // Takes in a List to keep track of encodings while transversing the tree.
   private void write(PrintStream output, HuffmanNode root, List<String> encoding) {
      if(root.left == null && root.right == null) { // completed encoding
         output.println((int)root.symbol);
         for(int i = 0; i < encoding.size(); i++) {
            output.print(encoding.get(i));
         }
         output.println();
         encoding.remove(encoding.size() - 1);
      } else if(root != null)  {
         encoding.add("" + 0);
         write(output, root.left, encoding);
         encoding.add("" + 1);
         write(output, root.right, encoding);
         if(encoding.size() > 0) {
            encoding.remove(encoding.size() - 1);
         }
      }
   }
   
   // Creates a Huffman tree out of the given code file provided by the scanner
   // Pre: the scanner contains a tree in the valid format
   public HuffmanTree(Scanner input) {
      overallRoot = new HuffmanNode(-1);
      while(input.hasNextLine()) {
         int n = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         overallRoot = constructTree(code, n,  overallRoot);
      }
   }
   
   // Constructs a HuffmanTree from the given part of a code file, the given ascii value,
   // and the given root. Returns a HuffmanNode.
   private HuffmanNode constructTree(String code, int n, HuffmanNode root) {
      if(root == null && code.length() == 0) {
         char symbol = (char)n;
         root = new HuffmanNode(-1, symbol);
      } else {
         if(root == null) {
            root = new HuffmanNode(-1);
         }
         if(code.charAt(0) == '0') {
            root.left = constructTree(code.substring(1), n, root.left);
         } else {
            root.right = constructTree(code.substring(1), n, root.right);
         }
      }
      return root;
   }
   
   // Takes in a compressed file and decodes it bit by bit back into its original state,
   // which is outputed to the given PrintStream object. The process ends once the given
   // eof value is reached.
   // Pre: input file is not empty and is in valid format
   public void decode(BitInputStream input, PrintStream output, int eof) {
      int end = -1;
      while(end != eof) {
         HuffmanNode current = overallRoot;
         
         // Transverses through the tree
         while(current.left != null || current.right != null) {
            int bit = input.readBit();
            if(bit == 0) {
               current = current.left;
            } else {
               current = current.right;
            }
         }
         int ascii = (int)current.symbol;
         if(ascii != eof) {
            output.write(ascii);
         } else {
            end = eof;
         }
      }
   }
   
   // Builds a HuffmanTree using the given PriorityQueue of counts
   private void buildTree(Queue<HuffmanNode> characterCounts) {
      while(characterCounts.size() > 1) {
         HuffmanNode left = characterCounts.remove();
         HuffmanNode right = characterCounts.remove();
         int parentFrequency = left.frequency + right.frequency;
         characterCounts.add(new HuffmanNode(parentFrequency, left, right));
         HuffmanNode newNode = new HuffmanNode(parentFrequency, left, right);
      }
   }
   
   // HuffManNodes store a single node of a HuffmanTree. Implements comparable to allow 
   // for comparison between nodes.
   private class HuffmanNode implements Comparable<HuffmanNode> {
      public int frequency; 
      public char symbol; // character value
      public HuffmanNode left;
      public HuffmanNode right;
      
      // Creates a HuffmanNode with the given frequency and symbol
      public HuffmanNode(int frequency, char symbol) {
         this(frequency, symbol, null, null);
      }
      
      // Creates a HuffmanNode with the given frequency
      public HuffmanNode(int frequency) {
         this(frequency, null, null);
      }
      
      // Creates a HuffmanNode with its given frequency and children Nodes
      public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
         this.frequency = frequency;
         this.left = left;
         this.right = right;
      }
      
      // Creates a HuffmanNode with its given frequenxy, symbol, and children Nodes
      public HuffmanNode(int frequency, char symbol, HuffmanNode left, HuffmanNode right) {
         this.frequency = frequency;
         this.symbol = symbol;
         this.left = left;
         this.right = right;
      }
      
      // compares a HuffmanNode to a passed in HuffmanNode, returns a positive number if the
      // passed in HuffmnNode is smaller than the other, a negative number if the passed in
      // HuffmanNode is greater than the other, and 0 if the two HuffmanNodes are equal
      public int compareTo(HuffmanNode other) {
         if(this.frequency != other.frequency) {
            return this.frequency - other.frequency;
         } else {
            return 0;
         }
      }
   }
}
