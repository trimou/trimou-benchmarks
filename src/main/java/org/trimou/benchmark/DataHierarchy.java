package org.trimou.benchmark;

import java.math.BigDecimal;
import java.util.Calendar;

import org.trimou.benchmark.data.Food;
import org.trimou.benchmark.data.Item;
import org.trimou.benchmark.data.Origin;
import org.trimou.benchmark.data.Toy;

/**
 *
 * @author Martin Kouba
 */
public class DataHierarchy extends Basic {

    protected Item generateItem(int idx) {
        Item item;
        if (idx % 2 == 0) {
            item = new Toy();
            ((Toy) item).setMinAge(10);
        } else {
            item = new Food();
            ((Food) item).setOrigin(new Origin("cell " + idx));
        }
        item.setId(Long.valueOf(idx * 10));
        item.setName("" + idx);
        item.setPrice(new BigDecimal(idx * 1000));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        item.setCreated(cal.getTime());
        item.setDescription(String.format("Item %s with price %s (created at %s)", item.getId(), item.getPrice(), item.getCreated().getTime()));
        return item;
    }

}
