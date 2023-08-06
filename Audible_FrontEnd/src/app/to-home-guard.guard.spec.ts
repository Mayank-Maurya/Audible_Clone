import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { toHomeGuardGuard } from './to-home-guard.guard';

describe('toHomeGuardGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => toHomeGuardGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
