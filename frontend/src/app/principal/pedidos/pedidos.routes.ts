import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, RedirectCommand, Router, Routes } from "@angular/router";
import { DsAppNotificacao } from "@dsmpf/ngx-dsmpf/basico";
import { DsHttpError } from "@dsmpf/ngx-dsmpf/rest";
import { DsAppSeguranca } from "@dsmpf/ngx-dsmpf/seguranca";
import { catchError, EMPTY, of } from "rxjs";
import { environment } from "../../../environment";
import { PedidosApi } from "./pedidos-api";


export default [
  {
    path: 'meus-pedidos',
    title: 'Meus Pedidos',
    loadComponent: () => import('./meus-pedidos/meus-pedidos').then(m => m.MeusPedidos)
  },
  {
    path: 'painel-gerencial',
    title: 'Painel Gerencial',
    loadComponent: () => import('./painel-gerencial/painel-gerencial').then(m => m.PainelGerencial),
    canMatch: [
      () => inject(DsAppSeguranca).isUsuarioAutorizadoAssincrono(environment.papeis.PAPEL_ATENDENTE, true)
    ]
  },
  {
    path: 'novo',
    title: 'Novo pedido',
    loadComponent: () => import('./novo-pedido/novo-pedido').then(m => m.NovoPedido),
    resolve: {
      novoPedido: (activatedRoute: ActivatedRouteSnapshot) => {
        const router = inject(Router);
        const appNotificacao = inject(DsAppNotificacao);
        const pedidosApi = inject(PedidosApi);

        return pedidosApi.obterNovoPedido(activatedRoute.queryParams['idServico']).pipe(
          catchError((error: DsHttpError) => {
            appNotificacao.notificarErro(`Algo deu errado. ${error.message}`);
            appNotificacao.notificarAviso('A página foi redirecionada.', '', {timeOut: 2000});

            if (error.status === 0) // Não completa a navegação se ocorreu um erro local de rede
              return EMPTY;

            // Retorna um RedirectCommand para navegar para uma rota fallback
            return of(new RedirectCommand(router.parseUrl('/')));
          })
        );
      }
    }
  },
  {
    path: ':idPedido',
    title: 'Pedido',
    loadComponent: () => import('./pedido-edicao/pedido-edicao').then(m => m.PedidoEdicao),
    resolve: {
      pedido: (activatedRoute: ActivatedRouteSnapshot) => {
        const router = inject(Router);
        const appNotificacao = inject(DsAppNotificacao);
        const pedidosApi = inject(PedidosApi);

        return pedidosApi.obter(activatedRoute.params['idPedido']).pipe(
          catchError((error: DsHttpError) => {
            appNotificacao.notificarErro(error.message); // Notifica opcionalmente a causa do erro

            if (error.status === 0) // Não completa a navegação se ocorreu um erro local de rede
              return EMPTY;

            // Abordagem 1: Retorna um RedirectCommand para navegar para uma rota de erro padrão
            return of(new RedirectCommand(router.parseUrl('/erro/nao-encontrado')));

            // Abordagem 2: Retorna um objeto inválido e deixa que o componente da tela trate a exibição
            // return of({id: null}); // Pode completar a navegação, mas retorna um objeto inválido
          })
        );
      }
    }
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'meus-pedidos'
  }
] as Routes;
