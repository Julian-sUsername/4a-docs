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
          Nuevo candidato
        </h3>
      </div>
      <div class="contenedorLog">
        <form id="forma" name="forma" v-on:submit.prevent="processCandidato">
          <div class="elemento">
            <label for="CodigoDeLaUrna">Código de la urna</label>
            <input
              type="text"
              id="CodigoDeLaUrna"
              v-model="createCandidato.codigoUrna"
              name="CodigoDeLaUrna"
              required="true"
            />
          </div>

          <div class="elemento">
            <label for="NombreDelCandidato">Nombre del candidato</label>
            <input
              type="text"
              id="NombreDelCandidato"
              v-model="createCandidato.nombreCompleto"
              name="NombreDelCandidato"
              required="true"
            />
          </div>
          <div class="elemento">
            <label for="DescripcionDelCandidato"
              >Descripción del candidato</label
            >
            <input
              type="text"
              id="DescripcionDelCandidato"
              v-model="createCandidato.descripcion"
              name="DescripcionDelCandidato"
              required="true"
            />
          </div>
          <div class="relativo">
            <div class="boton">
              <input type="submit" value="Crear" />
            </div>
          </div>
          <label style="color: rgba(240, 248, 255, 0); font-size: 40px;"
            >_</label
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
    <div class="infoComplementaria">
      <br>
      <p>
        Información Complementaria
      </p>
      <h4> {{infoCandidato.id}} </h4>
      <h4> {{infoCandidato.nombreCompleto}} </h4>
      <h4> {{infoCandidato.descripcion}} </h4>
    </div>
  </div>
</template>

<script>
import gql from "graphql-tag";
import jwt_decode from "jwt-decode";
export default {
  name: "CrearCandidatos",
  data: function() {
    return {
      userId: jwt_decode(localStorage.getItem("token_refresh")).user_id,
      userDetailById: {
        username: "",
        name: "",
        email: "",
      },
      createCandidato: {
        userId: jwt_decode(localStorage.getItem("token_refresh")).user_id + "",
        codigoUrna: "",
        nombreCompleto: "",
        descripcion: "",
      },
      infoCandidato: {
        id: "Id del candidato: " + localStorage.getItem("id"),
        nombreCompleto: "Nombre: " + localStorage.getItem("nombreCompleto"),
        descripcion: "Descripción: " + localStorage.getItem("descripcionCandidato"),
      }
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
    processCandidato: async function() {
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
        .mutate({
          mutation: gql`
            mutation CreateCandidato($candidato: CandidatoInput!) {
              createCandidato(candidato: $candidato) {
                id
                codigoUrna
                nombreCompleto
                descripcion
              }
            }
          `,
          variables: {
            candidato: this.createCandidato,
          },
        })
        .then((result) => {
          alert("Candidato creado con éxito");
          localStorage.setItem("id", result.data.createCandidato.id);
          localStorage.setItem("nombreCompleto", result.data.createCandidato.nombreCompleto);
          localStorage.setItem("descripcionCandidato", result.data.createCandidato.descripcion);
          location.reload();
        })
        .catch((error) => {
          alert("Error al crear el candidato");
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

.infoComplementaria {
  background: rgb(12 38 48 / 40%);  
  border-radius: 10px 0 10px 0;
  min-height: 250px;
  height: auto;
  margin: 50px;
  margin-top: 1px;
}

.infoComplementaria p {
  margin-top: 1px;
  color: #fff;
  text-align: center;
  font-size: 23px;
  font-family: Arial, Helvetica, sans-serif;
}

</style>
