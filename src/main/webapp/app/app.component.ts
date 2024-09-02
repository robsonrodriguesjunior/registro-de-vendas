import { registerLocaleData } from '@angular/common';
import locale from '@angular/common/locales/en';
import { Component } from '@angular/core';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import dayjs from 'dayjs/esm';
// jhipster-needle-angular-add-module-import JHipster will add new module here

import { Router } from '@angular/router';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { fontAwesomeIcons } from './config/font-awesome-icons';
import { AccountService } from './core/auth/account.service';
import MainComponent from './layouts/main/main.component';

@Component({
  selector: 'jhi-app',
  standalone: true,
  template: '<jhi-main></jhi-main>',
  imports: [
    MainComponent,
    // jhipster-needle-angular-add-module JHipster will add new module here
  ],
})
export default class AppComponent {
  constructor(
    applicationConfigService: ApplicationConfigService,
    iconLibrary: FaIconLibrary,
    dpConfig: NgbDatepickerConfig,
    private accountService: AccountService,
    private router: Router,
  ) {
    applicationConfigService.setEndpointPrefix(SERVER_API_URL);
    registerLocaleData(locale);
    iconLibrary.addIcons(...fontAwesomeIcons);
    iconLibrary.addIconPacks(fas);

    dpConfig.minDate = { year: dayjs().subtract(100, 'year').year(), month: 1, day: 1 };
    if (this.accountService.isAuthenticated()) {
      this.router.navigate(['']);
    }
  }
}
