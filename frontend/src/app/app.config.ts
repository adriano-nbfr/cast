import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideZoneChangeDetection } from '@angular/core';

import { provideConfiguracaoBasica } from '@dsmpf/ngx-dsmpf/inicializacao';
import { environment } from '../environment';
import { routes } from './app.routes';
import { provideHttpClient, withFetch } from '@angular/common/http';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideConfiguracaoBasica({
      parametrosAplicacao: environment.parametrosAplicacao,
      parametrosSeguranca: environment.parametrosSeguranca,
      rotas: { primeiroNivel: routes, gerarEstruturaPadrao: true }
    }),
    provideHttpClient(withFetch())
  ]
};
