<template>
  <div>
    <div>
      <div class="contenedor">
      <h3>
        Bienvenid@
      </h3>
    </div>
    <div class="contenedor">
      <h2>Crear cuenta</h2>
      <form id="forma" name="forma" v-on:submit.prevent="processSignUp">
        <div class="elemento">
          <label for="NombreCompleto">Nombre completo</label>
          <input
            type="text"
            id="NombreCompleto"
            name="NombreCompleto"
            v-model="user.name" 
            required="true"
          />
        </div>
        <div class="elemento">
          <label for="NombreDeUsuario">Nombre de usuario nuevo</label>
          <input
            type="text"
            id="NombreDeUsuario"
            v-model="user.username"
            name="NombreDeUsuario"
            required="true"
          />
        </div>
        <div class="elemento">
          <label for="CorreoElectronico">Correo electrónico</label>
          <input
            type="text"
            id="CorreoElectronico"
            v-model="user.email"
            name="CorreoElectronico"
            required="true"
          />
        </div>
        <div class="elemento">
          <label for="password">Contraseña nueva</label>
          <input
            type="password"
            id="password"
            v-model="user.password"
            name="password"
            required="true"
          />
        </div>
        <input type="checkbox" id="terminos" name="terminos" value="terminos" required="true"/>
        <label class="terminos" for="terminos">Acepto los <a href="./Terminos.html">Términos y Condiciones</a> </label>
        <label style="color: rgba(0, 0, 0, 0); font-size: 30px;">texto transparente</label>
        <div class="relativo">
          <div class="boton">
            <input type="submit" value="Crear" />
          </div>
        </div>
        <label style="color: rgba(0, 0, 0, 0); font-size: 40px;">texto transparente</label>
        
      </form>
    </div>

      
    </div>

  </div>
</template>

<script>
import gql from "graphql-tag";
export default {
  name: "SignUp",
  data: function() {
    return {
      user: {
        username: "",
        password: "",
        name: "",
        email: "",
      },
    };
  },
  methods: {
    processSignUp: async function() {
      await this.$apollo
        .mutate({
          mutation: gql`
            mutation($userInput: SignUpInput!) {
              signUpUser(userInput: $userInput) {
                refresh
                access
              }
            }
          `,
          variables: {
            userInput: this.user,
          },
        })
        .then((result) => {
          let dataLogIn = {
            username: this.user.username,
            token_access: result.data.signUpUser.access,
            token_refresh: result.data.signUpUser.refresh,
          };
          this.$emit("completedSignUp", dataLogIn);
        })
        .catch((error) => {
          alert("ERROR: Fallo en el registro.");
        });
    },
  },
};
</script>

<style>

</style>
