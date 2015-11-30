package DominoBase.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Jugador implements Serializable {

    private static final long serialVersionUID = -7414266462219657287L;

    private int id;

    private List<Domino> dominos;

    private String username;

    private boolean token;

    private int puntaje;

    public Jugador() {
        dominos = new ArrayList<Domino>();
        token = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Domino> getDominos() {
        return dominos;
    }

    public void setDominos(List<Domino> dominos) {
        this.dominos = dominos;
    }

    public boolean isToken() {
        return token;
    }

    public void setToken(boolean token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addDomino(Domino domino) {
        dominos.add(domino);
    }

    public void removeDomino(Domino domino) {
        dominos.remove(domino);
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

}
