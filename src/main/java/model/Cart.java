package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {

    private static class CartItem {
        protected int amount;
        protected Item item;
    }

    List<CartItem> items = new ArrayList<>();

}
