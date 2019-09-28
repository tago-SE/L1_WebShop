package view;

import javax.servlet.http.HttpServletRequest;

public class Commands {

    // Type
    public static final String COMMAND              = "command";

    // Actions
    public static final String LOGIN_COMMAND        = "login";
    public static final String LOGOUT_COMMAND       = "logout";
    public static final String REGISTER_COMMAND     = "register";


    public static String translateRequestToCommand(HttpServletRequest request) {
        return request.getParameter(Commands.COMMAND);
    }
}
