package com.abmike.ecommerce_prod_mgmt.util;

import com.abmike.ecommerce_prod_mgmt.model.Category;

public class BinaryTreeUtil {

    public static void insert(Category root, Category newCategory) {
        if (newCategory.getName().compareTo(root.getName()) < 0) {
            if (root.getLeft() == null) {
                root.setLeft(newCategory);
            } else {
                insert(root.getLeft(), newCategory);
            }
        } else {
            if (root.getRight() == null) {
                root.setRight(newCategory);
            } else {
                insert(root.getRight(), newCategory);
            }
        }
    }

    public static Category search(Category root, String categoryName) {
        if (root == null || root.getName().equals(categoryName)) {
            return root;
        }

        if (categoryName.compareTo(root.getName()) < 0) {
            return search(root.getLeft(), categoryName);
        }

        return search(root.getRight(), categoryName);
    }
}