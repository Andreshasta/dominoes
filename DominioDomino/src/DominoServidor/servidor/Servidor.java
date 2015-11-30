/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DominoServidor.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import DominoBase.system.Acciones;
import DominoBase.system.Parametros;
import DominoBase.system.Util;

import DominoBase.modelo.Invitacion;
import DominoBase.modelo.Jugador;
import DominoBase.modelo.Sala;

public class Servidor {

    private List<Conexion> clients;
    private List<Sala> rooms;
    private ServerSocket server;
    private List<Invitacion> invites;

    /**
     * Default constructor
     */
    public Servidor() {
        clients = new ArrayList<Conexion>();
        // room = new Sala();
        rooms = new ArrayList<Sala>();
        invites = new ArrayList<Invitacion>();
    }

    /**
     * Run listener server socket.
     */
    public void execute() {
        try {

            //server = new ServerSocket(7000);
            server = new ServerSocket(Parametros.PORT_SERVER);
            //System.out.println("Servidor ejecutando en el puerto 7000...");
            System.out.println("Servidor ejecutando en el puerto " + Parametros.PORT_SERVER + "...");

            while (true) {
                Socket socket = server.accept();
                Conexion connection = new Conexion(socket, this);
                clients.add(connection);
                validClients();
            }

        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }

    /**
     * Verify if the socket connection clients is active. If not, the
     * connections is removed.
     */
    private void validClients() {

        Iterator<Conexion> i = clients.iterator();
        while (i.hasNext()) {
            Conexion connection = i.next();
            if (!connection.isAlive()) {
                clients.remove(connection);
                sendMsgToAll(Util.prepareMsg(Acciones.MESSAGE,
                        connection.getUsername() + " salir."));
            }
        }

    }

    /**
     * Verify in all connection, if the login is used in server.
     *
     * @param login
     * @return
     */
    public boolean existsLogin(String login) {

        boolean result = false;

        for (Conexion connection : clients) {
            if (connection.isLogged()
                    && connection.getUsername().equalsIgnoreCase(login)) {
                result = true;
            }
        }

        return result;
    }

    /**
     * send a menssage broadcast. To all clients.
     *
     * @param msg
     */
    public void sendMsgToAll(Object msg) {
        for (Conexion connection : clients) {
            connection.sendMsg(msg);
        }
    }

    /**
     * Get all clients connections.
     *
     * @return List of connections
     */
    public List<Conexion> getClients() {
        return clients;
    }

    /**
     * Start game...
     */
    /*
     * public void startGame() { // The game can only be started once. if
     * (!room.isGameStarted()) {
     * 
     * validClients();
     * 
     * int id = 0;
     * 
     * for (Conexion connection : clients) { Jugador player = new Jugador();
     * player.setId(++id); player.setUsername(connection.getUsername());
     * room.addPlayers(player); }
     * 
     * room.start();
     * 
     * // Send a message all clients to update the room status.
     * sendMsgToAll(Util.prepareMsg(Acciones.UPDATE_ROOM, room));
     * 
     * }
     * 
     * }
     */
    /**
     * Start game...
     */
    public boolean startGame(List<String> usernamePlayers) {

        boolean canStart = true;
        boolean result = false;

        validClients();

        for (String username : usernamePlayers) {
            if (isUserInRoom(username)) {
                canStart = false;
                break;
            }
        }

        if (canStart) {

            Sala room = new Sala(getNextIdRoom());

            int id = 0;

            for (String s : usernamePlayers) {
                Conexion connection = getConnection(s);
                if (connection != null) {
                    Jugador player = new Jugador();
                    player.setId(++id);
                    player.setUsername(connection.getUsername());
                    room.addPlayers(player);
                }
            }

            room.start();
            rooms.add(room);
            result = true;

            // Send a message all players from room.
            sendMsgToPlayers(Util.prepareMsg(Acciones.UPDATE_ROOM, room), room);

        }

        return result;

    }

    private int getNextIdRoom() {

        int lastIdRoom = -1;

        for (Sala r : rooms) {
            if (r.getId() > lastIdRoom) {
                lastIdRoom = r.getId();
            }
        }

        return ++lastIdRoom;
    }

    public void sendMsgToPlayers(Map<String, Object> msg, Sala room) {

        for (Jugador p : room.getPlayers()) {
            Conexion connection = getConnection(p.getUsername());
            connection.sendMsg(msg);
        }

    }

    private boolean isUserInRoom(String username) {
        boolean result = false;

        for (Sala r : rooms) {
            for (Jugador p : r.getPlayers()) {
                if (p.getUsername().equals(username)) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    public List<Sala> getRooms() {
        return rooms;
    }

    public Sala getRoomPlayer(String username) {
        Sala result = null;

        for (Sala r : rooms) {
            for (Jugador p : r.getPlayers()) {
                if (p.getUsername().equals(username)) {
                    result = r;
                    break;
                }
            }
        }

        return result;
    }

    public List<Map<String, Object>> getAllUsersOnline() {
        List<Map<String, Object>> users = new ArrayList<Map<String, Object>>();
        List<String> usersInRoom = getUsersInRoom();
        List<String> usersOnline = getUsersOnline();

        for (String user : usersOnline) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(Util.KEY_USERNAME, user);
            map.put(Util.KEY_INROOM, usersInRoom.contains(user));
            users.add(map);
        }

        return users;
    }

    public List<String> getUsersInRoom() {
        List<String> users = new ArrayList<String>();

        for (Sala r : rooms) {
            for (Jugador p : r.getPlayers()) {
                users.add(p.getUsername());
            }
        }

        return users;
    }

    public List<String> getUsersOnline() {
        List<String> users = new ArrayList<String>();

        for (Conexion c : clients) {
            users.add(c.getUsername());
        }

        return users;
    }

    public void addInvite(Invitacion invite) {
        invites.add(invite);
    }

    public Invitacion getInvite(String issuing, String receptor) {
        Invitacion result = null;

        for (Invitacion invite : invites) {
            if (invite.getIssuing().equals(issuing)
                    && invite.getReceptor().equals(receptor)) {
                result = invite;
                break;
            }
        }

        return result;

    }

    public Conexion getConnection(String username) {

        Conexion result = null;

        for (Conexion connection : clients) {
            if (connection.getUsername() != null
                    && connection.getUsername().equals(username)) {
                result = connection;
            }
        }

        return result;
    }

    public List<Invitacion> getInvitesIssuing(String username) {
        List<Invitacion> result = new ArrayList<Invitacion>();

        for (Invitacion invite : invites) {
            if (invite.getIssuing().equals(username)) {
                result.add(invite);
            }
        }

        return result;
    }

    public void setRoom(Sala room) {

        for (int i = 0; i < rooms.size(); i++) {
            if (room.getId() == rooms.get(i).getId()) {
                rooms.set(i, room);
                break;
            }
        }

    }
}
