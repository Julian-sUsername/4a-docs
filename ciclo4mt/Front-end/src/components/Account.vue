<template>
  <div>
    <div v-if="!is_auth" v-on:click="loadIndex">
      <img class="logo" src="../assets/img/LOGO.svg" />
    </div>
  <div id="Historial">
    <div class="container">
      <h2>
        Nombre de usuario:
        <span>{{ username }}</span>
      </h2>
    </div>
    <h2>Urnas</h2>
    <div class="container-table">
      <table>
        <tr>
          <th>Código Urna</th>
          <th>Candidato</th>
          <th>Fecha</th>
          <th>Hora</th>
        </tr>
        <tr v-for="transaction in transactionByUsername" :key="transaction.id">
          <td>{{ transaction.date.substring(0, 10) }}</td>
          <td>{{ transaction.date.substring(11, 19) }}</td>
          <td>{{ transaction.usernameOrigin }}</td>
          <td>{{ transaction.usernameDestiny }}</td>
          <td>${{ transaction.value }} COP</td>
        </tr>
      </table>
    </div>
  </div>
  </div>
</template>

<script>
import gql from "graphql-tag";
export default {
  name: "Account",
  data: function() {
    return {
      username: localStorage.getItem("username") || "none",
      transactionByUsername: [],
      accountByUsername: {
        balance: "",
        lastChange: "",
      },
    };
  },
  apollo: {
    transactionByUsername: {
      query: gql`
        query($username: String!) {
          transactionByUsername(username: $username) {
            id
            usernameOrigin
            usernameDestiny
            value
            date
          }
        }
      `,
      variables() {
        return {
          username: this.username,
        };
      },
    },
    accountByUsername: {
      query: gql`
        query($username: String!) {
          accountByUsername(username: $username) {
            balance
            lastChange
          }
        }
      `,
      variables() {
        return {
          username: this.username,
        };
      },
    },
  },
  created: function() {
    this.$apollo.queries.transactionByUsername.refetch();
    this.$apollo.queries.accountByUsername.refetch();
  },
};
</script>

<style>
#Historial {
  width: 100%;
  display: flex;
  justify-content: flex-start;
  font-family: Arial, Helvetica, sans-serif;
  align-items: center;
  flex-direction: column;
}
#Historial .container-table {
  width: 50%;
  max-height: 250px;
  overflow-y: scroll;
  overflow-x: hidden;
}
#Historial table {
  width: 100%;
  border-collapse: collapse;
  border: 1px solid rgba (0, 0, 0, 0.3);
}
#Historial table td,
#Historial table th {
  border: 1px solid #ddd;
  padding: 8px;
}
#Historial table tr:nth-child(even) {
  background-color: #f2f2f2;
}
#Historial table tr:hover {
  background-color: #ddd;
}
#Historial table th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: rgb(12 38 48 / 40%);
  color: white;
}
#Historial > h2 {
  color: #283747;
  font-size: 25px;  
}
#Historial .container {
  padding: 30px;
  border: 3px solid rgba (0, 0, 0, 0.3);
  border-radius: 20px;
  margin: 5% 0 1% 0;
}
#Historial .container h2 {
  font-size: 25px;
  color: #283747;
}
#Historial .container span {
  color: rgb(12 38 48 / 40%);
  font-weight: bold;
}
</style>
