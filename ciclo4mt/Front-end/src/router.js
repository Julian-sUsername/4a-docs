import gql from "graphql-tag";
import { createRouter, createWebHistory } from "vue-router";
import {
  ApolloClient,
  createHttpLink,
  InMemoryCache,
} from "@apollo/client/core";
import LogIn from "./components/LogIn.vue";
import SignUp from "./components/SignUp.vue";
import Home from "./components/Home.vue";
import index from "./components/index.vue";
import Votar from "./components/Votar.vue";
import CrearUrna from "./components/CrearUrna.vue";
import CrearCandidatos from "./components/CrearCandidatos.vue";
import AbrirUrna from "./components/AbrirUrna.vue";
import CerrarUrna from "./components/CerrarUrna.vue";
import EliminarUrna from "./components/EliminarUrna.vue";
import EliminarCandidato from "./components/EliminarCandidato.vue";
import ConsultarResultados from "./components/ConsultarResultados.vue";
const routes = [
  {
    path: "/",
    name: "index",
    component: index,
    meta: { requiresAuth: false },
  },
  {
    path: "/user/logIn",
    name: "logIn",
    component: LogIn,
    meta: { requiresAuth: false },
  },
  {
    path: "/user/signUp",
    name: "signUp",
    component: SignUp,
    meta: { requiresAuth: false },
  },
  {
    path: "/user/home",
    name: "home",
    component: Home,
    meta: { requiresAuth: true },
  },
  {
    path: "/votar",
    name: "Votar",
    component: Votar,
    meta: { requiresAuth: true },
  },
  {
    path: "/crear-urna",
    name: "CrearUrna",
    component: CrearUrna,
    meta: { requiresAuth: true },
  },
  {
    path: "/crear-candidatos",
    name: "CrearCandidatos",
    component: CrearCandidatos,
    meta: { requiresAuth: true },
  },
  {
    path: "/abrir-urna",
    name: "AbrirUrna",
    component: AbrirUrna,
    meta: { requiresAuth: true },
  },
  {
    path: "/cerrar-urna",
    name: "CerrarUrna",
    component: CerrarUrna,
    meta: { requiresAuth: true },
  },
  {
    path: "/eliminar-urna",
    name: "EliminarUrna",
    component: EliminarUrna,
    meta: { requiresAuth: true },
  },
  {
    path: "/eliminar-candidato",
    name: "EliminarCandidato",
    component: EliminarCandidato,
    meta: { requiresAuth: true },
  },
  {
    path: "/consultar-resultados",
    name: "ConsultarResultados",
    component: ConsultarResultados,
    meta: { requiresAuth: true },
  },
];
const router = createRouter({
  history: createWebHistory(),
  routes,
});
const apolloClient = new ApolloClient({
  link: createHttpLink({
    uri: "https://votando-co-api.herokuapp.com/",
  }),
  cache: new InMemoryCache(),
});
async function isAuth() {
  if (
    localStorage.getItem("token_access") === null ||
    localStorage.getItem("token_refresh") === null
  ) {
    return false;
  }
  try {
    var result = await apolloClient.mutate({
      mutation: gql`
        mutation($refresh: String!) {
          refreshToken(refresh: $refresh) {
            access
          }
        }
      `,
      variables: {
        refresh: localStorage.getItem("token_refresh"),
      },
    });
    localStorage.setItem("token_access", result.data.refreshToken.access);
    return true;
  } catch {
    localStorage.clear();
    localStorage.clear();
    alert("Su sesión expiró, por favor vuelva a iniciar sesión");
    return false;
  }
}
router.beforeEach(async (to, from) => {
  var is_auth = await isAuth();
  if (is_auth == to.meta.requiresAuth) return true;
  if (is_auth) return { name: "home" };
  return { name: "logIn" };
});
export default router;
