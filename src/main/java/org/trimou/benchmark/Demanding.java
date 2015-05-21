package org.trimou.benchmark;


/**
 *
 * @author Martin Kouba
 */
public class Demanding extends Basic {

    @Override
    protected int getItemsSize() {
        return 200;
    }

    protected String getMustacheName() {
        return "templates/demanding";
    }

}
