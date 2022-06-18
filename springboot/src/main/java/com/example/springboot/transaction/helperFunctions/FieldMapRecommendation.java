package com.example.springboot.transaction.helperFunctions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// src: https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance
class LevenshteinDistance {                                               
    private static int minimum(int a, int b, int c) {                            
        return Math.min(Math.min(a, b), c);                                      
    }                                                                            
    public static int computeLevenshteinDistance(CharSequence lhs, CharSequence rhs) {      
        int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];        

        for (int i = 0; i <= lhs.length(); i++)                                 
            distance[i][0] = i;                                                  
        for (int j = 1; j <= rhs.length(); j++)                                 
            distance[0][j] = j;                                                  

        for (int i = 1; i <= lhs.length(); i++)                                 
            for (int j = 1; j <= rhs.length(); j++)                             
                distance[i][j] = minimum(                                        
                        distance[i - 1][j] + 1,                                  
                        distance[i][j - 1] + 1,                                  
                        distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1));

        return distance[lhs.length()][rhs.length()];                           
    } 
}

// Recommendation using Levenshtein Distance between 2 strings
public class FieldMapRecommendation {
    public static HashMap<Long, String> recommend(Map<Long, String> ssots, List<String> headers){
        // HashMap contains recommended mappings with key are SsotIds and String are closest field names to Ssots
        HashMap<Long, String> recommendedMapping = new HashMap<>();
        
        // HashMap contains a score map with key are SsotIds and values are the distance between  
        // the Ssot with that SsotId and the field name that is closest to the Ssot
        HashMap<Long, Integer> tempScore = new HashMap<>();
        
        for (var header: headers){
            String h = header.toLowerCase();
            mostSimilar(h, ssots, tempScore, recommendedMapping);
        }

        return recommendedMapping;
    }

    private static void mostSimilar(String toBeCompared, Map<Long, String> ssots, HashMap<Long, Integer> tempScore, HashMap<Long, String> recommendedMapping) {
        int minDistance = Integer.MAX_VALUE;
        Long mostSimilarSsot = null;

        // Compute Levenshtein Distance of user submitted field name with each SSOT name and 
        // get the SSOT will smallest distance from the field
        for (Long key : ssots.keySet()) {
            String str = ssots.get(key);
            
            int d = LevenshteinDistance.computeLevenshteinDistance(str, toBeCompared);
            if (d < minDistance) {
                minDistance = d;
                mostSimilarSsot = key;
            }
        }

        // if currently, the ssot is not in tempScore, add the ssotId and the minDistance to tempScore 
        // and add the ssotId and current field name to recommended mppings
        // or if the ssot is already inside tempScore, check if the current score is smaller than the score stored in 
        // HashMap. if yes, replace the value in HashMap
        if ((tempScore.get(mostSimilarSsot) != null && minDistance < tempScore.get(mostSimilarSsot)) || tempScore.get(mostSimilarSsot) == null){
            tempScore.put(mostSimilarSsot, minDistance);
            recommendedMapping.put(mostSimilarSsot, toBeCompared);
        }
    }                                                                        
}

    

