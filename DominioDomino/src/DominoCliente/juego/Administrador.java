/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DominoCliente.juego;

import DominoBase.modelo.AdministrarArchivo;
import java.util.ArrayList;
import java.util.List;

import DominoBase.system.Parametros;

import DominoBase.modelo.Domino;
import DominoBase.modelo.Invitacion;
import DominoBase.modelo.Jugador;
import DominoBase.modelo.Sala;
import DominoCliente.communicador.Traductor;

public class Administrador {

    private Traductor translator;

    private Sala room;

    private List<String> userMessages;

    private List<String> onlineUsers;

    private String myUsername;

    private List<String> chatMessages;

    private List<Invitacion> invites;

    private List<Invitacion> receivedInvites;

    private boolean acceptedInvite;

    public Administrador() throws Exception, Exception {
        translator = new Traductor(this);
        room = new Sala(-1);
        userMessages = new ArrayList<String>();
        myUsername = "";
        onlineUsers = new ArrayList<String>();
        chatMessages = new ArrayList<String>();
        invites = new ArrayList<Invitacion>();
        receivedInvites = new ArrayList<Invitacion>();
        acceptedInvite = false;
    }

    public void addUserMessage(String message) {
        userMessages.add(message);
    }

    public List<String> getMessages() {
        List<String> result = new ArrayList<String>();

        result.addAll(userMessages);
        userMessages.clear();

        return result;
    }

    public boolean login(String username) {
        boolean result = false;

        if (username == null || username.isEmpty()) {
            userMessages.add("Ingreso invalido");
        } else {
            myUsername = username;
            result = translator.login(username);
        }

        return result;
    }

    public Sala getRoom() {
        return room;
    }

    public void setRoom(Sala room) {
        this.room = room;
    }

    public void exit() {
        translator.exit();
    }

    public boolean isMyTurn() {
        return getMyPlayer().isToken();
    }

    public Jugador getMyPlayer() {
        return room.getPlayer(this.myUsername);
    }

    public void next() {
        translator.next();
    }

    public void ponerEnTablero() {
        if (!getRoom().pushAvaliableDominos(getMyPlayer())) {
            addUserMessage("No hay dominios disponibles");
        }

    }

    public void putOnBoard(Domino x, String side, String me) {
        if (getRoom().putOnBoard(x, side, me)) {
            next();
        } else {
            addUserMessage("Movimiento no permitido");
        }
    }

    public boolean finishedGame() {
        boolean result = false;

        if (getRoom().isFinishedGame()) {
            Jugador won = getRoom().whoWon();
            if (won == null) {
                addUserMessage("Fin del juego. No hay ganador.");
            } else {
                addUserMessage(won.getUsername() + " gano el juego!");
            }
            AdministrarArchivo aa = new AdministrarArchivo();
            aa.cargarArchivo();
            aa.adicionarJugador(won);
            aa.guardarDatos();
        }
        return result;
    }

    public List<String> getOnlineUsers() {
        translator.getOnlineUsers();
        return this.onlineUsers;
    }

    public void setOnlineUsers(List<String> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    public String getMyUsername() {
        return myUsername;
    }

    public List<String> getChatMsg() {
        List<String> result = new ArrayList<String>();
        result.addAll(chatMessages);
        chatMessages.clear();
        return result;
    }

    public void addChatMessages(String message) {
        chatMessages.add(message);
    }

    public void sendChatMsg(String msg) {
        translator.sendChatMsg(msg);

    }

    public void invite(String username) {
        int amountInvite = 0;
        for (Invitacion invite : getInvites()) {
            if (invite.getStatus().equals(Invitacion.ACEPTADO)
                    || invite.getStatus().equals(Invitacion.PENDIENTE)) {
                amountInvite++;
            }
        }
        if (amountInvite < Parametros.MAX_NUMBER_OF_PLAYERS) {

            if (getInvite(username) == null) {
                translator.invite(username);
            } else {
                addUserMessage("El usuario ha sido invitado");
            }
        } else {
            addUserMessage("Invitaciones limitadas");
        }

    }

    public List<Invitacion> getInvites() {
        return invites;
    }

    public void setInvites(List<Invitacion> invites) {
        this.invites = invites;
    }

    private Invitacion getInvite(String receptor) {
        Invitacion result = null;

        for (Invitacion invite : invites) {
            if (invite.getReceptor().equals(receptor)) {
                result = invite;
            }
        }

        return result;
    }

    private Invitacion getReceivedInvites(String issuing) {
        Invitacion result = null;

        for (Invitacion invite : receivedInvites) {
            if (invite.getIssuing().equals(issuing)) {
                result = invite;
            }
        }

        return result;
    }

    public List<Invitacion> getReceivedInvites() {
        return receivedInvites;
    }

    public void setReceivedInvites(List<Invitacion> receivedInvites) {
        this.receivedInvites = receivedInvites;
    }

    public void addReceivedInvites(Invitacion invite) {
        receivedInvites.add(invite);
    }

    public void refuseInvite(String user) {
        Invitacion invite = getReceivedInvites(user);
        invite.refuse();
        translator.responseInvite(invite);
    }

    public void acceptInvite(String user) {
        Invitacion invite = getReceivedInvites(user);
        invite.accept();
        this.acceptedInvite = true;
        translator.responseInvite(invite);
        addUserMessage("Espere por el jugador principal");
    }

    public boolean isAcceptedInvite() {
        return acceptedInvite;
    }

    public void startGame() {

        List<String> usersPlayers = new ArrayList<String>();
        usersPlayers.add(myUsername);
        for (Invitacion invite : invites) {
            if (invite.getStatus().equals(Invitacion.ACEPTADO)) {
                usersPlayers.add(invite.getReceptor());
            }
        }

        if (usersPlayers.size() > 1) {
            translator.startGame(usersPlayers);
        }

    }

}
