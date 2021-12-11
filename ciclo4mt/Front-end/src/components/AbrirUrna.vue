<template>
  <div>
    <div v-on:click="loadIndex">
      <img class="logo" src="../assets/img/LOGO.svg" />
    </div>

    <nav style="position:relative;top: 9px;">
      <a v-on:click="loadVotar">Votar</a>
      <a v-on:click="loadCrearUrna">Crear urna</a>
      <a v-on:click="loadCrearCandidatos">Crear candidatos</a>
      <a v-on:click="loadAbrirUrna">Abrir urna</a>
      <a v-on:click="loadCerrarUrna">Cerrar urna</a>
      <a v-on:click="loadEliminarUrna">Eliminar urna</a>
      <a v-on:click="loadEliminarCandidato">Eliminar candidato</a>
      <a v-on:click="loadConsultarResultados">Consultar resultados</a>
    </nav>

    <div class="contenedorInfo">
      <h2>
        <span>{{ userDetailById.name }}</span>
      </h2>
      <div class="details">
        <br />
        <h3>Su información es la siguiente</h3>
        <h2>
          Nombre de usuario:
          <span>{{ userDetailById.username }}</span>
        </h2>
        <h2>
          Correo electrónico:
          <span>{{ userDetailById.email }}</span>
        </h2>
      </div>
    </div>
    <div class="contenedorCentralLog">
      <div class="contenedorLog">
        <h3>
          Abrir Urna
        </h3>
      </div>
      <div class="contenedorLog">
        <form id="forma" name="forma" v-on:submit.prevent="processLogInUser">
          <div class="elemento">
            <label for="CodigoDeLaUrna">Código de la urna</label>
            <input
              type="text"
              id="CodigoDeLaUrna"
              name="CodigoDeLaUrna"
              required="true"
            />
          </div>
          <div class="relativo">
            <div class="boton">
              <input type="submit" value="Abrir" />
            </div>
          </div>
          <label style="color: rgba(240, 248, 255, 0); font-size: 40px;"
            >texto transparente</label
          >
        </form>
      </div>
    </div>
    <div class="main-component">
      <router-view
        v-on:completedLogIn="completedLogIn"
        v-on:completedSignUp="completedSignUp"
        v-on:logOut="logOut"
      >
      </router-view>
    </div>
  </div>
</template>

<script>
import gql from "graphql-tag";
import jwt_decode from "jwt-decode";
export default {
  name: "AbrirUrna",
  data: function() {
    return {
      userId: jwt_decode(localStorage.getItem("token_refresh")).user_id,
      userDetailById: {
        username: "",
        name: "",
        email: "",
      },
    };
  },

  computed: {
    is_auth: {
      get: function() {
        return this.$route.meta.requiresAuth;
      },
      set: function() {},
    },
  },

  methods: {
    loadLogIn: function() {
      this.$router.push({ name: "logIn" });
    },
    loadSignUp: function() {
      this.$router.push({ name: "signUp" });
    },
    loadIndex: function() {
      this.$router.push({ name: "index" });
    },
    loadVotar: function() {
      this.$router.push({ name: "Votar" });
    },
    loadCrearUrna: function() {
      this.$router.push({ name: "CrearUrna" });
    },
    loadCrearCandidatos: function() {
      this.$router.push({ name: "CrearCandidatos" });
    },
    loadAbrirUrna: function() {
      this.$router.push({ name: "AbrirUrna" });
    },
    loadCerrarUrna: function() {
      this.$router.push({ name: "CerrarUrna" });
    },
    loadEliminarUrna: function() {
      this.$router.push({ name: "EliminarUrna" });
    },
    loadEliminarCandidato: function() {
      this.$router.push({ name: "EliminarCandidato" });
    },
    loadConsultarResultados: function() {
      this.$router.push({ name: "ConsultarResultados" });
    },
    completedLogIn: function(data) {
      localStorage.setItem("username", data.username);
      localStorage.setItem("token_access", data.token_access);
      localStorage.setItem("token_refresh", data.token_refresh);
      this.loadHome();
    },
    completedSignUp: function(data) {
      this.completedLogIn(data);
    },
    loadHome: function() {
      this.$router.push({ name: "home" });
    },
    logOut: function() {
      localStorage.clear();
      this.loadLogIn();
    },
  },

  apollo: {
    userDetailById: {
      query: gql`
        query($userId: Int!) {
          userDetailById(userId: $userId) {
            username
            name
            email
          }
        }
      `,
      variables() {
        return {
          userId: this.userId,
        };
      },
    },
  },
};
</script>

<style></style>
