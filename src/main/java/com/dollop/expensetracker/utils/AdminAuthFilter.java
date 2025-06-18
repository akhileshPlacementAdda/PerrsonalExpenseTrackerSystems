package com.dollop.expensetracker.utils;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.dollop.expensetracker.Repository.IUserRepository;
import com.dollop.expensetracker.entity.User;
import com.dollop.expensetracker.enums.Role;
import com.dollop.expensetracker.enums.Status;
import com.dollop.expensetracker.exception.UnauthorzationException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(1) // Ensures this filter runs early
public class AdminAuthFilter implements Filter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI();
        boolean isAdminEndpoint = path.startsWith("/api/users/admin/register-user")
                || path.startsWith("/api/categories/admin/*")
                || path.startsWith("/api/categories/admin/update")
                || path.startsWith("/api/categories/admin/delete")
                || path.startsWith("/api/users/admin/get-all-users");
        
        
        if (isAdminEndpoint) {
            String email = request.getHeader("X-Admin-Email");
            String password = request.getHeader("X-Admin-Password");

            if (email == null || password == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Admin Login  required");
                return;
            }

            User admin = userRepository.findByUserEmail(email.trim()).orElse(null);

            if (admin == null ||
            	    admin.isDeleted() ||
            	    !Role.ADMIN.equals(admin.getRole()) ||
            	    !Status.ACTIVE.equals(admin.getUserStatus()) ||
            	    !password.equals(admin.getUserPassword()) ||
            	    !Boolean.TRUE.equals(admin.getIsLoggedIn())) {

            	    throw new UnauthorzationException("Invalid admin credentials or admin not logged in");
            	}



            //   Check if admin is logged in
            if (!Boolean.TRUE.equals(admin.getIsLoggedIn())) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Admin is not logged in");
                return;
            }


            // Make admin available in request for downstream access
            request.setAttribute("admin", admin);
        }

        // Continue processing the chain
        chain.doFilter(request, response);
}
}
