public class Woodchuck {

    public static long canCompleteFloor(long[] lengths) {
        long gcd = 0;
        
        for (long element : lengths) {
            gcd = gcdHelper(gcd, element); 
        }
         
        long [] arr = new long [lengths.length];
        
        //dividing by the gcd should leave array with prime factors of only 3, 5 (if possible)
        for (int i = 0; i < lengths.length; i++) {
            arr [i] = (lengths[i] / gcd);
        }
        
        if (cutArrHelper(arr)) {
            return gcd; 
        } else {
            return -1;
        }
    }
    
    private static long gcdHelper(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcdHelper(b, a % b);
    }
    
    //if the the elements can be cut into only 3 or 5 pieces
    private static boolean cutArrHelper(long[] arr) {
        for (int i = 0; i < arr.length; i ++) {
            while (arr[i] % 3 == 0) {
                arr[i] /= 3;
            }
            
            while (arr[i] % 5 == 0) {
                arr[i] /= 5;
            }
        }
        
        //after dividing by 3 and/or 5 are they all equal length
        long first = arr[0]; 
        for (long element : arr) {
            if (element != first) {
                return false;
            }
        }
        return true;
    }

}
