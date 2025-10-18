package org.library.patronmanagement;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PatronRepository {
    private static final Logger logger  = Logger.getLogger(PatronRepository.class.getName());
private Map<String,Patron> patronMap=new HashMap<>();
public void addPatron(Patron patron) {
    patronMap.put(patron.getPatronId(), patron);
}
public void removePatron(String patronId) {
    patronMap.remove(patronId);
}
public Patron getPatronById(String patronId) {
    return patronMap.get(patronId);
}
public void updatePatronName(String patronId, String newName) {
    Patron patron = patronMap.get(patronId);
    if (patron != null) {
        patron.setPatronName(newName);
    }
    else
    {
        logger.warning("Patron not found: " + patronId);
    }
}
public void updatePatronEmail(String patronId, String newEmail) {
    Patron patron = patronMap.get(patronId);
    if (patron != null) {
        patron.setEmail(newEmail);
    }
    else
    {
        logger.warning("Patron not found: " + patronId);
    }
}

public void displayAllPatrons() {
    logger.info("All Patrons in the Repository:");
    for (Patron patron : patronMap.values()) {
       logger.info("Patron{" +
                "patronName='" + patron.getPatronName() + '\'' +
                ", patronId='" + patron.getPatronId() + '\'' +
                ", email='" + patron.getEmail() + '\'' +
                '}');
    }
}
}
