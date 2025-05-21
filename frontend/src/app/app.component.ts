import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DsSplashComponent } from '@dsmpf/ngx-dsmpf/inicializacao/splash';
import { DsAppNavegacao } from '@dsmpf/ngx-dsmpf/navegacao';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    DsSplashComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {

  protected processandoNavegacaoInicial = inject(DsAppNavegacao).processandoNavegacaoInicial;

}
