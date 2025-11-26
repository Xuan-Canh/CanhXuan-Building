package com.canhxuan.CanhXuan_Building.utils;

import com.canhxuan.CanhXuan_Building.entity.Permission;
import com.canhxuan.CanhXuan_Building.entity.User;
import com.canhxuan.CanhXuan_Building.repository.ContractRepository;
import com.canhxuan.CanhXuan_Building.repository.InvoiceRepository;
import com.canhxuan.CanhXuan_Building.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthHelper {

    private final UserRepository userRepository;
    private final ContractRepository contractRepository;
    private final InvoiceRepository invoiceRepository;

    public AuthHelper(UserRepository userRepository, ContractRepository contractRepository, InvoiceRepository invoiceRepository) {
        this.userRepository = userRepository;
        this.contractRepository = contractRepository;
        this.invoiceRepository = invoiceRepository;
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

    public boolean isContractOwner(Long contractId) {
        User currentUser = getCurrentUser();
        boolean isOwner = contractRepository.existsByIdAndCreatedByUsername(contractId, currentUser.getUsername());
        System.out.println("contractId: " + contractId + ", currentUser: " + currentUser.getUsername() + ", isOwner: " + isOwner);
        return isOwner;
    }

    public boolean isInvoiceOwner(Long invoiceId) {
        User currentUser = getCurrentUser();
        boolean isOwner = invoiceRepository.existsByIdAndCreatedByUsername(invoiceId, currentUser.getUsername());
        System.out.println("invoiceId: " + invoiceId + ", currentUser: " + currentUser.getUsername() + ", isOwner: " + isOwner);
        return isOwner;
    }

    public boolean isUserProfileOwner(String username) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return currentUsername.equals(username);
    }
}
