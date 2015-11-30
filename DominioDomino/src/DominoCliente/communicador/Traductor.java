/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DominoCliente.communicador;

import DominoCliente.juego.Administrador;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import DominoBase.modelo.Invitacion;
import DominoBase.modelo.Jugador;
import DominoBase.modelo.Sala;

import DominoBase.system.Acciones;
import DominoBase.system.Util;

public class Traductor {

    private ClienteSocket client;
    private LectorListener readerListner;
    private Administrador manager;

    public Traductor(Administrador manager) throws Exception {
        this.manager = manager;
        client = new ClienteSocket();
        readerListner = new LectorListener(this);
        readerListner.start();
    }

    public boolean login(String username) {
        boolean result = false;
        try {
            remoteWrite(Util.prepareMsg(Acciones.LOGON, username));
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void next() {
        try {
            remoteWrite(Util.prepareMsg(Acciones.UPDATE_ROOM, manager.getRoom()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processMensage(Map<String, Object> message) {

        if (message != null) {
            String action = Util.getAction(message);
            Object parameter = Util.getParameter(message);

			// TODO implementar execução para todas as ações possiveis
            // (system.Acciones)
            switch (action) {
                case Acciones.LOGON_FAILURE:
                    if (parameter instanceof String) {
                        manager.addUserMessage((String) parameter);
                    }
                    break;

                case Acciones.LOGON_SUCESSFUL:
                    if (parameter instanceof Jugador) {
                      
                    }
                    break;

                case Acciones.MESSAGE:
                    if (parameter instanceof String) {
                        manager.addUserMessage((String) parameter);
                    }
                    break;

                case Acciones.UPDATE_ROOM:
                    if (parameter instanceof Sala) {
                        manager.setRoom((Sala) parameter);
                    }
                    break;

                case Acciones.ONLINE_USERS:
                    List<String> temp = new ArrayList<String>();
                    List<Map<String, Object>> users = (List<Map<String, Object>>) parameter;
                    for (int i = 0; i < users.size(); i++) {
                        temp.add((String) users.get(i).get(Util.KEY_USERNAME));
                    }
                    manager.setOnlineUsers(temp);
                    break;

                case Acciones.CHAT_MESSAGE:
                    String temp1 = (String) parameter;
                    manager.addChatMessages(temp1);
                    break;

                case Acciones.STATUS_INVITE:

                    if (parameter instanceof List) {
                        List<Invitacion> i = new ArrayList<Invitacion>();
                        List<Invitacion> remote = (List<Invitacion>) parameter;
                        i.addAll(remote);
                        manager.setInvites(i);
                    }
                    break;

                case Acciones.INVITE:
                    Invitacion invite = (Invitacion) parameter;
                    if (invite != null) {
                        Invitacion r = new Invitacion(invite.getIssuing(), invite.getReceptor());
                        r.setStatus(invite.getStatus());
                        manager.addReceivedInvites(r);
                    }
                    break;

                default:
                    System.out.println("Accion no encontrada: " + action);
                    break;
            }
        }
    }

    public void exit() {
        // readerListner.interrupt();
        readerListner.stop();

        try {
            remoteWrite(Util.prepareMsg(Acciones.DISCONNECT, null));
            client.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> remoteRead() throws ClassNotFoundException,
            IOException {

        Object remoteObject = client.getReader().read();
        Map<String, Object> result = null;

        if (remoteObject != null && remoteObject instanceof Map) {
            result = (Map<String, Object>) remoteObject;
        }

        return result;
    }

    private void remoteWrite(Map<String, Object> message) throws IOException {
        client.getWriter().write(message);
    }

    public ClienteSocket getClient() {
        return client;
    }

    public void getOnlineUsers() {
        try {
            remoteWrite(Util.prepareMsg(Acciones.ONLINE_USERS, null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendChatMsg(String msg) {
        try {
            remoteWrite(Util.prepareMsg(Acciones.CHAT_MESSAGE, msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void invite(String userSelected) {
        try {
            remoteWrite(Util.prepareMsg(Acciones.INVITE, userSelected));
            manager.addUserMessage("Invitacion enviada a " + userSelected);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void responseInvite(Invitacion invite) {

        try {
            remoteWrite(Util.prepareMsg(Acciones.RESPONSE_INVITE, invite));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void startGame(List<String> usersPlayers) {

        try {
            remoteWrite(Util.prepareMsg(Acciones.START_GAME, usersPlayers));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
