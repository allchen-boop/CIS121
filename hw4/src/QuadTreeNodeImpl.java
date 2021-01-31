// CIS 121, QuadTree

public class QuadTreeNodeImpl implements QuadTreeNode {
    
    private QuadTreeNodeImpl topLeft;
    private QuadTreeNodeImpl topRight;
    private QuadTreeNodeImpl bottomLeft;
    private QuadTreeNodeImpl bottomRight;
    
    private int size;
    private int color;
    
    //constructor
    public QuadTreeNodeImpl(int size, int color) { 
        this.size = size;
        this.color = color;
        topLeft = null;
        topRight = null;
        bottomLeft = null;
        bottomRight = null;
    }
    
    //constructor
    public QuadTreeNodeImpl(int size, int color,
                            QuadTreeNodeImpl topLeft, QuadTreeNodeImpl topRight, 
                            QuadTreeNodeImpl bottomLeft, QuadTreeNodeImpl bottomRight) {
        this.size = size;
        this.color = color;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }
    
    
    /**
     * ! Do not delete this method !
     * Please implement your logic inside this method without modifying the signature
     * of this method, or else your code won't compile.
     * <p/>
     * As always, if you want to create another method, make sure it is not public.
     *
     * @param image image to put into the tree
     * @return the newly build QuadTreeNode instance which stores the compressed image
     * @throws IllegalArgumentException if image is null
     * @throws IllegalArgumentException if image is empty
     * @throws IllegalArgumentException if image.length is not a power of 2
     * @throws IllegalArgumentException if image, the 2d-array, is not a perfect square
     */
    public static QuadTreeNodeImpl buildFromIntArray(int[][] image) {
        if (image == null) {
            throw new IllegalArgumentException("null image");
        }
        
        if (image.length == 0 || image[0].length == 0) {
            throw new IllegalArgumentException("empty image");
        }
        
        if (!isPowerOfTwoHelper(image.length)) {
            throw new IllegalArgumentException("image length not power of two");
        }
      
        for (int i = 0; i < image.length; i++) {
            if (image[i].length != image.length) {
                throw new IllegalArgumentException("image not perfect square");
            }
        }
        
        return buildQuadTreeHelper(0, 0, image, image.length);
    }
    
    static QuadTreeNodeImpl buildQuadTreeHelper(int x, int y, int[][] image, int size) {
        
        int halfTree = size / 2;
        
        //base case (leaf node)
        if (size == 1) { 
            return new QuadTreeNodeImpl(size, image[x][y]);
        }
        
        //children
        QuadTreeNodeImpl topLeft = buildQuadTreeHelper(x, y, image, halfTree);
        QuadTreeNodeImpl topRight = buildQuadTreeHelper(x, y + halfTree, image, halfTree);
        QuadTreeNodeImpl bottomLeft = buildQuadTreeHelper(x + halfTree, y, image, halfTree);
        QuadTreeNodeImpl bottomRight = buildQuadTreeHelper(x + halfTree, y + halfTree,
                                                            image, halfTree);
        
        //if all children (leaves) are the same color, we can merge
        if ((topLeft.isLeaf() && topRight.isLeaf() &&
             bottomLeft.isLeaf() && bottomRight.isLeaf()) &&
             topLeft.color == topRight.color &&
             bottomLeft.color == bottomRight.color &&
             topRight.color == bottomLeft.color) {
            return new QuadTreeNodeImpl(size, topLeft.color);
        }
        return new QuadTreeNodeImpl(size, image[x][y], topLeft, topRight, bottomLeft, bottomRight);
    }

   
   //checks if input is a power of two
    private static boolean isPowerOfTwoHelper(int num) {
        if (num == 0) {
            return false;
        }
        
        while (num != 1) {
            if (num % 2 != 0) {
                return false;
            }
            num /= 2;
        }
        return true;
    }
    

    @Override
    public int getColor(int x, int y) {
        if (x < 0 || x > size - 1 || y < 0 || y > size - 1) {
            throw new IllegalArgumentException();
        }
        return getColorHelper(x, y); 
    }
    
    private int getColorHelper(int x, int y) {
        int halfTree = size / 2;
        
        if (isLeaf()) {
            return color;
        }
        
        //recursing through specific quadrant
        if (x > halfTree - 1) { //right
            if (y > halfTree - 1) { //bottom
                return bottomRight.getColorHelper(x - halfTree, y - halfTree);
            }
            return topRight.getColorHelper(x - halfTree, y);
        } else if (y > halfTree - 1) {
            return bottomLeft.getColorHelper(x, y - halfTree);
        }
        return topLeft.getColorHelper(x, y);
        
    }
    
    @Override
    public void setColor(int x, int y, int c) {
        if (x < 0 || x > size - 1 || y < 0 || y > size - 1) {
            throw new IllegalArgumentException();
        }
        setColorHelper(x, y, c);
    }
    
    
    private void setColorHelper(int x, int y, int c) {
        int halfTree = size / 2;
        //case 1
        if (size == 1 && isLeaf()) {
            color = c;
        } else {
            //case 2 (breakdown)
            if (isLeaf()) {
                topLeft = new QuadTreeNodeImpl(halfTree, color);
                topRight = new QuadTreeNodeImpl(halfTree, color);
                bottomLeft = new QuadTreeNodeImpl(halfTree, color);
                bottomRight = new QuadTreeNodeImpl(halfTree, color);
            }
            
            setColorRecurseHelper(x, y, c, halfTree);
          
            //combining leaves of same color
            if ((topLeft.isLeaf() && topRight.isLeaf() &&
                 bottomLeft.isLeaf() && bottomRight.isLeaf()) &&
                 topLeft.color == topRight.color &&
                 bottomLeft.color == bottomRight.color &&
                 topRight.color == bottomLeft.color) {
                
                //reset color
                color = topLeft.color;
                topLeft = null;
                topRight = null;
                bottomLeft = null;
                bottomRight = null;
            }
        }
    }
    
    //recurse through specific quadrant
    private void setColorRecurseHelper(int x, int y, int c, int size) { 
        if (x > size - 1) { //right
            if (y > size - 1) { //bottom
                bottomRight.setColorHelper(x - size, y - size, c);
            } else {
                topRight.setColorHelper(x - size, y, c);
            }
        } else if (y > size - 1) {
            bottomLeft.setColorHelper(x, y - size, c);
        } else {
            topLeft.setColorHelper(x, y, c);
        }
    }

    @Override
    public QuadTreeNode getQuadrant(QuadName quadrant) {
        if (quadrant == QuadTreeNode.QuadName.TOP_LEFT) {
            return topLeft;
        }
        
        if (quadrant == QuadTreeNode.QuadName.TOP_RIGHT) {
            return topRight;
        }
        
        if (quadrant == QuadTreeNode.QuadName.BOTTOM_LEFT) {
            return bottomLeft;
        }
        
        if (quadrant == QuadTreeNode.QuadName.BOTTOM_RIGHT) {
            return bottomRight;
        }
        
        throw new IllegalArgumentException();
    }

    @Override
    public int getDimension() {
        return size;
    }

    @Override
    public int getSize() {
        if (isLeaf()) {
            return 1;
        }
        return topLeft.getSize() + topRight.getSize() +
               bottomLeft.getSize() + bottomRight.getSize() + 1;
    }

    @Override
    public boolean isLeaf() {
        return topLeft == null && topRight == null && bottomLeft == null && bottomRight == null;
    }

    @Override
    public int[][] decompress() {
        int [][] toDecompress = new int [size][size];
        decompressHelper(0, 0, toDecompress);
        return toDecompress;
    }
    
    private void decompressHelper(int x, int y, int[][] decompress) {
        int halfTree = size / 2;
        
        if (size == 1) {
            decompress [y][x] = color;
        }
        
        //recurse through quadrants when not leaf
        if (!isLeaf()) {
            topLeft.decompressHelper(x, y, decompress);
            topRight.decompressHelper(x + halfTree, y, decompress);
            bottomLeft.decompressHelper(x, y + halfTree, decompress);
            bottomRight.decompressHelper(x + halfTree, y + halfTree, decompress);
        } else {
            for (int i = y; i < y + size; i++) {
                for (int j = x; j < x + size; j++) {
                    decompress [i][j] = color;
                }
            }
        } 
        
    }

    @Override
    public double getCompressionRatio() {
        double nodes = (double) getSize();
        double pixels = (double) size * size;
        
        return (double) (nodes / pixels);
    }
}
