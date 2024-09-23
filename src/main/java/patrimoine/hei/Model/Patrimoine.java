package patrimoine.hei.Model;

import java.time.LocalDateTime;

public class Patrimoine {
    private String possesseur;
    private LocalDateTime derniereModification;

    public Patrimoine(String possesseur, LocalDateTime derniereModification) {
        this.possesseur = possesseur;
        this.derniereModification = derniereModification;
    }

    public String getPossesseur() {
        return possesseur;
    }

    public void setPossesseur(String possesseur) {
        this.possesseur = possesseur;
    }

    public LocalDateTime getDerniereModification() {
        return derniereModification;
    }

    public void setDerniereModification(LocalDateTime derniereModification) {
        this.derniereModification = derniereModification;
    }
}
