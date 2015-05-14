package org.trimou.benchmark.data;

import java.math.BigDecimal;

public abstract class Item {

	public String name;

	public BigDecimal price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean hasPrice() {
		return price != null;
	}

	public boolean isToy() {
		return this instanceof Toy;
	}

}
