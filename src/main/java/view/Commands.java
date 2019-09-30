package view;

import javax.servlet.http.HttpServletRequest;

public class Commands {

    // Type
    public static final String COMMAND                  = "command";

    // User Actions
    public static final String LOGIN_COMMAND                = "login";
    public static final String LOGOUT_COMMAND               = "logout";
    public static final String REGISTER_COMMAND             = "register";

    // Category
    public static final String CMD_INSERT_CATEGORY          = "cat_insert";
    public static final String CMD_UPDATE_CATEGORY          = "cat_update";
    public static final String CMD_DELETE_CATEGORY          = "cat_delete";
    public static final String CMD_CATEGORY_GET_ALL         = "cmd_cat_all";

    // Users
    public static final String ARG_ALL_USERS                = "users_all";
    public static final String ARG_ACCESS_ROLES             = "users_roles";

    // Items
    public static final String UPSERT_ITEM_CDM             = "i_upsert";
    public static final String ITEMS_GET_ALL_CMD            = "i_get_all";
    public static final String GOTO_INSERT_ITEM_CMD         = "i_goto_insert";

    public static final String ITEM_NAME_ARG                = "i_name";
    public static final String ITEM_ID_ARG                  = "i_id";
    public static final String ITEM_VER_ARG                 = "i_ver";


    // User Management
    public static final String CMD_USERS_GET_ALL            = "cmd_u_all";
    public static final String CMD_GOTO_EDIT_USER           = "goto_edit_user";
    public static final String CMD_DELETE_USER              = "delete_user";
    public static final String CMD_EDIT_USER                =   "delete_user";
    public static final String ARG_USER_ID                  = "id_user";

    public static final String USER_TO_EDIT_ARG             = "watch_user";

    // Error response
    public static final String ERROR_RESPONSE_COMMAND       = "errorResponse";

    // Arguments
    public static final String CATEGORY_VERSION_ARG         = "cat_ver";
    public static final String CATEGORY_ID_ARG              = "cat_id";
    public static final String CATEGORIES_ARG               = "categories";
    public static final String CATEGORY_NAME_ARG            = "cat_name";
    public static final String CATEGORY_NEW_NAME_ARG        = "cat_new_name";
    public static final String ARG_CURR_USER                = "curr_user";
    public static final String USER_NAME_ARG                = "username";
    public static final String USER_PASS_ARG                = "password";
    public static final String USER_PASS_1_ARG              = "password1";

    public static final String USER_LIST_ARG                = "all_users";

    public static final String ARG_CURR_PAGE                 = "curr_page";

    public static final String ERR_RESPONSE                 = "errorResponse";



    public static String translateRequestToCommand(HttpServletRequest request) {
        return request.getParameter(Commands.COMMAND);
    }
}
