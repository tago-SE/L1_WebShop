package model.handlers;

import model.repository.DAO.ItemsDao;
import model.repository.entities.ItemEntity;
import utils.Converter;
import view.viewmodels.Item;

import java.util.List;

public class ItemsHandler {

    private static final String[] accessRoles = {"Admin", "Worker"};
    private static final AccessControl accessControl = new AccessControl(accessRoles);

    public static void newItem(Item item, List<String> access) throws Exception, IllegalAccessException {
        if (!accessControl.validateAccess(null, access))
            throw new IllegalAccessException();
        ItemEntity entity = Converter.toItemEntity(item);
        System.out.println("ITEM_HANDLER___" + entity.toString());
        ItemsDao.insert(entity);
    }
}
