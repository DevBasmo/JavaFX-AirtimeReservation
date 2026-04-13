import java.util.*;


public class AirtimeTravelDataBase {
    
    private final Map < String, AirtimeTravelStore > timeTravel = new HashMap();
    
    
    public void registerTicket ( AirtimeTravelStore a)
    {
        timeTravel.put(a.referenceDB.trim().toLowerCase(), a);
    }
    
    
    public AirtimeTravelStore findTicket ( String referenceDB )
    {
        
        return timeTravel.get(referenceDB.trim().toLowerCase());
    }
    
    public boolean exists ( String referenceDB )
    {
        return timeTravel.containsKey(referenceDB.trim().toLowerCase());
    }
    
}
