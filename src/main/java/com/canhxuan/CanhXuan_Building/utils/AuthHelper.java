package com.canhxuan.CanhXuan_Building.utils;

import com.canhxuan.CanhXuan_Building.entity.Permission;
import com.canhxuan.CanhXuan_Building.entity.User;
import com.canhxuan.CanhXuan_Building.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthHelper {

    private final UserRepository userRepository;

    public AuthHelper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("User not authenticated");
        }
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public boolean hasPermission(Permission permission) {
        User user = getCurrentUser();
        return user.getRole().hasPermission(permission);
    }

    public void requirePermission(Permission... permissions) {
        User user = getCurrentUser();
        for (Permission permission : permissions) {
            if (user.getRole().hasPermission(permission)) {
                return; // Có ít nhất 1 permission thì OK
            }
        }
        throw new AccessDeniedException("You don't have permission to perform this action");
    }

    public boolean isOwner(Long userId) {
        User currentUser = getCurrentUser();
        return currentUser.getId().equals(userId);
    }
}
