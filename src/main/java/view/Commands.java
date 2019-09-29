package view;

import javax.servlet.http.HttpServletRequest;

public class Commands {

    // Type
    public static final String COMMAND                  = "command";

    // User Actions
    public static final String LOGIN_COMMAND                = "login";
    public static final String LOGOUT_COMMAND               = "logout";
    public static final String REGISTER_COMMAND             = "register";

    // Category Actions
    public static final String CATEGORY_INSERT_COMMAND      = "cat_insert";
    public static final String CATEGORY_UPDATE_COMMAND      = "cat_update";
    public static final String CATEGORY_DELETE_COMMAND      = "cat_delete";

    // Error response
    public static final String ERROR_RESPONSE_COMMAND       = "errorResponse";

    // Arguments
    public static final String CATEGORY_VERSION_ARG         = "cat_ver";
    public static final String CATEGORY_TS_ARG              = "cat_ts";
    public static final String CATEGORY_ID_ARG              = "cat_id";
    public static final String CATEGORY_LIST_ARG            = "categories";
    public static final String CATEGORY_NAME_ARG            = "cat_name";
    public static final String CATEGORY_NEW_NAME_ARG        = "cat_new_name";
    public static final String CURR_USER_ARG                = "curr_user";
    public static final String USER_NAME_ARG                = "username";
    public static final String USER_PASS_ARG                = "password";
    public static final String USER_PASS_1_ARG              = "password1";



    public static String translateRequestToCommand(HttpServletRequest request) {
        return request.getParameter(Commands.COMMAND);
    }
}
