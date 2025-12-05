import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from './services/auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.getToken();
  
  // Only add the header if a token exists and the request is not for the authentication endpoints
  if (token && !req.url.includes('/api/auth')) {
    // Clone the request and add the Authorization header
    const clonedRequest = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    console.log(`[HTTP Interceptor] Token added to request for: ${req.url}`);
    return next(clonedRequest);
  }
  // If no token, or if it's a login request, proceed without modification
  return next(req);
};
