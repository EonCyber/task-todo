import { ActivatedRouteSnapshot, CanActivateFn, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { inject } from '@angular/core';
import { ToasterService } from '../services/toaster.service';

export const authGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot, 
  state: RouterStateSnapshot
) => {
  const authService = inject(AuthService);
  const toasterService = inject(ToasterService);
  const router = inject(Router);

  if (authService.isLoggedIn) {
    return true;
  } 
  toasterService.error("Authentication Needed.")
  router.navigate(['/login']);
  return false;
};
