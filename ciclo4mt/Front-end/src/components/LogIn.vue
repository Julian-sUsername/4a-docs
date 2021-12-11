<template>
  <div>

    <div class="contenedor">
      <h3>
        Bienvenid@
      </h3>
    </div>
    <div class="contenedor">
      <h2>Iniciar sesión</h2>
      <form id="forma" name="forma" v-on:submit.prevent="processLogInUser">
        
        <div class="elemento">
          <label for="NombreDeUsuario">Nombre de usuario</label>
          <input
            type="text"
            id="NombreDeUsuario"
            v-model="user.username"
            name="NombreDeUsuario"
            required="true"
          />
        </div>
        
        <div class="elemento">
          <label for="password">Contraseña</label>
          <input
            type="password"
            id="password"
            v-model="user.password"
            name="password"
            required="true"
          />
        
        </div>
        <div class="relativo">
          <div class="boton">
            <input type="submit" value="Iniciar" />
          </div>
        </div>
        <label style="color: rgba(240, 248, 255, 0); font-size: 40px;">texto transparente</label>
          
      </form>
    </div>
  </div>
</template>

<script>
import gql from "graphql-tag";
export default {
  name: "LogIn",
  data: function() {
    return {
      user: {
        username: "",
        password: "",
      },
    };
  },
  methods: {
    processLogInUser: async function() {
      await this.$apollo
        .mutate({
          mutation: gql`
            mutation($credentials: CredentialsInput!) {
              logIn(credentials: $credentials) {
                refresh
                access
              }
            }
          `,
          variables: {
            credentials: this.user,
          },
        })
        .then((result) => {
          let dataLogIn = {
            username: this.user.username,
            token_access: result.data.logIn.access,
            token_refresh: result.data.logIn.refresh,
          };
          this.$emit("completedLogIn", dataLogIn);
        })
        .catch((error) => {
          alert("ERROR 401: Credenciales Incorrectas.");
        });
    },
  },
};
</script>

<style>

</style>
