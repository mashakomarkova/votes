package com.komarkova.voteSystem.db;

import com.komarkova.voteSystem.db.entity.User;

public enum Role {
    CLIENT, ADMIN;

    public static Role getRole(User user) {
        int roleId = user.getRoleId() - 1;
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
