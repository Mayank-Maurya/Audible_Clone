import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { StorageService } from './storage.service';
@Injectable({
  providedIn: 'root'
})

export class toHomeGuardGuard implements CanActivate {
  constructor(private auth:StorageService, private router: Router){};
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let isLoggedIn = this.auth.isLoggedIn();
    if (isLoggedIn){
      this.router.navigate(['/home']);
    } else {
      return true;
    }
    return false;
  }
}
