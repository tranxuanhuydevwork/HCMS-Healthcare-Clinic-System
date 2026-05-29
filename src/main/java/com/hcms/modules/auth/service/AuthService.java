package com.hcms.modules.auth.service;

import com.hcms.modules.auth.dto.LoginRequest;
import com.hcms.modules.auth.dto.LoginResponse;

/**
 * Service interface for authentication operations.
 */
public interface AuthService {
    /**
     * Authenticates a user and returns a JWT token.
     */
    LoginResponse login(LoginRequest request);
}
