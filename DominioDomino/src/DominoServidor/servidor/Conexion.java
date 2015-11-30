/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DominoServidor.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import DominoBase.modelo.Invitacion;
import DominoBase.modelo.Sala;

import DominoBase.system.Acciones;
import DominoBase.system.Util;

public class Conexion extends Thread {

    private Socket connection;

    private String username;

    private boolean acceptedInvite;

    private ObjectInputStream in;

    private ObjectOutputStream out;

    private Servidor server;

    public Conexion(Socket socket, Servidor server) throws IOException {
        super();

        connection = socket;
        this.server = server;
        username = "";
        acceptedInvite = false;

        out = new ObjectOutputStream(connection.getOutputStream());
        out.flush();

        in = new ObjectInputStream(connection.getInputStream());
        out.flush();

        start();
    }

    @Override
    public void run() {

        try {

            while (true) {

                try {
                    Object msg = in.readObject();
                    System.out.println(">> Leer " + this.getUsername() + ": "
                            + msg);
                    processMessage(msg);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

   
    private void processMessage(Object message) {

        if (message instanceof Map) {

            @SuppressWarnings("unchecked")
            Map<String, Object> msg = (Map<String, Object>) message;

            String action = Util.getAction(msg);
            Object parameter = Util.getParameter(msg);

            switch (action) {
                case Acciones.LOGON:

                    String login = (String) parameter;

                    if (server.existsLogin(login)) {
                        msg = Util.prepareMsg(Acciones.LOGON_FAILURE, "login "
                                + login + " en uso.");
                        sendMsg(msg);
                    } else {
                        this.username = login;
                        msg = Util.prepareMsg(Acciones.LOGON_SUCESSFUL, null);
                        sendMsg(msg);

                        msg = Util.prepareMsg(Acciones.MESSAGE, login
                                + " conectado.");
                        server.sendMsgToAll(msg);

                    }
                    break;

                case Acciones.GIVEME_ROOM:


                    if (server.getRoomPlayer(username).isGameStarted()
                            && server.getRoomPlayer(username)
                            .getPlayer(this.username).isToken()) {
                        msg = Util.prepareMsg(Acciones.UPDATE_ROOM,
                                server.getRoomPlayer(username));
                        sendMsg(msg);
                    }

                    break;

                case Acciones.UPDATE_ROOM:


                    if (parameter instanceof Sala) {
                        Sala room = server.getRoomPlayer(username);
                        if (room.isGameStarted()
                                && room.getPlayer(this.username).isToken()) {

                            room = (Sala) parameter;
                            room.next();
                            server.setRoom(room);
                            msg = Util.prepareMsg(Acciones.UPDATE_ROOM, room);
                            server.sendMsgToPlayers(msg, room);
                        }

                    }

                    break;

                case Acciones.DISCONNECT:
                    try {
                        msg = Util.prepareMsg(Acciones.MESSAGE, this.username
                                + " salir.");
                        disconnect();
                        server.sendMsgToAll(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case Acciones.ONLINE_USERS:
                    msg = Util.prepareMsg(Acciones.ONLINE_USERS,
                            server.getAllUsersOnline());
                    sendMsg(msg);
                    break;

                case Acciones.INVITE:

                    String user = (String) parameter;

                    if (server.getUsersOnline().contains(user)
                            && !server.getUsersInRoom().contains(user)) {

                        Conexion recptorConnection = server.getConnection(user);
                        if (recptorConnection != null) {

                            if (!recptorConnection.isAcceptedInvite()) {
                                Invitacion invite = server.getInvite(username, user);

                                if (invite == null) {

                                    invite = new Invitacion(username, user);

                                    server.addInvite(invite);
                                    recptorConnection.sendMsg(Util.prepareMsg(Acciones.INVITE, invite));

                                    sendMsg(Util.prepareMsg(Acciones.STATUS_INVITE,
                                            server.getInvitesIssuing(username)));
                                } else {
                                    sendMsg(Util.prepareMsg(Acciones.MESSAGE,
                                            "Hay una invitacion para el usuario "
                                            + user));
                                }
                            } else {
                                sendMsg(Util.prepareMsg(Acciones.MESSAGE,
                                        "El usuario ha sido invitado"));

                            }
                        } else {
                            sendMsg(Util.prepareMsg(Acciones.MESSAGE,
                                    "Receptor no encontrado"));
                        }

                    } else {
                        msg = Util.prepareMsg(Acciones.MESSAGE, user
                                + " ya esta jugando.");
                        sendMsg(msg);
                    }
                    break;

                case Acciones.RESPONSE_INVITE:

                    Invitacion responseInvite = (Invitacion) parameter;
                    Invitacion serverInvite = server.getInvite(
                            responseInvite.getIssuing(),
                            responseInvite.getReceptor());
                    if (serverInvite != null) {
                        serverInvite.setStatus(responseInvite.getStatus());
                        if (serverInvite.getStatus().equals(Invitacion.ACEPTADO)
                                && !isAcceptedInvite()) {
                            setAcceptedInvite(true);
                        }
                    }

                    Conexion issuingConnection = server
                            .getConnection(responseInvite.getIssuing());
                    List<Invitacion> invites = server.getInvitesIssuing(responseInvite
                            .getIssuing());
                    issuingConnection.sendMsg(Util.prepareMsg(Acciones.STATUS_INVITE, invites));

                    break;

                case Acciones.CHAT_MESSAGE:
                    String chatMsg = (String) parameter;
                    server.sendMsgToAll(Util.prepareMsg(Acciones.CHAT_MESSAGE,
                            username + "> " + chatMsg));
                    break;

                case Acciones.START_GAME:
                    List<String> usernamePlayers = (List<String>) parameter;

                    if (server.startGame(usernamePlayers)) {
                        msg = Util.prepareMsg(Acciones.MESSAGE,
                                "El juego inicio");
                        server.sendMsgToPlayers(msg, server.getRoomPlayer(username));
                    }
                  

                    break;

                default:
                    break;
            }
        }
    }

    /**
     * Send a message to client.
     *
     * @param msg
     */
    public void sendMsg(Object msg) {
        try {
            System.out.println(">> Escribir " + this.getUsername() + ": " + msg);
            out.reset();
            out.writeObject(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getConnection() {
        return connection;
    }

    public void disconnect() throws IOException {
        in.close();
        out.close();
        connection.close();
        interrupt();
    }

    public String getUsername() {
        return username;
    }

    public boolean isLogged() {
        boolean result = false;

        if (this.username != null && !this.username.trim().equals("")) {
            result = true;
        }

        return result;
    }

    private boolean isAcceptedInvite() {
        return acceptedInvite;
    }

    private void setAcceptedInvite(boolean acceptedInvite) {
        this.acceptedInvite = acceptedInvite;
    }

}
