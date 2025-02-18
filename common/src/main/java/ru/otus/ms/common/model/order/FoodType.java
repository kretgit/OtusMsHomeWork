package ru.otus.ms.common.model.order;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum FoodType {
    PIZZA(1000, 10),
    BURGER(500, 5),
    COLA(300, 3);

    private final int amount;

    private final int maxItems;

    FoodType(int amount, int maxItems) {
        this.amount = amount;
        this.maxItems = maxItems;
    }

    public static FoodType findType(String name) {
        for (FoodType type : FoodType.values()) {
            if (name.equalsIgnoreCase(type.name())) {
                return type;
            }
        }

        return null;
    }

    public static boolean maxItemsExceeded(int size) {
        return size > Arrays.stream(FoodType.values()).mapToInt(FoodType::getMaxItems).sum();
    }
}
