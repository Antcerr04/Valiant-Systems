package model.gestionecarrello;

import model.gestioneinventario.Prodotto;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user's shopping cart containing a list of {@link CarrelloItem}.
 *
 */
public class Carrello {

    private List<CarrelloItem> carrelloItemList = new ArrayList<CarrelloItem>();


    /**
     * Returns the list of items contained in the shopping cart.
     *
     * @return a list of {@link CarrelloItem} objects
     */
    public List<CarrelloItem> getCarrelloItemList() {
        return carrelloItemList;
    }

    /**
     * Adds a product to the shopping cart.
     * <p>
     * If the product already exists in the cart, its quantity is increased by one,
     * as long as the available stock allows it.
     * If the product is not present in the cart, it is added with an initial quantity of one.
     * </p>
     * @param prodotto the {@link Prodotto} to add to the shopping cart
     */
    public void addCarrelloItem(Prodotto prodotto) {
        boolean found = false;
        for(CarrelloItem carrelloItem : carrelloItemList) {
            if (carrelloItem.getProdotto().getId() == prodotto.getId()) {
                if(carrelloItem.getQuantita()<prodotto.getQuantita()) {
                    int quantita = carrelloItem.getQuantita();
                    carrelloItem.setQuantita(quantita + 1);
                }

                found = true;
            }
        }
        if(!found) {
            if(prodotto.getQuantita() > 0) {
                CarrelloItem item = new CarrelloItem();
                item.setProdotto(prodotto);
                item.setQuantita(1);
                carrelloItemList.add(item);
            }
        }

    }

    /**
     * Removes one unit of the specified product from the shopping cart if present.
     * <p>
     * If the quantity is 1, the product is removed from the cart, otherwise the quantity is decremented by 1.
     * </p>
     * @param prodotto the {@link Prodotto} to remove from the shopping cart
     * @return {@code true} if the product was found and removed or decremented,
     *         {@code false} if the product was not found in the cart
     */
    public boolean removeCarrelloItem(Prodotto prodotto) {
        for(CarrelloItem carrelloItem : carrelloItemList) {
            if (carrelloItem.getProdotto().getId() == prodotto.getId()) {
                int quantita = carrelloItem.getQuantita();
                if(quantita == 1) {
                    carrelloItemList.remove(carrelloItem);
                }else {
                    carrelloItem.setQuantita(quantita - 1);
                }
                return true;
            }
        }
        return false;
    }
}