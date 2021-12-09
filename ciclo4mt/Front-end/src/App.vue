<template>
  <div id="app" class="app">
    <div v-if="!is_auth" v-on:click="loadIndex">
      <img class="logo" src="../src/assets/img/LOGO.svg" />
    </div>
    <div>
      <nav>
        <button v-if="is_auth" v-on:click="loadHome" style="right: 500px;">Inicio</button>
        <button v-if="is_auth" v-on:click="loadAccount" style="right: 250px;">Mis urnas</button>
        <button v-if="is_auth" v-on:click="logOut">Cerrar Sesión</button>
        <button v-if="!is_auth" v-on:click="loadLogIn">Iniciar Sesión</button>
        <button v-if="!is_auth" v-on:click="loadSignUp" style="right: 250px;">
          Crear cuenta
        </button>
      </nav>
    </div>

    <div class="main-component">
      <router-view
        v-on:completedLogIn="completedLogIn"
        v-on:completedSignUp="completedSignUp"
        v-on:logOut="logOut"
      >
      </router-view>
    </div>

    <footer>
      <h2 v-if="!is_auth" v-on:click="loadIndex">
        
          votando.co
        
      </h2>
    </footer>
  </div>
</template>

<script>
export default {
  name: "App",
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
    loadAccount: function() {
      this.$router.push({ name: "account" });
    },
    loadTransaction: function() {
      this.$router.push({ name: "transaction" });
    },
    logOut: function() {
      localStorage.clear();
      this.loadLogIn();
    },
  },
};
</script>

<style>
html {
  height: auto;
  min-height: 100%;
  background: radial-gradient(circle, #006cba3b, #179bd318);
}

p {
  color: #374042;
}

.logo {
  margin: 10px;
  width: 70px;
  cursor: pointer;
}

input {
  font-family: Arial, Helvetica, sans-serif;
  font-size: 17px;
}

.contenedor {
  margin: 20px auto;
  color: #fff;
  width: 350px;
  padding: 30px;
  font-family: Arial, Helvetica, sans-serif;
  background: rgb(12 38 48 / 40%);
  border-radius: 10px;
}
.contenedor h2 {
  margin: 0 0 30px;
  padding: 0;
  font-size: 30px;
  color: #ffffff;
  text-align: center;
}

.contenedor h3 {
  margin: auto;
  padding: 0;
  font-size: 30px;
  color: #ffffff;
  text-align: center;
}

.contenedor .elemento label {
  color: #fff;
}
.contenedor .elemento input {
  width: 100%;
  padding: 10px 0;
  color: #fff;
  background: transparent;
  border: none;
  border-bottom: 1px solid #fff;
  margin-bottom: 30px;
}
.boton input[type="submit"] {
  background-color: #fff;
  font-family: Arial, Helvetica, sans-serif;
  color: #467fb3;
  position: absolute;
  right: 0;
  border: none;
  border-radius: 6px;
  letter-spacing: 5px;
  text-align: center;
  text-transform: uppercase;
  cursor: pointer;
  padding: 20px;
  width: 190px;
}

.boton input[type="submit"]:hover {
  background-color: #467fb3;
  font-family: Arial, Helvetica, sans-serif;
  color: #fff;
  position: absolute;
  right: 0;
  border: none;
  border-radius: 6px;
  letter-spacing: 5px;
  text-align: center;
  text-transform: uppercase;
  cursor: pointer;
  padding: 20px;
  width: 190px;
}

button {
  background-color: rgb(12 38 48 / 40%);
  font-family: Arial, Helvetica, sans-serif;
  color: #fff;
  position: absolute;
  top: 0;
  right: 0;
  margin: 20px;
  border: none;
  border-radius: 6px;
  letter-spacing: 5px;
  text-transform: uppercase;
  text-align: center;
  text-decoration: none;
  cursor: pointer;
  padding: 20px;
  width: 220px;
}

button:hover {
  background-color: #fff;
  font-family: Arial, Helvetica, sans-serif;
  color: rgb(12 38 48 / 40%);
  position: absolute;
  top: 0;
  right: 0;
  margin: 20px;
  border: none;
  border-radius: 6px;
  letter-spacing: 5px;
  text-transform: uppercase;
  text-align: center;
  text-decoration: none;
  cursor: pointer;
  padding: 20px;
  width: 220px;
}

.terminos a:link,
a:visited {
  text-decoration: none;
  color: #fff;
}

.terminos a:hover {
  color: #b3d7ec;
}

.relativo {
  position: relative;
}

header {
  color: #37658dc2;
  text-align: center;
  font-size: 20px;
}
nav {
  width: 20%;
  float: left;
}
nav a {
  background-color: #37617885;
  border-radius: 10px 0 10px 0;
  color: #fff;
  margin: 10px;
  padding: 10px;
  text-decoration: none;
  display: block;
}

nav a:hover {
  background-color: #fff;
  border-radius: 10px 0 10px 0;
  color: #37617885;
  margin: 10px;
  padding: 10px;
  text-decoration: none;
  display: block;
}
section {
  float: right;
  width: 75%;
  color: #143b4eb6;
  border-radius: 5px;
  box-shadow: 1px 0 4px rgb(0, 0, 0, 0.2);
  margin-bottom: 15px;
  padding: 15px;
}
aside {
  float: right;
  width: 30%;
  padding: 5px;
  margin: 5px;
  font-style: italic;
  color: #143b4eb6;
  box-shadow: inset 5px 0 5px -5px #4895ef;
}
footer {
  clear: both;
  padding: 1px;
  text-align: center;
}
footer h2 {
  color: #374042;
}

footer h2:hover {
  color: #37658dc2;
  cursor: pointer;
}
</style>
