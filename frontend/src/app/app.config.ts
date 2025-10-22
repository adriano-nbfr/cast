import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideConfiguracaoBasica } from '@dsmpf/ngx-dsmpf/inicializacao';
import { routes } from './app.routes';
import { environment } from '../environment';


export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideConfiguracaoBasica({
      parametrosAplicacao: environment.parametrosAplicacao,
      parametrosSeguranca: environment.parametrosSeguranca,
      rotas: { primeiroNivel: routes, gerarEstruturaPadrao: true }
    })
  ]
};
