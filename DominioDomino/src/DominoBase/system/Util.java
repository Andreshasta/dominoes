package DominoBase.system;

import java.util.HashMap;
import java.util.Map;

/**
 * Generic Functions
 *
 */
public final class Util {

    public final static String ACTION = "action";

    public final static String PARAMETER = "parameter";

    public static final String KEY_USERNAME = "username";

    public static final String KEY_INROOM = "inroom";

    public static final Map<String, Object> prepareMsg(String action, Object parameter) {

        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put(ACTION, action);
        msg.put(PARAMETER, parameter);

        return msg;
    }

    /**
     * Get action value of a message.
     *
     * @param message
     * @return The action
     */
    public static String getAction(Map<String, Object> message) {
        String result = "";

        if (message != null) {
            Object action = message.get(ACTION);
            if (action instanceof String) {
                result = (String) action;
            }
        }
        return result;
    }

    /**
     * Get parameter value of a message.
     *
     * @param message
     * @return The parameter
     */
    public static Object getParameter(Map<String, Object> message) {
        Object result = null;

        if (message != null) {
            result = message.get(PARAMETER);
        }
        return result;
    }

}
