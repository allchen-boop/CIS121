import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class QuadTreeNodeImplTest {
    
    @Test (expected = IllegalArgumentException.class)
    public void buildIntFromNullImage() {
        int[][] image = null;
        QuadTreeNodeImpl.buildFromIntArray(image);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void buildIntFromEmptyImage() {
        int[][] image = {{}, {}};
        QuadTreeNodeImpl.buildFromIntArray(image);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void buildIntFromImageLengthNotPowOfTwo() {
        int[][] image = {{0},
                         {0}};
        QuadTreeNodeImpl.buildFromIntArray(image);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void buildIntFromImageLengthNotSquare() {
        int[][] image = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 0, 1}
            };
        QuadTreeNodeImpl.buildFromIntArray(image);
    }
    
    
    @Test (expected = IllegalArgumentException.class)
    public void buildIntFromImageLengthSquareNotPowTwo() {
        int[][] image = {
                {0, 1, 2},
                {4, 5, 6},
                {8, 9, 0}
            };
        QuadTreeNodeImpl.buildFromIntArray(image);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void buildIntFromRaggedImage() {
        int[][] image = {
                {0, 1, 2, 3},
                {4, 5, 6},
                {8, 9, 0, 1}
            };
        QuadTreeNodeImpl.buildFromIntArray(image);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void buildIntFromRaggedImagePowTwo() {
        int[][] image = {
                {0, 1, 2, 3},
                {4, 5},
                {8, 9, 0, 1},
                {2, 3, 4, 5}
            };
        QuadTreeNodeImpl.buildFromIntArray(image);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void getColorOutOfBoundsTest() {
        int[][] image = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 0, 1},
                {2, 3, 4, 5}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        node.getColor(5, 5);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void getColorOutOfBoundsNegTest() {
        int[][] image = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 0, 1},
                {2, 3, 4, 5}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        node.getColor(-1, -1);
    }
    
    @Test
    public void getColorTest() {
        int[][] image = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 0, 1},
                {2, 3, 4, 5}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        assertEquals(7, node.getColor(3, 1));
    }
    
    @Test  (expected = IllegalArgumentException.class)
    public void setColorOutOfBoundsTest() {
        int[][] image = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 0, 1},
                {2, 3, 4, 5}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        assertEquals(3, node.getColor(3, 0));
        assertEquals(21, node.getSize());
        
        node.setColor(5, 5, 2);
        assertEquals(2, node.getColor(3, 0));
        assertEquals(21, node.getSize());
    }
    
    @Test  (expected = IllegalArgumentException.class)
    public void setColorOutOfBoundsNegTest() {
        int[][] image = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 0, 1},
                {2, 3, 4, 5}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        assertEquals(3, node.getColor(3, 0));
        assertEquals(21, node.getSize());
        
        node.setColor(-1, -1, 2);
        assertEquals(2, node.getColor(3, 0));
        assertEquals(21, node.getSize());
    }
    
    @Test
    public void setColorNoStructureChangeTest() {
        int[][] image = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 0, 1},
                {2, 3, 4, 5}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        assertEquals(3, node.getColor(3, 0));
        assertEquals(21, node.getSize());
        
        node.setColor(3, 0, 2);
        assertEquals(2, node.getColor(3, 0));
        assertEquals(21, node.getSize());
    }
    
    @Test
    public void setColorChildNeedsBreakdownTest() {
        int[][] image = {
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {2, 1, 1, 1},
                {1, 1, 1, 1}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        assertEquals(9, node.getSize());
        
        node.getQuadrant(QuadTreeNode.QuadName.BOTTOM_LEFT).setColor(0, 0, 1);
        assertEquals(1, node.getQuadrant(QuadTreeNode.QuadName.BOTTOM_LEFT).getSize()); 
    }

    @Test
    public void setColorParentNeedBreakdownTest() {
        int[][] image = {
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {2, 1, 1, 1},
                {1, 1, 1, 1}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        assertEquals(9, node.getSize());
        assertEquals(2, node.getColor(0, 2));
       
        node.setColor(0, 2, 1);
        assertEquals(1, node.getColor(0, 2));
        assertEquals(1, node.getSize());
    }
    
    @Test
    public void setColorCombineTest() {
        int[][] image = {
                {0, 0, 1, 0},
                {0, 0, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        assertEquals(9, node.getSize());
        
        node.setColor(3, 0, 1);
        assertEquals(1, node.getQuadrant(QuadTreeNode.QuadName.TOP_RIGHT).getColor(1, 0));
        assertEquals(1, node.getQuadrant(QuadTreeNode.QuadName.TOP_RIGHT).getSize());
    }
    
    @Test
    public void setColorSetColorTwiceTest() {
        int[][] image = {
                {1, 0, 1, 0},
                {0, 1, 0, 1},
                {1, 0, 1, 0},
                {0, 1, 0, 1}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        assertEquals(21, node.getSize());
        
        node.setColor(3, 0, 1);
        node.setColor(1, 1, 1);
        assertEquals(1, node.getQuadrant(QuadTreeNode.QuadName.TOP_RIGHT).getColor(1, 0));
        assertEquals(5, node.getQuadrant(QuadTreeNode.QuadName.TOP_RIGHT).getSize());
    }
     
    @Test
    public void getDimensionTest() {
        int[][] image = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 0, 1},
                {2, 3, 4, 5}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        assertEquals(4, node.getDimension());
    }
    
    @Test
    public void getSizeTest() {
        int[][] image = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 0, 1},
                {2, 3, 4, 5}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        assertEquals(21, node.getSize());
    }
    
    @Test
    public void getSizeLeafTest() {
        int[][] image = {
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        assertEquals(1, node.getSize());
    }
    
    @Test
    public void getSizeHalfColorsTest() {
        int[][] image = {
                {1, 1, 0, 0},
                {1, 1, 0, 0},
                {1, 1, 0, 0},
                {1, 1, 0, 0}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        assertEquals(5, node.getSize());
    }
    
    @Test
    public void getSizeChildrenNoGrandchildrenTest() {
        int[][] image = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 0, 1},
                {2, 3, 4, 5}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        assertEquals(5, node.getQuadrant(QuadTreeNode.QuadName.TOP_RIGHT).getSize());
    }
    
    @Test
    public void isLeafFalseTest() {
        int[][] image = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 0, 1},
                {2, 3, 4, 5}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        assertFalse(node.getQuadrant(QuadTreeNode.QuadName.TOP_RIGHT).isLeaf());
    }
    
    @Test
    public void isLeafTrueTest() {
        int[][] image = {
                {0, 0, 2, 3},
                {0, 0, 6, 7},
                {8, 9, 0, 1},
                {2, 3, 4, 5}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        assertTrue(node.getQuadrant(QuadTreeNode.QuadName.TOP_LEFT).isLeaf());
    }
    
    @Test
    public void decompressDiffColorsTest() {
        int[][] image = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 0, 1},
                {2, 3, 4, 5}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        int [][] decompress = node.decompress();
        assertArrayEquals(image, decompress);
    }
    
    @Test
    public void decompressSameColors() {
        int[][] image = {
                {0, 0, 2, 3},
                {0, 0, 6, 7},
                {8, 9, 0, 1},
                {2, 3, 4, 5}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        int[][] decompress = node.decompress();
        assertArrayEquals(image, decompress);
    }
    
    @Test
    public void decompressSingleColor() {
        int[][] image = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        int[][] decompress = node.decompress();
        assertArrayEquals(image, decompress);
    }
    
    @Test
    public void decompressAfterSetColorTest() {
        int[][] image = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 0, 1},
                {2, 3, 4, 5}
            };
        
        int[][] decompressed = {
                {0, 1, 2, 1},
                {4, 5, 6, 7},
                {8, 9, 0, 1},
                {2, 3, 4, 5}
            };
        
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        
        node.setColor(3, 0, 1);
        
        int [][] decompress = node.decompress();
        assertArrayEquals(decompressed, decompress);
    }
    
    @Test
    public void getCompressionRatioTest() {
        int[][] image = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 0, 1},
                {2, 3, 4, 5}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        double ratio = 21.0 / 16.0;
        assertEquals(ratio, node.getCompressionRatio(), 0.0001);
    }
    
    @Test
    public void getCompressionRatioSingleColorTest() {
        int[][] image = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        double ratio = 1.0 / 16.0;
        assertEquals(ratio, node.getCompressionRatio(), 0.0001);
    }
    
    @Test
    public void getCompressionRatioCompressedColorTest() {
        int[][] image = {
                {1, 1, 0, 0},
                {1, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        double ratio = 5.0 / 16.0;
        assertEquals(ratio, node.getCompressionRatio(), 0.0001);
    }
    
    @Test
    public void getCompressionRatioSetColorDecompress() {
        int[][] image = { 
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            };
        int[][] decompressed = { 
                {1, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0} 
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        double before = 1.0 / 16.0;
        assertEquals(before, node.getCompressionRatio(), 0.0001);
        
        node.setColor(0, 0, 1);
        double after = 9.0 / 16.0;
        assertEquals(after, node.getCompressionRatio(), 0.0001);
        
        int[][] decompress = node.decompress();
        assertArrayEquals(decompressed, decompress);
    }
    
    @Test
    public void getCompressionRatioSetColorCompress() {
        int[][] image = { 
                {1, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            };
        int[][] decompressed = { 
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            };
        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        double before = 9.0 / 16.0;
        assertEquals(before, node.getCompressionRatio(), 0.0001);
        
        node.setColor(0, 0, 0);
        double after = 1.0 / 16.0;
        assertEquals(after, node.getCompressionRatio(), 0.0001);
        
        int[][] decompress = node.decompress();
        assertArrayEquals(decompressed, decompress);
    }

    @Test
    public void bigTest() {
        int[][] image = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 0, 1},
                {2, 3, 4, 5}
            };
        
        int[][] one = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 0, 0},
                {2, 3, 0, 0}
            };
        
        int[][] two = {
                {0, 0, 2, 3},
                {0, 0, 6, 7},
                {8, 9, 0, 0},
                {2, 3, 0, 0}
            };
        
        int[][] three = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {8, 9, 0, 0},
                {2, 3, 0, 0}
            };

        QuadTreeNode node = QuadTreeNodeImpl.buildFromIntArray(image);
        assertEquals(21, node.getSize());
        
        node.setColor(3, 2, 0);
        node.setColor(2, 3, 0);
        node.setColor(3, 3, 0);
        assertEquals(0, node.getQuadrant(QuadTreeNode.QuadName.BOTTOM_RIGHT).getColor(0, 0));
        assertEquals(0, node.getQuadrant(QuadTreeNode.QuadName.BOTTOM_RIGHT).getColor(1, 1));
        assertEquals(1, node.getQuadrant(QuadTreeNode.QuadName.BOTTOM_RIGHT).getSize());
        assertEquals(17, node.getSize());
        int[][] firstDecompress = node.decompress();
        assertArrayEquals(one, firstDecompress);

        
        node.setColor(1, 0, 0);
        node.setColor(1, 1, 0);
        node.setColor(0, 1, 0);
        assertEquals(0, node.getColor(0, 0));
        assertEquals(0, node.getColor(0, 1));
        assertEquals(0, node.getColor(1, 1));
        assertEquals(0, node.getColor(1, 0));
        assertEquals(1, node.getQuadrant(QuadTreeNode.QuadName.TOP_LEFT).getSize());
        assertEquals(13, node.getSize());
        int[][] secondDecompressLast = node.decompress();
        assertArrayEquals(two, secondDecompressLast);
        
        node.setColor(3, 0, 0);
        node.setColor(2, 0, 0);
        node.setColor(2, 1, 0);
        node.setColor(3, 1, 0);
        assertEquals(0, node.getQuadrant(QuadTreeNode.QuadName.TOP_RIGHT).getColor(0, 0));
        assertEquals(0, node.getQuadrant(QuadTreeNode.QuadName.TOP_RIGHT).getColor(0, 1));
        assertEquals(0, node.getQuadrant(QuadTreeNode.QuadName.TOP_RIGHT).getColor(1, 1));
        assertEquals(0, node.getQuadrant(QuadTreeNode.QuadName.TOP_RIGHT).getColor(1, 0));
        assertEquals(1, node.getQuadrant(QuadTreeNode.QuadName.TOP_RIGHT).getSize());
        assertEquals(9, node.getSize());
        int[][] thirdDecompress = node.decompress();
        assertArrayEquals(three, thirdDecompress);
    }

}
