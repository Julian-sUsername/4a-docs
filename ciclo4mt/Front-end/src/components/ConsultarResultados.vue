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
          Consultar Resultados
        </h3>
      </div>
      <div class="contenedorLog">
        <form id="forma" name="forma" v-on:submit.prevent="processConsultarResultados">
          <div class="elemento">
            <label for="CodigoDeLaUrna">Código de la urna</label>
            <input
              type="text"
              id="CodigoDeLaUrna"
              v-model="codigoInput.codigo"
              name="CodigoDeLaUrna"
              required="true"
            />
          </div>
          <div class="relativo">
            <div class="boton">
              <input
                type="submit"
                value="Consultar"
              />
            </div>
          </div>
          <label style="color: rgba(240, 248, 255, 0); font-size: 40px;"
            >_</label
          >
        </form>
      </div>
    </div>
    <div class="main-component"></div>
    <div class="informacionComplementariay">
      <br />
      <p>
        Información Complementaria
      </p>
      <h4>Resultados: {{ resultadosUrna.resultados }}</h4>
      <h4>{{ resultadosUrna.ganador }}</h4>
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
  name: "ConsultarResultados",
  data: function() {
    return {
      userId: jwt_decode(localStorage.getItem("token_refresh")).user_id,
      userDetailById: {
        username: "",
        name: "",
        email: "",
      },
      codigoInput: {
        userId: jwt_decode(localStorage.getItem("token_refresh")).user_id + "",
        codigo: "",
      },
      resultadosUrna: {
        resultados: localStorage.getItem("resultados"),
        ganador: localStorage.getItem("ganador"),
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

  created: function() {
    this.$apollo.queries.resultadosUrna.refetch();
  },

  methods: {
    processConsultarResultados: async function() {
      
      if (
        localStorage.getItem("token_access") === null ||
        localStorage.getItem("token_refresh") === null
      ) {
        this.$emit("logOut");
        return;
      }
      localStorage.setItem("token_access", "");
      await this.$apollo
        .mutate({
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
        })
        .then((result) => {
          localStorage.setItem("token_access", result.data.refreshToken.access);
        })
        .catch((error) => {
          this.$emit("logOut");
          return;
        });
      await this.$apollo
        .query({
          query: gql`
            query ResultadosUrna($codigoInput: urnaByCodigoInput!) {
              resultadosUrna(codigoInput: $codigoInput) {
                resultados
                ganador
              }
            }
          `,
          variables: {
            codigoInput: this.resultadosUrna,
          },
        })
        .then((result) => {
          console.log(result)
          localStorage.setItem("ganador", result.data.urnaByCodigo.ganador);
          localStorage.setItem(
            "resultados",
            result.data.urnaByCodigo.resultados
          );
        })
        .catch((error) => {
          alert("Intenta de nuevo");
        });
    },

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

<style>
.informacionComplementariay {
  background: rgb(12 38 48 / 40%);
  border-radius: 10px 0 10px 0;
  min-height: 250px;
  height: auto;
  margin: 50px;
  margin-top: 140px;
}

.informacionComplementariay p {
  color: #fff;
  text-align: center;
  font-size: 23px;
  font-family: Arial, Helvetica, sans-serif;
}
</style>
