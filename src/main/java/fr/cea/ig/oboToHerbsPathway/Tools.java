package fr.cea.ig.oboToHerbsPathway;

import java.util.List;

public final class Tools {
    public static String join(final String r[], final String d) {
        if (r.length == 0)
            return "";
        StringBuilder sb = new StringBuilder( r.length * d.length() );
        sb.append(r[0]);
        for (int i = 1; i < r.length ; i++) {
            sb.append(d);
            sb.append(r[i]);
        }
        return sb.toString();
    }
    
    public static String join(final List<String> r, final String d) {
        String[] result = new String[r.size()];
        r.toArray( result );
        return join( result, d );
    }
    
    public static String[] replace(final String r[], final String oldString, final String newString ) {
        if (r.length == 0)
            return new String[0];
        
        String[] result = new String[ r.length ];
        
        for (int i = 0; i < r.length ; i++) 
            result[i] = r[i].replace(oldString, newString);
        return result;
    }
    
    public static String[] replace(final List<String> r, final String oldString, final String newString ) {
        String[] result = new String[r.size()];
        r.toArray( result );
        return replace( result, oldString, newString );
    }
}
