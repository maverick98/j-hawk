package balancedtree;

/**
 *
 * @author manosahu
 */
public class AVLTree {
    
    
    
  static class Node {

        int val;   
        int ht;     
        Node left;  
        Node right;  
    }
    
   
    public static Node insert(Node root , int val){
        return  put(root,val);
    }
     
    private static int height(Node x){
        if(x == null){
            return -1;
        }
        return x.ht;
    }
    private static Node put(Node x, int val){
        if(x == null){
            Node node = new Node();
            node.ht = 0;
            node.val= val;
            return node;
        }
        if(val > x.val){
            x.right = put(x.right,val);
        }else if(val < x.val){
            x.left = put(x.left,val);
        }else{
            x.val= val;
            return x;
        }
        x.ht = 1+Math.max(height(x.left), height(x.right));
        return balance(x);
    }
    
    private static Node balance(Node x){
        
        if(balanceFactor(x) < -1){
            if(balanceFactor(x.right) > 0){
                x.right = rotateRight(x.right);
            }
            x = rotateLeft(x);
        }else if(balanceFactor(x) > 1){
            if(balanceFactor(x.left) < 0){
                x.left = rotateLeft(x.left);
            }
            x = rotateRight(x);
        }
        
        return x;
    }
    
    
    private  static int balanceFactor(Node x){
        return height(x.left) - height(x.right);
    }
    
    private static  Node rotateLeft(Node x){
        
        Node y = x.right;
        x.right = y.left;
        y.left =x;
        x.ht = 1 + Math.max(height(x.left) , height(x.right));
        y.ht = 1 + Math.max(height(y.left) , height(y.right));
        return y;
                
    }
    
     private static Node rotateRight(Node x){
        
        Node y = x.left;
        x.left = y.right;
        y.right =x;
        x.ht = 1 + Math.max(height(x.left) , height(x.right));
        y.ht = 1 + Math.max(height(y.left) , height(y.right));
        return y;
                
    }

    
    
    
}
