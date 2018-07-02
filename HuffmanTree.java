import java.util.*;
import java.io.*;


public class HuffmanTree {
   private PriorityQueue<HuffmanNode> pq;
   private HuffmanNode overallRoot;
   
   public HuffmanTree(int[] counts) {
      pq = new PriorityQueue<HuffmanNode>();
      for(int i = 0; i < counts.length; i++) {
         if(counts[i] != 0) {
         char result = (char)(i);
         HuffmanNode node = new HuffmanNode(counts[i],result);
         pq.add(node);
         System.out.println(pq.toString());
      }
      }
      HuffmanNode node = new HuffmanNode(1, 'E');
      pq.add(node);
      overallRoot = makeTree();
      }
      
      private HuffmanNode makeTree() {
      if(pq.aize() == 1)  {
         return root;
      } else {
         HuffmanNode node1 = pq.remove();
         HuffmanNode node2 = pq.remove();
         HuffmanNode newNode = new HuffmanNode(node1.frequency + node2.frequency,'.');
         newNode.left = makeTree(node1);
         newNode.right = makeTree(node2);
         pq.add(newNode);
         } 
         return root;  
      }   
   
   public HuffmanTree(Scanner input) {
   }
   
   public void write(PrintStream output) {
   }
   
   /*public void decode(BitInputStream input, Printstream output,int eof) {
   }
*/}   
   
   
   
